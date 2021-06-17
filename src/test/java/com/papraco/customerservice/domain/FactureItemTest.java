package com.papraco.customerservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.papraco.customerservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FactureItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FactureItem.class);
        FactureItem factureItem1 = new FactureItem();
        factureItem1.setId("id1");
        FactureItem factureItem2 = new FactureItem();
        factureItem2.setId(factureItem1.getId());
        assertThat(factureItem1).isEqualTo(factureItem2);
        factureItem2.setId("id2");
        assertThat(factureItem1).isNotEqualTo(factureItem2);
        factureItem1.setId(null);
        assertThat(factureItem1).isNotEqualTo(factureItem2);
    }
}
