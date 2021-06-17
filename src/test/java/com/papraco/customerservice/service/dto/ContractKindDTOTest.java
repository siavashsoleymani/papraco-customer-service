package com.papraco.customerservice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.papraco.customerservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContractKindDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractKindDTO.class);
        ContractKindDTO contractKindDTO1 = new ContractKindDTO();
        contractKindDTO1.setId("id1");
        ContractKindDTO contractKindDTO2 = new ContractKindDTO();
        assertThat(contractKindDTO1).isNotEqualTo(contractKindDTO2);
        contractKindDTO2.setId(contractKindDTO1.getId());
        assertThat(contractKindDTO1).isEqualTo(contractKindDTO2);
        contractKindDTO2.setId("id2");
        assertThat(contractKindDTO1).isNotEqualTo(contractKindDTO2);
        contractKindDTO1.setId(null);
        assertThat(contractKindDTO1).isNotEqualTo(contractKindDTO2);
    }
}
