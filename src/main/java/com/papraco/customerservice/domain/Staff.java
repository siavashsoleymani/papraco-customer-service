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
 * A Staff.
 */
@Document(collection = "staff")
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("family")
    private String family;

    @Field("phone_number")
    private String phoneNumber;

    @Field("email")
    private String email;

    @Field("desciption")
    private String desciption;

    @DBRef
    @Field("note")
    @JsonIgnoreProperties(value = { "staff" }, allowSetters = true)
    private Set<Note> notes = new HashSet<>();

    @DBRef
    @Field("profilePhoto")
    @JsonIgnoreProperties(value = { "staff", "offer", "product" }, allowSetters = true)
    private Set<File> profilePhotos = new HashSet<>();

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

    public Staff id(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Staff name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return this.family;
    }

    public Staff family(String family) {
        this.family = family;
        return this;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Staff phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public Staff email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesciption() {
        return this.desciption;
    }

    public Staff desciption(String desciption) {
        this.desciption = desciption;
        return this;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public Set<Note> getNotes() {
        return this.notes;
    }

    public Staff notes(Set<Note> notes) {
        this.setNotes(notes);
        return this;
    }

    public Staff addNote(Note note) {
        this.notes.add(note);
        note.setStaff(this);
        return this;
    }

    public Staff removeNote(Note note) {
        this.notes.remove(note);
        note.setStaff(null);
        return this;
    }

    public void setNotes(Set<Note> notes) {
        if (this.notes != null) {
            this.notes.forEach(i -> i.setStaff(null));
        }
        if (notes != null) {
            notes.forEach(i -> i.setStaff(this));
        }
        this.notes = notes;
    }

    public Set<File> getProfilePhotos() {
        return this.profilePhotos;
    }

    public Staff profilePhotos(Set<File> files) {
        this.setProfilePhotos(files);
        return this;
    }

    public Staff addProfilePhoto(File file) {
        this.profilePhotos.add(file);
        file.setStaff(this);
        return this;
    }

    public Staff removeProfilePhoto(File file) {
        this.profilePhotos.remove(file);
        file.setStaff(null);
        return this;
    }

    public void setProfilePhotos(Set<File> files) {
        if (this.profilePhotos != null) {
            this.profilePhotos.forEach(i -> i.setStaff(null));
        }
        if (files != null) {
            files.forEach(i -> i.setStaff(this));
        }
        this.profilePhotos = files;
    }

    public Company getCompany() {
        return this.company;
    }

    public Staff company(Company company) {
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
        if (!(o instanceof Staff)) {
            return false;
        }
        return id != null && id.equals(((Staff) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Staff{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", family='" + getFamily() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", desciption='" + getDesciption() + "'" +
            "}";
    }
}
