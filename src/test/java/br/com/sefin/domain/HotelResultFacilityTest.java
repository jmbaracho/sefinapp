package br.com.sefin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.sefin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HotelResultFacilityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HotelResultFacility.class);
        HotelResultFacility hotelResultFacility1 = new HotelResultFacility();
        hotelResultFacility1.setId(1L);
        HotelResultFacility hotelResultFacility2 = new HotelResultFacility();
        hotelResultFacility2.setId(hotelResultFacility1.getId());
        assertThat(hotelResultFacility1).isEqualTo(hotelResultFacility2);
        hotelResultFacility2.setId(2L);
        assertThat(hotelResultFacility1).isNotEqualTo(hotelResultFacility2);
        hotelResultFacility1.setId(null);
        assertThat(hotelResultFacility1).isNotEqualTo(hotelResultFacility2);
    }
}
