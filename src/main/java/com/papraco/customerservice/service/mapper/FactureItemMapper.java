package com.papraco.customerservice.service.mapper;

import com.papraco.customerservice.domain.*;
import com.papraco.customerservice.service.dto.FactureItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FactureItem} and its DTO {@link FactureItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FactureItemMapper extends EntityMapper<FactureItemDTO, FactureItem> {}
