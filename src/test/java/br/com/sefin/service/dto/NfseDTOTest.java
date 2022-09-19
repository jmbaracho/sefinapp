package br.com.sefin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.sefin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NfseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NfseDTO.class);
        NfseDTO nfseDTO1 = new NfseDTO();
        nfseDTO1.setId(1L);
        NfseDTO nfseDTO2 = new NfseDTO();
        assertThat(nfseDTO1).isNotEqualTo(nfseDTO2);
        nfseDTO2.setId(nfseDTO1.getId());
        assertThat(nfseDTO1).isEqualTo(nfseDTO2);
        nfseDTO2.setId(2L);
        assertThat(nfseDTO1).isNotEqualTo(nfseDTO2);
        nfseDTO1.setId(null);
        assertThat(nfseDTO1).isNotEqualTo(nfseDTO2);
    }
}
