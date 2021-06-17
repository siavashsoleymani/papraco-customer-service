package com.papraco.customerservice.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.papraco.customerservice.domain.ContractKind} entity.
 */
public class ContractKindDTO implements Serializable {

    private String id;

    private String name;

    private ContractDTO contract;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContractDTO getContract() {
        return contract;
    }

    public void setContract(ContractDTO contract) {
        this.contract = contract;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContractKindDTO)) {
            return false;
        }

        ContractKindDTO contractKindDTO = (ContractKindDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contractKindDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContractKindDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", contract='" + getContract() + "'" +
            "}";
    }
}
