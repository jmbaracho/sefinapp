package br.com.sefin.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RawDataMapperTest {

    private RawDataMapper rawDataMapper;

    @BeforeEach
    public void setUp() {
        rawDataMapper = new RawDataMapperImpl();
    }
}
