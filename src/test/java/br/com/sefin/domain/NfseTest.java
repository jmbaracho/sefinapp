package br.com.sefin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.sefin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NfseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nfse.class);
        Nfse nfse1 = new Nfse();
        nfse1.setId(1L);
        Nfse nfse2 = new Nfse();
        nfse2.setId(nfse1.getId());
        assertThat(nfse1).isEqualTo(nfse2);
        nfse2.setId(2L);
        assertThat(nfse1).isNotEqualTo(nfse2);
        nfse1.setId(null);
        assertThat(nfse1).isNotEqualTo(nfse2);
    }
}
