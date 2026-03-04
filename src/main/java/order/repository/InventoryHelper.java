package order.repository;

import food.entity.FoodEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import user.entity.UserEntity;

import java.util.HashMap;

@Component
public class InventoryHelper {

    @Autowired
    private MongoTemplate mongoTemplate;

    // Atomically decrements stock IF current stock is >= requested quantity
    public boolean decrementStockAtomically(String foodId, int quantity) {
        Query query = new Query(Criteria.where("_id").is(foodId).and("stock").gte(quantity));
        Update update = new Update().inc("stock", -quantity);

        // Returns true if a document was successfully modified
        return mongoTemplate.updateFirst(query, update, FoodEntity.class).getModifiedCount() > 0;
    }

    // Clears the user's cart
    public void clearUserCart(String userId) {
        Query query = new Query(Criteria.where("_id").is(userId));
        Update update = new Update().set("cartData", new HashMap<>());
        mongoTemplate.updateFirst(query, update, UserEntity.class);
    }
}
