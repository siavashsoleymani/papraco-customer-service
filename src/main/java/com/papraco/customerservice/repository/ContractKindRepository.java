package com.papraco.customerservice.repository;

import com.papraco.customerservice.domain.ContractKind;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ContractKind entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractKindRepository extends MongoRepository<ContractKind, String> {}
