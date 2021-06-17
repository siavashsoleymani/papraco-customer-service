package com.papraco.customerservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.papraco.customerservice.domain.enumeration.FactureType;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Facture.
 */
@Document(collection = "facture")
public class Facture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("facture_type")
    private FactureType factureType;

    @Field("agreed")
    private Boolean agreed;

    @Field("notification")
    private String notification;

    @Field("checkedout")
    private Boolean checkedout;

    @DBRef
    @Field("company")
    @JsonIgnoreProperties(value = { "staff", "offers", "contracts", "factures", "userComments", "tags" }, allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Facture id(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Facture title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FactureType getFactureType() {
        return this.factureType;
    }

    public Facture factureType(FactureType factureType) {
        this.factureType = factureType;
        return this;
    }

    public void setFactureType(FactureType factureType) {
        this.factureType = factureType;
    }

    public Boolean getAgreed() {
        return this.agreed;
    }

    public Facture agreed(Boolean agreed) {
        this.agreed = agreed;
        return this;
    }

    public void setAgreed(Boolean agreed) {
        this.agreed = agreed;
    }

    public String getNotification() {
        return this.notification;
    }

    public Facture notification(String notification) {
        this.notification = notification;
        return this;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public Boolean getCheckedout() {
        return this.checkedout;
    }

    public Facture checkedout(Boolean checkedout) {
        this.checkedout = checkedout;
        return this;
    }

    public void setCheckedout(Boolean checkedout) {
        this.checkedout = checkedout;
    }

    public Company getCompany() {
        return this.company;
    }

    public Facture company(Company company) {
        this.setCompany(company);
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Facture)) {
            return false;
        }
        return id != null && id.equals(((Facture) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Facture{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", factureType='" + getFactureType() + "'" +
            ", agreed='" + getAgreed() + "'" +
            ", notification='" + getNotification() + "'" +
            ", checkedout='" + getCheckedout() + "'" +
            "}";
    }
}
