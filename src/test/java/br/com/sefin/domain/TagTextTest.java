package br.com.sefin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.sefin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TagTextTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagText.class);
        TagText tagText1 = new TagText();
        tagText1.setId(1L);
        TagText tagText2 = new TagText();
        tagText2.setId(tagText1.getId());
        assertThat(tagText1).isEqualTo(tagText2);
        tagText2.setId(2L);
        assertThat(tagText1).isNotEqualTo(tagText2);
        tagText1.setId(null);
        assertThat(tagText1).isNotEqualTo(tagText2);
    }
}
