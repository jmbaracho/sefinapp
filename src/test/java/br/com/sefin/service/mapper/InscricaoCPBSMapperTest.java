package br.com.sefin.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InscricaoCPBSMapperTest {

    private InscricaoCPBSMapper inscricaoCPBSMapper;

    @BeforeEach
    public void setUp() {
        inscricaoCPBSMapper = new InscricaoCPBSMapperImpl();
    }
}
