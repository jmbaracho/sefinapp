package br.com.sefin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.sefin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AtividadeCPBSTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AtividadeCPBS.class);
        AtividadeCPBS atividadeCPBS1 = new AtividadeCPBS();
        atividadeCPBS1.setId(1L);
        AtividadeCPBS atividadeCPBS2 = new AtividadeCPBS();
        atividadeCPBS2.setId(atividadeCPBS1.getId());
        assertThat(atividadeCPBS1).isEqualTo(atividadeCPBS2);
        atividadeCPBS2.setId(2L);
        assertThat(atividadeCPBS1).isNotEqualTo(atividadeCPBS2);
        atividadeCPBS1.setId(null);
        assertThat(atividadeCPBS1).isNotEqualTo(atividadeCPBS2);
    }
}
