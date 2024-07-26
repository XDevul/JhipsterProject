package com.jhipster.test.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlloyHttpDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlloyHttpDTO.class);
        AlloyHttpDTO alloyHttpDTO1 = new AlloyHttpDTO();
        alloyHttpDTO1.setId(1L);
        AlloyHttpDTO alloyHttpDTO2 = new AlloyHttpDTO();
        assertThat(alloyHttpDTO1).isNotEqualTo(alloyHttpDTO2);
        alloyHttpDTO2.setId(alloyHttpDTO1.getId());
        assertThat(alloyHttpDTO1).isEqualTo(alloyHttpDTO2);
        alloyHttpDTO2.setId(2L);
        assertThat(alloyHttpDTO1).isNotEqualTo(alloyHttpDTO2);
        alloyHttpDTO1.setId(null);
        assertThat(alloyHttpDTO1).isNotEqualTo(alloyHttpDTO2);
    }
}
