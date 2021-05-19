package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.BeerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Beer}.
 */
public interface BeerService {
    /**
     * Save a beer.
     *
     * @param beerDTO the entity to save.
     * @return the persisted entity.
     */
    BeerDTO save(BeerDTO beerDTO);

    /**
     * Partially updates a beer.
     *
     * @param beerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BeerDTO> partialUpdate(BeerDTO beerDTO);

    /**
     * Get all the beers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BeerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" beer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BeerDTO> findOne(Long id);

    /**
     * Delete the "id" beer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
