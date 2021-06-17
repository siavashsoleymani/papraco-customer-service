package com.papraco.customerservice.repository;

import com.papraco.customerservice.domain.Facture;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Facture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FactureRepository extends MongoRepository<Facture, String> {}
