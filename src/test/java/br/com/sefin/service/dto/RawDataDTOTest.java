package br.com.sefin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.sefin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RawDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RawDataDTO.class);
        RawDataDTO rawDataDTO1 = new RawDataDTO();
        rawDataDTO1.setId(1L);
        RawDataDTO rawDataDTO2 = new RawDataDTO();
        assertThat(rawDataDTO1).isNotEqualTo(rawDataDTO2);
        rawDataDTO2.setId(rawDataDTO1.getId());
        assertThat(rawDataDTO1).isEqualTo(rawDataDTO2);
        rawDataDTO2.setId(2L);
        assertThat(rawDataDTO1).isNotEqualTo(rawDataDTO2);
        rawDataDTO1.setId(null);
        assertThat(rawDataDTO1).isNotEqualTo(rawDataDTO2);
    }
}
