package br.com.sefin.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SourceMapperTest {

    private SourceMapper sourceMapper;

    @BeforeEach
    public void setUp() {
        sourceMapper = new SourceMapperImpl();
    }
}
