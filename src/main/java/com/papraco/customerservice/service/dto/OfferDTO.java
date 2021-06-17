package com.papraco.customerservice.service.dto;

import com.papraco.customerservice.domain.enumeration.OfferType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.papraco.customerservice.domain.Offer} entity.
 */
public class OfferDTO implements Serializable {

    private String id;

    private String title;

    private String body;

    private OfferType offerType;

    private Long notifInterval;

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public void setOfferType(OfferType offerType) {
        this.offerType = offerType;
    }

    public Long getNotifInterval() {
        return notifInterval;
    }

    public void setNotifInterval(Long notifInterval) {
        this.notifInterval = notifInterval;
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
        if (!(o instanceof OfferDTO)) {
            return false;
        }

        OfferDTO offerDTO = (OfferDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, offerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfferDTO{" +
            "id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", body='" + getBody() + "'" +
            ", offerType='" + getOfferType() + "'" +
            ", notifInterval=" + getNotifInterval() +
            ", company='" + getCompany() + "'" +
            "}";
    }
}
