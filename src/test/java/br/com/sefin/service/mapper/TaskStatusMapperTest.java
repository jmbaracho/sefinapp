package br.com.sefin.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskStatusMapperTest {

    private TaskStatusMapper taskStatusMapper;

    @BeforeEach
    public void setUp() {
        taskStatusMapper = new TaskStatusMapperImpl();
    }
}
