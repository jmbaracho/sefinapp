package br.com.sefin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.sefin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AtividadeCPBSDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AtividadeCPBSDTO.class);
        AtividadeCPBSDTO atividadeCPBSDTO1 = new AtividadeCPBSDTO();
        atividadeCPBSDTO1.setId(1L);
        AtividadeCPBSDTO atividadeCPBSDTO2 = new AtividadeCPBSDTO();
        assertThat(atividadeCPBSDTO1).isNotEqualTo(atividadeCPBSDTO2);
        atividadeCPBSDTO2.setId(atividadeCPBSDTO1.getId());
        assertThat(atividadeCPBSDTO1).isEqualTo(atividadeCPBSDTO2);
        atividadeCPBSDTO2.setId(2L);
        assertThat(atividadeCPBSDTO1).isNotEqualTo(atividadeCPBSDTO2);
        atividadeCPBSDTO1.setId(null);
        assertThat(atividadeCPBSDTO1).isNotEqualTo(atividadeCPBSDTO2);
    }
}
