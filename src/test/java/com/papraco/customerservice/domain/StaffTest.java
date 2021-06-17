package com.papraco.customerservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.papraco.customerservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StaffTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Staff.class);
        Staff staff1 = new Staff();
        staff1.setId("id1");
        Staff staff2 = new Staff();
        staff2.setId(staff1.getId());
        assertThat(staff1).isEqualTo(staff2);
        staff2.setId("id2");
        assertThat(staff1).isNotEqualTo(staff2);
        staff1.setId(null);
        assertThat(staff1).isNotEqualTo(staff2);
    }
}
