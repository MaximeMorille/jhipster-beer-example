package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.BeerColor;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Beer.
 */
@Entity
@Table(name = "beer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Beer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "abv")
    private Double abv;

    @Column(name = "ebc")
    private Double ebc;

    @Column(name = "ibu")
    private Double ibu;

    @Column(name = "owner_uuid")
    private String ownerUuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "color")
    private BeerColor color;

    @OneToMany(mappedBy = "beer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "beer" }, allowSetters = true)
    private Set<Ingredient> ingredients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Beer id(Long id) {
        this.id = id;
        return this;
    }

    public String getUuid() {
        return this.uuid;
    }

    public Beer uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }

    public Beer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAbv() {
        return this.abv;
    }

    public Beer abv(Double abv) {
        this.abv = abv;
        return this;
    }

    public void setAbv(Double abv) {
        this.abv = abv;
    }

    public Double getEbc() {
        return this.ebc;
    }

    public Beer ebc(Double ebc) {
        this.ebc = ebc;
        return this;
    }

    public void setEbc(Double ebc) {
        this.ebc = ebc;
    }

    public Double getIbu() {
        return this.ibu;
    }

    public Beer ibu(Double ibu) {
        this.ibu = ibu;
        return this;
    }

    public void setIbu(Double ibu) {
        this.ibu = ibu;
    }

    public String getOwnerUuid() {
        return this.ownerUuid;
    }

    public Beer ownerUuid(String ownerUuid) {
        this.ownerUuid = ownerUuid;
        return this;
    }

    public void setOwnerUuid(String ownerUuid) {
        this.ownerUuid = ownerUuid;
    }

    public BeerColor getColor() {
        return this.color;
    }

    public Beer color(BeerColor color) {
        this.color = color;
        return this;
    }

    public void setColor(BeerColor color) {
        this.color = color;
    }

    public Set<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public Beer ingredients(Set<Ingredient> ingredients) {
        this.setIngredients(ingredients);
        return this;
    }

    public Beer addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        ingredient.setBeer(this);
        return this;
    }

    public Beer removeIngredient(Ingredient ingredient) {
        this.ingredients.remove(ingredient);
        ingredient.setBeer(null);
        return this;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        if (this.ingredients != null) {
            this.ingredients.forEach(i -> i.setBeer(null));
        }
        if (ingredients != null) {
            ingredients.forEach(i -> i.setBeer(this));
        }
        this.ingredients = ingredients;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beer)) {
            return false;
        }
        return id != null && id.equals(((Beer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beer{" +
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
