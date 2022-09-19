package br.com.sefin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.sefin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InscricaoCPBSTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InscricaoCPBS.class);
        InscricaoCPBS inscricaoCPBS1 = new InscricaoCPBS();
        inscricaoCPBS1.setId(1L);
        InscricaoCPBS inscricaoCPBS2 = new InscricaoCPBS();
        inscricaoCPBS2.setId(inscricaoCPBS1.getId());
        assertThat(inscricaoCPBS1).isEqualTo(inscricaoCPBS2);
        inscricaoCPBS2.setId(2L);
        assertThat(inscricaoCPBS1).isNotEqualTo(inscricaoCPBS2);
        inscricaoCPBS1.setId(null);
        assertThat(inscricaoCPBS1).isNotEqualTo(inscricaoCPBS2);
    }
}
