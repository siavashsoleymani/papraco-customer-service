package com.papraco.customerservice.domain;

import com.papraco.customerservice.domain.enumeration.MeasureType;
import java.io.Serializable;
import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A FactureItem.
 */
@Document(collection = "facture_item")
public class FactureItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("description")
    private String description;

    @Field("count")
    private Integer count;

    @Field("measure_type")
    private MeasureType measureType;

    @Field("amount")
    private BigDecimal amount;

    @Field("total_amount")
    private BigDecimal totalAmount;

    @Field("discount")
    private BigDecimal discount;

    @Field("tax")
    private BigDecimal tax;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FactureItem id(String id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public FactureItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCount() {
        return this.count;
    }

    public FactureItem count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public MeasureType getMeasureType() {
        return this.measureType;
    }

    public FactureItem measureType(MeasureType measureType) {
        this.measureType = measureType;
        return this;
    }

    public void setMeasureType(MeasureType measureType) {
        this.measureType = measureType;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public FactureItem amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    public FactureItem totalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getDiscount() {
        return this.discount;
    }

    public FactureItem discount(BigDecimal discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTax() {
        return this.tax;
    }

    public FactureItem tax(BigDecimal tax) {
        this.tax = tax;
        return this;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FactureItem)) {
            return false;
        }
        return id != null && id.equals(((FactureItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactureItem{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", count=" + getCount() +
            ", measureType='" + getMeasureType() + "'" +
            ", amount=" + getAmount() +
            ", totalAmount=" + getTotalAmount() +
            ", discount=" + getDiscount() +
            ", tax=" + getTax() +
            "}";
    }
}
