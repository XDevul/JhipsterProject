package com.jhipster.test.domain;

import static com.jhipster.test.domain.AlloyHttpTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlloyHttpTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlloyHttp.class);
        AlloyHttp alloyHttp1 = getAlloyHttpSample1();
        AlloyHttp alloyHttp2 = new AlloyHttp();
        assertThat(alloyHttp1).isNotEqualTo(alloyHttp2);

        alloyHttp2.setId(alloyHttp1.getId());
        assertThat(alloyHttp1).isEqualTo(alloyHttp2);

        alloyHttp2 = getAlloyHttpSample2();
        assertThat(alloyHttp1).isNotEqualTo(alloyHttp2);
    }
}
