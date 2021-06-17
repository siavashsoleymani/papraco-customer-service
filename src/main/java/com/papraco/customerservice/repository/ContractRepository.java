package com.papraco.customerservice.repository;

import com.papraco.customerservice.domain.Contract;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Contract entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractRepository extends MongoRepository<Contract, String> {}
