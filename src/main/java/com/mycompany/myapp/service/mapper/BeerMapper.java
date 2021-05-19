package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.BeerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Beer} and its DTO {@link BeerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BeerMapper extends EntityMapper<BeerDTO, Beer> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BeerDTO toDtoId(Beer beer);
}
