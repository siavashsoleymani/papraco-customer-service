package com.papraco.customerservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.papraco.customerservice.domain.enumeration.OfferType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Offer.
 */
@Document(collection = "offer")
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("body")
    private String body;

    @Field("offer_type")
    private OfferType offerType;

    @Field("notif_interval")
    private Long notifInterval;

    @DBRef
    @Field("photo")
    @JsonIgnoreProperties(value = { "staff", "offer", "product" }, allowSetters = true)
    private Set<File> photos = new HashSet<>();

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

    public Offer id(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Offer title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return this.body;
    }

    public Offer body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public OfferType getOfferType() {
        return this.offerType;
    }

    public Offer offerType(OfferType offerType) {
        this.offerType = offerType;
        return this;
    }

    public void setOfferType(OfferType offerType) {
        this.offerType = offerType;
    }

    public Long getNotifInterval() {
        return this.notifInterval;
    }

    public Offer notifInterval(Long notifInterval) {
        this.notifInterval = notifInterval;
        return this;
    }

    public void setNotifInterval(Long notifInterval) {
        this.notifInterval = notifInterval;
    }

    public Set<File> getPhotos() {
        return this.photos;
    }

    public Offer photos(Set<File> files) {
        this.setPhotos(files);
        return this;
    }

    public Offer addPhoto(File file) {
        this.photos.add(file);
        file.setOffer(this);
        return this;
    }

    public Offer removePhoto(File file) {
        this.photos.remove(file);
        file.setOffer(null);
        return this;
    }

    public void setPhotos(Set<File> files) {
        if (this.photos != null) {
            this.photos.forEach(i -> i.setOffer(null));
        }
        if (files != null) {
            files.forEach(i -> i.setOffer(this));
        }
        this.photos = files;
    }

    public Company getCompany() {
        return this.company;
    }

    public Offer company(Company company) {
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
        if (!(o instanceof Offer)) {
            return false;
        }
        return id != null && id.equals(((Offer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Offer{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", body='" + getBody() + "'" +
            ", offerType='" + getOfferType() + "'" +
            ", notifInterval=" + getNotifInterval() +
            "}";
    }
}
