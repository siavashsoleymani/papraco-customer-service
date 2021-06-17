package com.papraco.customerservice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.papraco.customerservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FactureItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FactureItemDTO.class);
        FactureItemDTO factureItemDTO1 = new FactureItemDTO();
        factureItemDTO1.setId("id1");
        FactureItemDTO factureItemDTO2 = new FactureItemDTO();
        assertThat(factureItemDTO1).isNotEqualTo(factureItemDTO2);
        factureItemDTO2.setId(factureItemDTO1.getId());
        assertThat(factureItemDTO1).isEqualTo(factureItemDTO2);
        factureItemDTO2.setId("id2");
        assertThat(factureItemDTO1).isNotEqualTo(factureItemDTO2);
        factureItemDTO1.setId(null);
        assertThat(factureItemDTO1).isNotEqualTo(factureItemDTO2);
    }
}
