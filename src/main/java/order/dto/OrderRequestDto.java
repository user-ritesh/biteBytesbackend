package order.dto;


import order.entity.OrderItem;

import java.util.List;
import java.util.Map;

public class OrderRequestDto {
    private String userId; // Will be injected by controller
    private List<OrderItem> items;
    private Double amount;
    private Map<String, Object> address;
    private Boolean stockUpdate;

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public Map<String, Object> getAddress() { return address; }
    public void setAddress(Map<String, Object> address) { this.address = address; }
    public Boolean getStockUpdate() { return stockUpdate; }
    public void setStockUpdate(Boolean stockUpdate) { this.stockUpdate = stockUpdate; }
}

