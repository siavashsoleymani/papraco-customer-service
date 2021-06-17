package com.papraco.customerservice.service.mapper;

import com.papraco.customerservice.domain.*;
import com.papraco.customerservice.service.dto.FileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link File} and its DTO {@link FileDTO}.
 */
@Mapper(componentModel = "spring", uses = { StaffMapper.class, OfferMapper.class, ProductMapper.class })
public interface FileMapper extends EntityMapper<FileDTO, File> {
    @Mapping(target = "staff", source = "staff", qualifiedByName = "id")
    @Mapping(target = "offer", source = "offer", qualifiedByName = "id")
    @Mapping(target = "product", source = "product", qualifiedByName = "id")
    FileDTO toDto(File s);
}
