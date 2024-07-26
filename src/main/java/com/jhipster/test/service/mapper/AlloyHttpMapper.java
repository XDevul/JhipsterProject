package com.jhipster.test.service.mapper;

import com.jhipster.test.domain.AlloyHttp;
import com.jhipster.test.service.dto.AlloyHttpDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AlloyHttp} and its DTO {@link AlloyHttpDTO}.
 */
@Mapper(componentModel = "spring")
public interface AlloyHttpMapper extends EntityMapper<AlloyHttpDTO, AlloyHttp> {}
