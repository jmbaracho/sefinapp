package br.com.sefin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.sefin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HotelResultDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HotelResultDTO.class);
        HotelResultDTO hotelResultDTO1 = new HotelResultDTO();
        hotelResultDTO1.setId(1L);
        HotelResultDTO hotelResultDTO2 = new HotelResultDTO();
        assertThat(hotelResultDTO1).isNotEqualTo(hotelResultDTO2);
        hotelResultDTO2.setId(hotelResultDTO1.getId());
        assertThat(hotelResultDTO1).isEqualTo(hotelResultDTO2);
        hotelResultDTO2.setId(2L);
        assertThat(hotelResultDTO1).isNotEqualTo(hotelResultDTO2);
        hotelResultDTO1.setId(null);
        assertThat(hotelResultDTO1).isNotEqualTo(hotelResultDTO2);
    }
}
