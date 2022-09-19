package br.com.sefin.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TagTextMapperTest {

    private TagTextMapper tagTextMapper;

    @BeforeEach
    public void setUp() {
        tagTextMapper = new TagTextMapperImpl();
    }
}
