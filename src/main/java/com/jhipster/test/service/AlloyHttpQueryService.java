package com.jhipster.test.service;

import com.jhipster.test.domain.*; // for static metamodels
import com.jhipster.test.domain.AlloyHttp;
import com.jhipster.test.repository.AlloyHttpRepository;
import com.jhipster.test.service.criteria.AlloyHttpCriteria;
import com.jhipster.test.service.dto.AlloyHttpDTO;
import com.jhipster.test.service.mapper.AlloyHttpMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link AlloyHttp} entities in the database.
 * The main input is a {@link AlloyHttpCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link AlloyHttpDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AlloyHttpQueryService extends QueryService<AlloyHttp> {

    private static final Logger log = LoggerFactory.getLogger(AlloyHttpQueryService.class);

    private final AlloyHttpRepository alloyHttpRepository;

    private final AlloyHttpMapper alloyHttpMapper;

    public AlloyHttpQueryService(AlloyHttpRepository alloyHttpRepository, AlloyHttpMapper alloyHttpMapper) {
        this.alloyHttpRepository = alloyHttpRepository;
        this.alloyHttpMapper = alloyHttpMapper;
    }

    /**
     * Return a {@link Page} of {@link AlloyHttpDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AlloyHttpDTO> findByCriteria(AlloyHttpCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AlloyHttp> specification = createSpecification(criteria);
        return alloyHttpRepository.findAll(specification, page).map(alloyHttpMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AlloyHttpCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AlloyHttp> specification = createSpecification(criteria);
        return alloyHttpRepository.count(specification);
    }

    /**
     * Function to convert {@link AlloyHttpCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AlloyHttp> createSpecification(AlloyHttpCriteria criteria) {
        Specification<AlloyHttp> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AlloyHttp_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AlloyHttp_.name));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), AlloyHttp_.address));
            }
            if (criteria.getModul() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModul(), AlloyHttp_.modul));
            }
            if (criteria.getApi() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApi(), AlloyHttp_.api));
            }
            if (criteria.getEnv() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEnv(), AlloyHttp_.env));
            }
            if (criteria.getHostname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHostname(), AlloyHttp_.hostname));
            }
            if (criteria.getCreateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateTime(), AlloyHttp_.createTime));
            }
            if (criteria.getUpdateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateTime(), AlloyHttp_.updateTime));
            }
        }
        return specification;
    }
}
