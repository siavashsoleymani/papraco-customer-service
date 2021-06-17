package com.papraco.customerservice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.papraco.customerservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FactureDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FactureDTO.class);
        FactureDTO factureDTO1 = new FactureDTO();
        factureDTO1.setId("id1");
        FactureDTO factureDTO2 = new FactureDTO();
        assertThat(factureDTO1).isNotEqualTo(factureDTO2);
        factureDTO2.setId(factureDTO1.getId());
        assertThat(factureDTO1).isEqualTo(factureDTO2);
        factureDTO2.setId("id2");
        assertThat(factureDTO1).isNotEqualTo(factureDTO2);
        factureDTO1.setId(null);
        assertThat(factureDTO1).isNotEqualTo(factureDTO2);
    }
}
