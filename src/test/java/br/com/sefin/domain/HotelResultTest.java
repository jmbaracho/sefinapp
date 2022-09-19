package br.com.sefin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.sefin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HotelResultTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HotelResult.class);
        HotelResult hotelResult1 = new HotelResult();
        hotelResult1.setId(1L);
        HotelResult hotelResult2 = new HotelResult();
        hotelResult2.setId(hotelResult1.getId());
        assertThat(hotelResult1).isEqualTo(hotelResult2);
        hotelResult2.setId(2L);
        assertThat(hotelResult1).isNotEqualTo(hotelResult2);
        hotelResult1.setId(null);
        assertThat(hotelResult1).isNotEqualTo(hotelResult2);
    }
}
