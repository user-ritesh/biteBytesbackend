package cart.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class CartWriteRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void updateCartItemQuantity(String userId, String itemId, int amount) {
        Query query = new Query(Criteria.where("_id").is(userId));
        // This acts exactly like MongoDB's $inc, making it thread-safe and atomic
        Update update = new Update().inc("cartData." + itemId, amount);
        mongoTemplate.updateFirst(query, update, "users");
    }
}
