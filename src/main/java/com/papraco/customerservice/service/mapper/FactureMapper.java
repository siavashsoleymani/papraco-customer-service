package com.papraco.customerservice.service.mapper;

import com.papraco.customerservice.domain.*;
import com.papraco.customerservice.service.dto.FactureDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Facture} and its DTO {@link FactureDTO}.
 */
@Mapper(componentModel = "spring", uses = { CompanyMapper.class })
public interface FactureMapper extends EntityMapper<FactureDTO, Facture> {
    @Mapping(target = "company", source = "company", qualifiedByName = "id")
    FactureDTO toDto(Facture s);
}
