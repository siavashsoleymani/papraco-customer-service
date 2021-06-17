package com.papraco.customerservice.service.dto;

import com.papraco.customerservice.domain.enumeration.FactureType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.papraco.customerservice.domain.Facture} entity.
 */
public class FactureDTO implements Serializable {

    private String id;

    private String title;

    private FactureType factureType;

    private Boolean agreed;

    private String notification;

    private Boolean checkedout;

    private CompanyDTO company;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FactureType getFactureType() {
        return factureType;
    }

    public void setFactureType(FactureType factureType) {
        this.factureType = factureType;
    }

    public Boolean getAgreed() {
        return agreed;
    }

    public void setAgreed(Boolean agreed) {
        this.agreed = agreed;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public Boolean getCheckedout() {
        return checkedout;
    }

    public void setCheckedout(Boolean checkedout) {
        this.checkedout = checkedout;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FactureDTO)) {
            return false;
        }

        FactureDTO factureDTO = (FactureDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, factureDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactureDTO{" +
            "id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", factureType='" + getFactureType() + "'" +
            ", agreed='" + getAgreed() + "'" +
            ", notification='" + getNotification() + "'" +
            ", checkedout='" + getCheckedout() + "'" +
            ", company='" + getCompany() + "'" +
            "}";
    }
}
