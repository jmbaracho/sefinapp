package br.com.sefin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.sefin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HotelResultFacilityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HotelResultFacilityDTO.class);
        HotelResultFacilityDTO hotelResultFacilityDTO1 = new HotelResultFacilityDTO();
        hotelResultFacilityDTO1.setId(1L);
        HotelResultFacilityDTO hotelResultFacilityDTO2 = new HotelResultFacilityDTO();
        assertThat(hotelResultFacilityDTO1).isNotEqualTo(hotelResultFacilityDTO2);
        hotelResultFacilityDTO2.setId(hotelResultFacilityDTO1.getId());
        assertThat(hotelResultFacilityDTO1).isEqualTo(hotelResultFacilityDTO2);
        hotelResultFacilityDTO2.setId(2L);
        assertThat(hotelResultFacilityDTO1).isNotEqualTo(hotelResultFacilityDTO2);
        hotelResultFacilityDTO1.setId(null);
        assertThat(hotelResultFacilityDTO1).isNotEqualTo(hotelResultFacilityDTO2);
    }
}
