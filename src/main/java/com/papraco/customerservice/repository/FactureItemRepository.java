package com.papraco.customerservice.repository;

import com.papraco.customerservice.domain.FactureItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the FactureItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FactureItemRepository extends MongoRepository<FactureItem, String> {}
