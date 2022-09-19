package br.com.sefin.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NfseMapperTest {

    private NfseMapper nfseMapper;

    @BeforeEach
    public void setUp() {
        nfseMapper = new NfseMapperImpl();
    }
}
