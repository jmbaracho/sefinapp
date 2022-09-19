package br.com.sefin.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AtividadeCPBSMapperTest {

    private AtividadeCPBSMapper atividadeCPBSMapper;

    @BeforeEach
    public void setUp() {
        atividadeCPBSMapper = new AtividadeCPBSMapperImpl();
    }
}
