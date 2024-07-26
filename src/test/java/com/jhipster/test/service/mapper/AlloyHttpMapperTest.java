package com.jhipster.test.service.mapper;

import static com.jhipster.test.domain.AlloyHttpAsserts.*;
import static com.jhipster.test.domain.AlloyHttpTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlloyHttpMapperTest {

    private AlloyHttpMapper alloyHttpMapper;

    @BeforeEach
    void setUp() {
        alloyHttpMapper = new AlloyHttpMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAlloyHttpSample1();
        var actual = alloyHttpMapper.toEntity(alloyHttpMapper.toDto(expected));
        assertAlloyHttpAllPropertiesEquals(expected, actual);
    }
}
