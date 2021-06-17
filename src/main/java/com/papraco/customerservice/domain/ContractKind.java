package com.papraco.customerservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ContractKind.
 */
@Document(collection = "contract_kind")
public class ContractKind implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @DBRef
    @Field("contract")
    @JsonIgnoreProperties(value = { "company", "contractKinds" }, allowSetters = true)
    private Contract contract;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ContractKind id(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ContractKind name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contract getContract() {
        return this.contract;
    }

    public ContractKind contract(Contract contract) {
        this.setContract(contract);
        return this;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContractKind)) {
            return false;
        }
        return id != null && id.equals(((ContractKind) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContractKind{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
