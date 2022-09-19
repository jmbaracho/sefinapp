package br.com.sefin.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HotelResultFacilityMapperTest {

    private HotelResultFacilityMapper hotelResultFacilityMapper;

    @BeforeEach
    public void setUp() {
        hotelResultFacilityMapper = new HotelResultFacilityMapperImpl();
    }
}
