package com.papraco.customerservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.papraco.customerservice.domain.enumeration.ProductKind;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Product.
 */
@Document(collection = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("product_kind")
    private ProductKind productKind;

    @Field("remain_count")
    private Long remainCount;

    @Field("reserved_count")
    private Long reservedCount;

    @Field("bought_cost")
    private BigDecimal boughtCost;

    @Field("bought_at")
    private Instant boughtAt;

    @Field("facture_number")
    private String factureNumber;

    @Field("description")
    private String description;

    @Field("product_origin")
    private String productOrigin;

    @DBRef
    @Field("contractFiel")
    @JsonIgnoreProperties(value = { "staff", "offer", "product" }, allowSetters = true)
    private Set<File> contractFiels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product id(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductKind getProductKind() {
        return this.productKind;
    }

    public Product productKind(ProductKind productKind) {
        this.productKind = productKind;
        return this;
    }

    public void setProductKind(ProductKind productKind) {
        this.productKind = productKind;
    }

    public Long getRemainCount() {
        return this.remainCount;
    }

    public Product remainCount(Long remainCount) {
        this.remainCount = remainCount;
        return this;
    }

    public void setRemainCount(Long remainCount) {
        this.remainCount = remainCount;
    }

    public Long getReservedCount() {
        return this.reservedCount;
    }

    public Product reservedCount(Long reservedCount) {
        this.reservedCount = reservedCount;
        return this;
    }

    public void setReservedCount(Long reservedCount) {
        this.reservedCount = reservedCount;
    }

    public BigDecimal getBoughtCost() {
        return this.boughtCost;
    }

    public Product boughtCost(BigDecimal boughtCost) {
        this.boughtCost = boughtCost;
        return this;
    }

    public void setBoughtCost(BigDecimal boughtCost) {
        this.boughtCost = boughtCost;
    }

    public Instant getBoughtAt() {
        return this.boughtAt;
    }

    public Product boughtAt(Instant boughtAt) {
        this.boughtAt = boughtAt;
        return this;
    }

    public void setBoughtAt(Instant boughtAt) {
        this.boughtAt = boughtAt;
    }

    public String getFactureNumber() {
        return this.factureNumber;
    }

    public Product factureNumber(String factureNumber) {
        this.factureNumber = factureNumber;
        return this;
    }

    public void setFactureNumber(String factureNumber) {
        this.factureNumber = factureNumber;
    }

    public String getDescription() {
        return this.description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductOrigin() {
        return this.productOrigin;
    }

    public Product productOrigin(String productOrigin) {
        this.productOrigin = productOrigin;
        return this;
    }

    public void setProductOrigin(String productOrigin) {
        this.productOrigin = productOrigin;
    }

    public Set<File> getContractFiels() {
        return this.contractFiels;
    }

    public Product contractFiels(Set<File> files) {
        this.setContractFiels(files);
        return this;
    }

    public Product addContractFiel(File file) {
        this.contractFiels.add(file);
        file.setProduct(this);
        return this;
    }

    public Product removeContractFiel(File file) {
        this.contractFiels.remove(file);
        file.setProduct(null);
        return this;
    }

    public void setContractFiels(Set<File> files) {
        if (this.contractFiels != null) {
            this.contractFiels.forEach(i -> i.setProduct(null));
        }
        if (files != null) {
            files.forEach(i -> i.setProduct(this));
        }
        this.contractFiels = files;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", productKind='" + getProductKind() + "'" +
            ", remainCount=" + getRemainCount() +
            ", reservedCount=" + getReservedCount() +
            ", boughtCost=" + getBoughtCost() +
            ", boughtAt='" + getBoughtAt() + "'" +
            ", factureNumber='" + getFactureNumber() + "'" +
            ", description='" + getDescription() + "'" +
            ", productOrigin='" + getProductOrigin() + "'" +
            "}";
    }
}
