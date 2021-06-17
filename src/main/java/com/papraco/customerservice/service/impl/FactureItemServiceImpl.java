package com.papraco.customerservice.service.impl;

import com.papraco.customerservice.domain.FactureItem;
import com.papraco.customerservice.repository.FactureItemRepository;
import com.papraco.customerservice.service.FactureItemService;
import com.papraco.customerservice.service.dto.FactureItemDTO;
import com.papraco.customerservice.service.mapper.FactureItemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link FactureItem}.
 */
@Service
public class FactureItemServiceImpl implements FactureItemService {

    private final Logger log = LoggerFactory.getLogger(FactureItemServiceImpl.class);

    private final FactureItemRepository factureItemRepository;

    private final FactureItemMapper factureItemMapper;

    public FactureItemServiceImpl(FactureItemRepository factureItemRepository, FactureItemMapper factureItemMapper) {
        this.factureItemRepository = factureItemRepository;
        this.factureItemMapper = factureItemMapper;
    }

    @Override
    public FactureItemDTO save(FactureItemDTO factureItemDTO) {
        log.debug("Request to save FactureItem : {}", factureItemDTO);
        FactureItem factureItem = factureItemMapper.toEntity(factureItemDTO);
        factureItem = factureItemRepository.save(factureItem);
        return factureItemMapper.toDto(factureItem);
    }

    @Override
    public Optional<FactureItemDTO> partialUpdate(FactureItemDTO factureItemDTO) {
        log.debug("Request to partially update FactureItem : {}", factureItemDTO);

        return factureItemRepository
            .findById(factureItemDTO.getId())
            .map(
                existingFactureItem -> {
                    factureItemMapper.partialUpdate(existingFactureItem, factureItemDTO);
                    return existingFactureItem;
                }
            )
            .map(factureItemRepository::save)
            .map(factureItemMapper::toDto);
    }

    @Override
    public Page<FactureItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FactureItems");
        return factureItemRepository.findAll(pageable).map(factureItemMapper::toDto);
    }

    @Override
    public Optional<FactureItemDTO> findOne(String id) {
        log.debug("Request to get FactureItem : {}", id);
        return factureItemRepository.findById(id).map(factureItemMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete FactureItem : {}", id);
        factureItemRepository.deleteById(id);
    }
}
