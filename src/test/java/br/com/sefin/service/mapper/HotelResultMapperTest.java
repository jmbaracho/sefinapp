package br.com.sefin.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HotelResultMapperTest {

    private HotelResultMapper hotelResultMapper;

    @BeforeEach
    public void setUp() {
        hotelResultMapper = new HotelResultMapperImpl();
    }
}
