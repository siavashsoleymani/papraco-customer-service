package com.papraco.customerservice.repository;

import com.papraco.customerservice.domain.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Offer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferRepository extends MongoRepository<Offer, String> {}
