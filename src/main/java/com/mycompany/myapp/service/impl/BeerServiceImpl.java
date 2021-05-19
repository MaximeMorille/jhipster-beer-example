package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Beer;
import com.mycompany.myapp.repository.BeerRepository;
import com.mycompany.myapp.service.BeerService;
import com.mycompany.myapp.service.dto.BeerDTO;
import com.mycompany.myapp.service.mapper.BeerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Beer}.
 */
@Service
@Transactional
public class BeerServiceImpl implements BeerService {

    private final Logger log = LoggerFactory.getLogger(BeerServiceImpl.class);

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper;

    public BeerServiceImpl(BeerRepository beerRepository, BeerMapper beerMapper) {
        this.beerRepository = beerRepository;
        this.beerMapper = beerMapper;
    }

    @Override
    public BeerDTO save(BeerDTO beerDTO) {
        log.debug("Request to save Beer : {}", beerDTO);
        Beer beer = beerMapper.toEntity(beerDTO);
        beer = beerRepository.save(beer);
        return beerMapper.toDto(beer);
    }

    @Override
    public Optional<BeerDTO> partialUpdate(BeerDTO beerDTO) {
        log.debug("Request to partially update Beer : {}", beerDTO);

        return beerRepository
            .findById(beerDTO.getId())
            .map(
                existingBeer -> {
                    beerMapper.partialUpdate(existingBeer, beerDTO);
                    return existingBeer;
                }
            )
            .map(beerRepository::save)
            .map(beerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BeerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Beers");
        return beerRepository.findAll(pageable).map(beerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BeerDTO> findOne(Long id) {
        log.debug("Request to get Beer : {}", id);
        return beerRepository.findById(id).map(beerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Beer : {}", id);
        beerRepository.deleteById(id);
    }
}
