package com.papraco.customerservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Contract.
 */
@Document(collection = "contract")
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("body")
    private String body;

    @Field("agreed")
    private Boolean agreed;

    @DBRef
    @Field("company")
    @JsonIgnoreProperties(value = { "staff", "offers", "contracts", "factures", "userComments", "tags" }, allowSetters = true)
    private Company company;

    @DBRef
    @Field("contractKind")
    @JsonIgnoreProperties(value = { "contract" }, allowSetters = true)
    private Set<ContractKind> contractKinds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Contract id(String id) {
        this.id = id;
        return this;
    }

    public String getBody() {
        return this.body;
    }

    public Contract body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean getAgreed() {
        return this.agreed;
    }

    public Contract agreed(Boolean agreed) {
        this.agreed = agreed;
        return this;
    }

    public void setAgreed(Boolean agreed) {
        this.agreed = agreed;
    }

    public Company getCompany() {
        return this.company;
    }

    public Contract company(Company company) {
        this.setCompany(company);
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<ContractKind> getContractKinds() {
        return this.contractKinds;
    }

    public Contract contractKinds(Set<ContractKind> contractKinds) {
        this.setContractKinds(contractKinds);
        return this;
    }

    public Contract addContractKind(ContractKind contractKind) {
        this.contractKinds.add(contractKind);
        contractKind.setContract(this);
        return this;
    }

    public Contract removeContractKind(ContractKind contractKind) {
        this.contractKinds.remove(contractKind);
        contractKind.setContract(null);
        return this;
    }

    public void setContractKinds(Set<ContractKind> contractKinds) {
        if (this.contractKinds != null) {
            this.contractKinds.forEach(i -> i.setContract(null));
        }
        if (contractKinds != null) {
            contractKinds.forEach(i -> i.setContract(this));
        }
        this.contractKinds = contractKinds;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contract)) {
            return false;
        }
        return id != null && id.equals(((Contract) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contract{" +
            "id=" + getId() +
            ", body='" + getBody() + "'" +
            ", agreed='" + getAgreed() + "'" +
            "}";
    }
}
