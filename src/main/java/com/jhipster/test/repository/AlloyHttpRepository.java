package com.jhipster.test.repository;

import com.jhipster.test.domain.AlloyHttp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AlloyHttp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlloyHttpRepository extends JpaRepository<AlloyHttp, Long>, JpaSpecificationExecutor<AlloyHttp> {}
