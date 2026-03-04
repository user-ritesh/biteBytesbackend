package com.ritesh.biteBytes.order.service;


import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.ritesh.biteBytes.order.dto.OrderRequestDto;
import com.ritesh.biteBytes.order.dto.OrderResponseDto;
import com.ritesh.biteBytes.order.entity.OrderEntity;
import com.ritesh.biteBytes.order.entity.OrderItem;
import com.ritesh.biteBytes.order.repository.InventoryHelper;
import com.ritesh.biteBytes.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryHelper inventoryHelper;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public OrderResponseDto placeOrder(OrderRequestDto request) {
        try {
            Stripe.apiKey = stripeSecretKey;

            // 1. Prepare Order Entity
            OrderEntity newOrder = new OrderEntity();
            newOrder.setUserId(request.getUserId());
            newOrder.setItems(request.getItems());
            newOrder.setAmount(request.getAmount());
            newOrder.setAddress(request.getAddress());
            // Save the order to get the generated ID for the Stripe callback URLs
            newOrder = orderRepository.save(newOrder);

            // 2. Process Inventory Atomically
            for (OrderItem item : request.getItems()) {
                boolean success = inventoryHelper.decrementStockAtomically(item.getId(), item.getQuantity());
                if (!success) {
                    inventoryHelper.clearUserCart(request.getUserId());
                    return new OrderResponseDto(false, item.getName() + " is out of stock");
                }
            }
            inventoryHelper.clearUserCart(request.getUserId());

            // 3. Prepare Stripe Line Items
            List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
            for (OrderItem item : request.getItems()) {
                lineItems.add(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity((long) item.getQuantity())
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("inr")
                                                .setUnitAmount((long) (item.getPrice() * 100 * 80))
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(item.getName())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                );
            }

            // Add Delivery Charge
            lineItems.add(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("inr")
                                            .setUnitAmount((long) (5 * 80 * 100))
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName("Delivery Charge")
                                                            .build()
                                            )
                                            .build()
                            )
                            .build()
            );

            // 4. Create Stripe Session
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:5173/verify?success=true&orderId=" + newOrder.getId())
                    .setCancelUrl("http://localhost:5173/verify?success=false&orderId=" + newOrder.getId())
                    .addAllLineItem(lineItems)
                    .build();

            Session session = Session.create(params);

            return new OrderResponseDto(true, session.getUrl(), true);

        } catch (Exception e) {
            e.printStackTrace();
            return new OrderResponseDto(false, "Error");
        }
    }

    public OrderResponseDto listOrders() {
        return new OrderResponseDto(true, orderRepository.findAll());
    }

    public OrderResponseDto userOrders(String userId) {
        return new OrderResponseDto(true, orderRepository.findByUserId(userId));
    }

    public OrderResponseDto updateStatus(Map<String, String> request) {
        try {
            Optional<OrderEntity> orderOpt = orderRepository.findById(request.get("orderId"));
            if (orderOpt.isPresent()) {
                OrderEntity order = orderOpt.get();
                order.setStatus(request.get("status"));
                orderRepository.save(order);
                return new OrderResponseDto(true, "Status Updated");
            }
            return new OrderResponseDto(false, "Order not found");
        } catch (Exception e) {
            return new OrderResponseDto(false, "Error");
        }
    }

    public OrderResponseDto verifyOrder(Map<String, String> request) {
        try {
            String orderId = request.get("orderId");
            String success = request.get("success");

            if ("true".equals(success)) {
                Optional<OrderEntity> orderOpt = orderRepository.findById(orderId);
                if(orderOpt.isPresent()) {
                    OrderEntity order = orderOpt.get();
                    order.setPayment(true);
                    orderRepository.save(order);
                    return new OrderResponseDto(true, "Paid");
                }
            } else {
                orderRepository.deleteById(orderId);
                return new OrderResponseDto(false, "Not Paid");
            }
            return new OrderResponseDto(false, "Not Verified");
        } catch (Exception e) {
            return new OrderResponseDto(false, "Error");
        }
    }
}

