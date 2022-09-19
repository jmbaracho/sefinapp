package br.com.sefin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.sefin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InscricaoCPBSDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InscricaoCPBSDTO.class);
        InscricaoCPBSDTO inscricaoCPBSDTO1 = new InscricaoCPBSDTO();
        inscricaoCPBSDTO1.setId(1L);
        InscricaoCPBSDTO inscricaoCPBSDTO2 = new InscricaoCPBSDTO();
        assertThat(inscricaoCPBSDTO1).isNotEqualTo(inscricaoCPBSDTO2);
        inscricaoCPBSDTO2.setId(inscricaoCPBSDTO1.getId());
        assertThat(inscricaoCPBSDTO1).isEqualTo(inscricaoCPBSDTO2);
        inscricaoCPBSDTO2.setId(2L);
        assertThat(inscricaoCPBSDTO1).isNotEqualTo(inscricaoCPBSDTO2);
        inscricaoCPBSDTO1.setId(null);
        assertThat(inscricaoCPBSDTO1).isNotEqualTo(inscricaoCPBSDTO2);
    }
}
