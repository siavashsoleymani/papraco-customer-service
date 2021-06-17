package com.papraco.customerservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.papraco.customerservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserCommentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserComment.class);
        UserComment userComment1 = new UserComment();
        userComment1.setId("id1");
        UserComment userComment2 = new UserComment();
        userComment2.setId(userComment1.getId());
        assertThat(userComment1).isEqualTo(userComment2);
        userComment2.setId("id2");
        assertThat(userComment1).isNotEqualTo(userComment2);
        userComment1.setId(null);
        assertThat(userComment1).isNotEqualTo(userComment2);
    }
}
