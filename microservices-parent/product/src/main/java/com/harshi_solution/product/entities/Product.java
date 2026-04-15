package com.harshi_solution.product.entities;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(unique = true)
    @Length(min = 3, max = 30, message = "brandName should be min 3 and max 12")
    @NotBlank(message = "brandName is mandatory")
    private String productCode;

    private String productName;

    private String pType;

    private BigDecimal basePrice;

    public Product() {
        // Default constructor
    }

    public Product(String productCode, String pType) {
        this.productCode = productCode;
        this.pType = pType;
    }

    // Getters and setters

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getPType() {
        return pType;
    }

    public void setPType(String pType) {
        this.pType = pType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", productCode='" + productCode + '\'' + ", basePrice='"
                + basePrice
                + '\'' + ", pType='" + pType + '\'' + '}';
    }
}
