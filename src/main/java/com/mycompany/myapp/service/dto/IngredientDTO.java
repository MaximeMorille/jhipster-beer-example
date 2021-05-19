package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Ingredient} entity.
 */
public class IngredientDTO implements Serializable {

    private Long id;

    private String uuid;

    private String name;

    private Double quantity;

    private String unit;

    private String countryName;

    private BeerDTO beer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public BeerDTO getBeer() {
        return beer;
    }

    public void setBeer(BeerDTO beer) {
        this.beer = beer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IngredientDTO)) {
            return false;
        }

        IngredientDTO ingredientDTO = (IngredientDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ingredientDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IngredientDTO{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", unit='" + getUnit() + "'" +
            ", countryName='" + getCountryName() + "'" +
            ", beer=" + getBeer() +
            "}";
    }
}
