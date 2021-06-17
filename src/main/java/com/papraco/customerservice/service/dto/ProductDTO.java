package com.papraco.customerservice.service.dto;

import com.papraco.customerservice.domain.enumeration.ProductKind;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.papraco.customerservice.domain.Product} entity.
 */
public class ProductDTO implements Serializable {

    private String id;

    private String name;

    private ProductKind productKind;

    private Long remainCount;

    private Long reservedCount;

    private BigDecimal boughtCost;

    private Instant boughtAt;

    private String factureNumber;

    private String description;

    private String productOrigin;

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

    public ProductKind getProductKind() {
        return productKind;
    }

    public void setProductKind(ProductKind productKind) {
        this.productKind = productKind;
    }

    public Long getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(Long remainCount) {
        this.remainCount = remainCount;
    }

    public Long getReservedCount() {
        return reservedCount;
    }

    public void setReservedCount(Long reservedCount) {
        this.reservedCount = reservedCount;
    }

    public BigDecimal getBoughtCost() {
        return boughtCost;
    }

    public void setBoughtCost(BigDecimal boughtCost) {
        this.boughtCost = boughtCost;
    }

    public Instant getBoughtAt() {
        return boughtAt;
    }

    public void setBoughtAt(Instant boughtAt) {
        this.boughtAt = boughtAt;
    }

    public String getFactureNumber() {
        return factureNumber;
    }

    public void setFactureNumber(String factureNumber) {
        this.factureNumber = factureNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductOrigin() {
        return productOrigin;
    }

    public void setProductOrigin(String productOrigin) {
        this.productOrigin = productOrigin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDTO)) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDTO{" +
            "id='" + getId() + "'" +
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
