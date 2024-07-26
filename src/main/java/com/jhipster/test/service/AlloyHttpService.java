package com.jhipster.test.service;

import com.jhipster.test.domain.AlloyHttp;
import com.jhipster.test.repository.AlloyHttpRepository;
import com.jhipster.test.service.dto.AlloyHttpDTO;
import com.jhipster.test.service.mapper.AlloyHttpMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jhipster.test.domain.AlloyHttp}.
 */
@Service
@Transactional
public class AlloyHttpService {

    private static final Logger log = LoggerFactory.getLogger(AlloyHttpService.class);

    private final AlloyHttpRepository alloyHttpRepository;

    private final AlloyHttpMapper alloyHttpMapper;

    public AlloyHttpService(AlloyHttpRepository alloyHttpRepository, AlloyHttpMapper alloyHttpMapper) {
        this.alloyHttpRepository = alloyHttpRepository;
        this.alloyHttpMapper = alloyHttpMapper;
    }

    /**
     * Save a alloyHttp.
     *
     * @param alloyHttpDTO the entity to save.
     * @return the persisted entity.
     */
    public AlloyHttpDTO save(AlloyHttpDTO alloyHttpDTO) {
        log.debug("Request to save AlloyHttp : {}", alloyHttpDTO);
        AlloyHttp alloyHttp = alloyHttpMapper.toEntity(alloyHttpDTO);
        alloyHttp = alloyHttpRepository.save(alloyHttp);
        return alloyHttpMapper.toDto(alloyHttp);
    }

    /**
     * Update a alloyHttp.
     *
     * @param alloyHttpDTO the entity to save.
     * @return the persisted entity.
     */
    public AlloyHttpDTO update(AlloyHttpDTO alloyHttpDTO) {
        log.debug("Request to update AlloyHttp : {}", alloyHttpDTO);
        AlloyHttp alloyHttp = alloyHttpMapper.toEntity(alloyHttpDTO);
        alloyHttp = alloyHttpRepository.save(alloyHttp);
        return alloyHttpMapper.toDto(alloyHttp);
    }

    /**
     * Partially update a alloyHttp.
     *
     * @param alloyHttpDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AlloyHttpDTO> partialUpdate(AlloyHttpDTO alloyHttpDTO) {
        log.debug("Request to partially update AlloyHttp : {}", alloyHttpDTO);

        return alloyHttpRepository
            .findById(alloyHttpDTO.getId())
            .map(existingAlloyHttp -> {
                alloyHttpMapper.partialUpdate(existingAlloyHttp, alloyHttpDTO);

                return existingAlloyHttp;
            })
            .map(alloyHttpRepository::save)
            .map(alloyHttpMapper::toDto);
    }

    /**
     * Get one alloyHttp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AlloyHttpDTO> findOne(Long id) {
        log.debug("Request to get AlloyHttp : {}", id);
        return alloyHttpRepository.findById(id).map(alloyHttpMapper::toDto);
    }

    /**
     * Delete the alloyHttp by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AlloyHttp : {}", id);
        alloyHttpRepository.deleteById(id);
    }
}
