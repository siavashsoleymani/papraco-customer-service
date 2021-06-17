package com.papraco.customerservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContractKindMapperTest {

    private ContractKindMapper contractKindMapper;

    @BeforeEach
    public void setUp() {
        contractKindMapper = new ContractKindMapperImpl();
    }
}
