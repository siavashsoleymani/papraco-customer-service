package com.papraco.customerservice.service.impl;

import com.papraco.customerservice.domain.Facture;
import com.papraco.customerservice.repository.FactureRepository;
import com.papraco.customerservice.service.FactureService;
import com.papraco.customerservice.service.dto.FactureDTO;
import com.papraco.customerservice.service.mapper.FactureMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Facture}.
 */
@Service
public class FactureServiceImpl implements FactureService {

    private final Logger log = LoggerFactory.getLogger(FactureServiceImpl.class);

    private final FactureRepository factureRepository;

    private final FactureMapper factureMapper;

    public FactureServiceImpl(FactureRepository factureRepository, FactureMapper factureMapper) {
        this.factureRepository = factureRepository;
        this.factureMapper = factureMapper;
    }

    @Override
    public FactureDTO save(FactureDTO factureDTO) {
        log.debug("Request to save Facture : {}", factureDTO);
        Facture facture = factureMapper.toEntity(factureDTO);
        facture = factureRepository.save(facture);
        return factureMapper.toDto(facture);
    }

    @Override
    public Optional<FactureDTO> partialUpdate(FactureDTO factureDTO) {
        log.debug("Request to partially update Facture : {}", factureDTO);

        return factureRepository
            .findById(factureDTO.getId())
            .map(
                existingFacture -> {
                    factureMapper.partialUpdate(existingFacture, factureDTO);
                    return existingFacture;
                }
            )
            .map(factureRepository::save)
            .map(factureMapper::toDto);
    }

    @Override
    public Page<FactureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Factures");
        return factureRepository.findAll(pageable).map(factureMapper::toDto);
    }

    @Override
    public Optional<FactureDTO> findOne(String id) {
        log.debug("Request to get Facture : {}", id);
        return factureRepository.findById(id).map(factureMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Facture : {}", id);
        factureRepository.deleteById(id);
    }
}
