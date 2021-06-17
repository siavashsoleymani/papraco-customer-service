package com.papraco.customerservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FactureItemMapperTest {

    private FactureItemMapper factureItemMapper;

    @BeforeEach
    public void setUp() {
        factureItemMapper = new FactureItemMapperImpl();
    }
}
