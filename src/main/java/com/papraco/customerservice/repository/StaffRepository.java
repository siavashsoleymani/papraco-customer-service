package com.papraco.customerservice.repository;

import com.papraco.customerservice.domain.Staff;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Staff entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StaffRepository extends MongoRepository<Staff, String> {}
