package com.papraco.customerservice.service.mapper;

import com.papraco.customerservice.domain.*;
import com.papraco.customerservice.service.dto.OfferDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Offer} and its DTO {@link OfferDTO}.
 */
@Mapper(componentModel = "spring", uses = { CompanyMapper.class })
public interface OfferMapper extends EntityMapper<OfferDTO, Offer> {
    @Mapping(target = "company", source = "company", qualifiedByName = "id")
    OfferDTO toDto(Offer s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OfferDTO toDtoId(Offer offer);
}
