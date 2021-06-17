package com.papraco.customerservice.service.dto;

import com.papraco.customerservice.domain.enumeration.MeasureType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.papraco.customerservice.domain.FactureItem} entity.
 */
public class FactureItemDTO implements Serializable {

    private String id;

    private String description;

    private Integer count;

    private MeasureType measureType;

    private BigDecimal amount;

    private BigDecimal totalAmount;

    private BigDecimal discount;

    private BigDecimal tax;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public MeasureType getMeasureType() {
        return measureType;
    }

    public void setMeasureType(MeasureType measureType) {
        this.measureType = measureType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FactureItemDTO)) {
            return false;
        }

        FactureItemDTO factureItemDTO = (FactureItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, factureItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactureItemDTO{" +
            "id='" + getId() + "'" +
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
