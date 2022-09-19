package br.com.sefin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.sefin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TagTextDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagTextDTO.class);
        TagTextDTO tagTextDTO1 = new TagTextDTO();
        tagTextDTO1.setId(1L);
        TagTextDTO tagTextDTO2 = new TagTextDTO();
        assertThat(tagTextDTO1).isNotEqualTo(tagTextDTO2);
        tagTextDTO2.setId(tagTextDTO1.getId());
        assertThat(tagTextDTO1).isEqualTo(tagTextDTO2);
        tagTextDTO2.setId(2L);
        assertThat(tagTextDTO1).isNotEqualTo(tagTextDTO2);
        tagTextDTO1.setId(null);
        assertThat(tagTextDTO1).isNotEqualTo(tagTextDTO2);
    }
}
