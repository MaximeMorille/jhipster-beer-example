package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.BeerColor;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Beer} entity.
 */
public class BeerDTO implements Serializable {

    private Long id;

    private String uuid;

    private String name;

    private Double abv;

    private Double ebc;

    private Double ibu;

    private String ownerUuid;

    private BeerColor color;

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

    public Double getAbv() {
        return abv;
    }

    public void setAbv(Double abv) {
        this.abv = abv;
    }

    public Double getEbc() {
        return ebc;
    }

    public void setEbc(Double ebc) {
        this.ebc = ebc;
    }

    public Double getIbu() {
        return ibu;
    }

    public void setIbu(Double ibu) {
        this.ibu = ibu;
    }

    public String getOwnerUuid() {
        return ownerUuid;
    }

    public void setOwnerUuid(String ownerUuid) {
        this.ownerUuid = ownerUuid;
    }

    public BeerColor getColor() {
        return color;
    }

    public void setColor(BeerColor color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BeerDTO)) {
            return false;
        }

        BeerDTO beerDTO = (BeerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, beerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BeerDTO{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", name='" + getName() + "'" +
            ", abv=" + getAbv() +
            ", ebc=" + getEbc() +
            ", ibu=" + getIbu() +
            ", ownerUuid='" + getOwnerUuid() + "'" +
            ", color='" + getColor() + "'" +
            "}";
    }
}
