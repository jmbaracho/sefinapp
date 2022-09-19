package br.com.sefin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.sefin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RawDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RawData.class);
        RawData rawData1 = new RawData();
        rawData1.setId(1L);
        RawData rawData2 = new RawData();
        rawData2.setId(rawData1.getId());
        assertThat(rawData1).isEqualTo(rawData2);
        rawData2.setId(2L);
        assertThat(rawData1).isNotEqualTo(rawData2);
        rawData1.setId(null);
        assertThat(rawData1).isNotEqualTo(rawData2);
    }
}
