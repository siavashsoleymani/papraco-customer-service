package com.papraco.customerservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserCommentMapperTest {

    private UserCommentMapper userCommentMapper;

    @BeforeEach
    public void setUp() {
        userCommentMapper = new UserCommentMapperImpl();
    }
}
