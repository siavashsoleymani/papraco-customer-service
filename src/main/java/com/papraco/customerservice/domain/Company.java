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
 * A Company.
 */
@Document(collection = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("email")
    private String email;

    @Field("phone_number")
    private String phoneNumber;

    @Field("address")
    private String address;

    @Field("website")
    private String website;

    @Field("photo")
    private byte[] photo;

    @Field("photo_content_type")
    private String photoContentType;

    @DBRef
    @Field("staff")
    @JsonIgnoreProperties(value = { "notes", "profilePhotos", "company" }, allowSetters = true)
    private Set<Staff> staff = new HashSet<>();

    @DBRef
    @Field("offer")
    @JsonIgnoreProperties(value = { "photos", "company" }, allowSetters = true)
    private Set<Offer> offers = new HashSet<>();

    @DBRef
    @Field("contract")
    @JsonIgnoreProperties(value = { "company", "contractKinds" }, allowSetters = true)
    private Set<Contract> contracts = new HashSet<>();

    @DBRef
    @Field("facture")
    @JsonIgnoreProperties(value = { "company" }, allowSetters = true)
    private Set<Facture> factures = new HashSet<>();

    @DBRef
    @Field("userComment")
    @JsonIgnoreProperties(value = { "company" }, allowSetters = true)
    private Set<UserComment> userComments = new HashSet<>();

    @DBRef
    @Field("tags")
    @JsonIgnoreProperties(value = { "companies" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Company id(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public Company email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Company phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public Company address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return this.website;
    }

    public Company website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Company photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Company photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Set<Staff> getStaff() {
        return this.staff;
    }

    public Company staff(Set<Staff> staff) {
        this.setStaff(staff);
        return this;
    }

    public Company addStaff(Staff staff) {
        this.staff.add(staff);
        staff.setCompany(this);
        return this;
    }

    public Company removeStaff(Staff staff) {
        this.staff.remove(staff);
        staff.setCompany(null);
        return this;
    }

    public void setStaff(Set<Staff> staff) {
        if (this.staff != null) {
            this.staff.forEach(i -> i.setCompany(null));
        }
        if (staff != null) {
            staff.forEach(i -> i.setCompany(this));
        }
        this.staff = staff;
    }

    public Set<Offer> getOffers() {
        return this.offers;
    }

    public Company offers(Set<Offer> offers) {
        this.setOffers(offers);
        return this;
    }

    public Company addOffer(Offer offer) {
        this.offers.add(offer);
        offer.setCompany(this);
        return this;
    }

    public Company removeOffer(Offer offer) {
        this.offers.remove(offer);
        offer.setCompany(null);
        return this;
    }

    public void setOffers(Set<Offer> offers) {
        if (this.offers != null) {
            this.offers.forEach(i -> i.setCompany(null));
        }
        if (offers != null) {
            offers.forEach(i -> i.setCompany(this));
        }
        this.offers = offers;
    }

    public Set<Contract> getContracts() {
        return this.contracts;
    }

    public Company contracts(Set<Contract> contracts) {
        this.setContracts(contracts);
        return this;
    }

    public Company addContract(Contract contract) {
        this.contracts.add(contract);
        contract.setCompany(this);
        return this;
    }

    public Company removeContract(Contract contract) {
        this.contracts.remove(contract);
        contract.setCompany(null);
        return this;
    }

    public void setContracts(Set<Contract> contracts) {
        if (this.contracts != null) {
            this.contracts.forEach(i -> i.setCompany(null));
        }
        if (contracts != null) {
            contracts.forEach(i -> i.setCompany(this));
        }
        this.contracts = contracts;
    }

    public Set<Facture> getFactures() {
        return this.factures;
    }

    public Company factures(Set<Facture> factures) {
        this.setFactures(factures);
        return this;
    }

    public Company addFacture(Facture facture) {
        this.factures.add(facture);
        facture.setCompany(this);
        return this;
    }

    public Company removeFacture(Facture facture) {
        this.factures.remove(facture);
        facture.setCompany(null);
        return this;
    }

    public void setFactures(Set<Facture> factures) {
        if (this.factures != null) {
            this.factures.forEach(i -> i.setCompany(null));
        }
        if (factures != null) {
            factures.forEach(i -> i.setCompany(this));
        }
        this.factures = factures;
    }

    public Set<UserComment> getUserComments() {
        return this.userComments;
    }

    public Company userComments(Set<UserComment> userComments) {
        this.setUserComments(userComments);
        return this;
    }

    public Company addUserComment(UserComment userComment) {
        this.userComments.add(userComment);
        userComment.setCompany(this);
        return this;
    }

    public Company removeUserComment(UserComment userComment) {
        this.userComments.remove(userComment);
        userComment.setCompany(null);
        return this;
    }

    public void setUserComments(Set<UserComment> userComments) {
        if (this.userComments != null) {
            this.userComments.forEach(i -> i.setCompany(null));
        }
        if (userComments != null) {
            userComments.forEach(i -> i.setCompany(this));
        }
        this.userComments = userComments;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public Company tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public Company addTag(Tag tag) {
        this.tags.add(tag);
        tag.getCompanies().add(this);
        return this;
    }

    public Company removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getCompanies().remove(this);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", website='" + getWebsite() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            "}";
    }
}
