package com.harshi_solution.product.dto;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductRequestDTO {

    @NotBlank(message = "Product name is mandatory")
    @Length(min = 3, max = 50, message = "Product name must be between 3 and 50 characters")
    private String productName;

    @NotBlank(message = "Product code is mandatory")
    @Length(min = 3, max = 10, message = "Product code must be between 3 and 10 characters")
    private String productCode;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal basePrice;

      private String pType;

    public BigDecimal getBasePrice() {
        return basePrice;
    }

      public void setBasePrice(BigDecimal basePrice) {
          this.basePrice = basePrice;
      }

      public String getpType() {
          return pType;
      }

      public void setpType(String pType) {
          this.pType = pType;
      }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
