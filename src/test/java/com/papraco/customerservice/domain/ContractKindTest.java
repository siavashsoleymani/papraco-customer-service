package com.papraco.customerservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.papraco.customerservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContractKindTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractKind.class);
        ContractKind contractKind1 = new ContractKind();
        contractKind1.setId("id1");
        ContractKind contractKind2 = new ContractKind();
        contractKind2.setId(contractKind1.getId());
        assertThat(contractKind1).isEqualTo(contractKind2);
        contractKind2.setId("id2");
        assertThat(contractKind1).isNotEqualTo(contractKind2);
        contractKind1.setId(null);
        assertThat(contractKind1).isNotEqualTo(contractKind2);
    }
}
