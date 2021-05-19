package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.IngredientDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ingredient} and its DTO {@link IngredientDTO}.
 */
@Mapper(componentModel = "spring", uses = { BeerMapper.class })
public interface IngredientMapper extends EntityMapper<IngredientDTO, Ingredient> {
    @Mapping(target = "beer", source = "beer", qualifiedByName = "id")
    IngredientDTO toDto(Ingredient s);
}
