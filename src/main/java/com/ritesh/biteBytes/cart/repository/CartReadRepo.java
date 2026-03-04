package com.ritesh.biteBytes.cart.repository;

import com.ritesh.biteBytes.cart.entity.CartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartReadRepo extends MongoRepository<CartEntity, String> {
    // Standard CRUD operations for reading are inherited automatically
}
