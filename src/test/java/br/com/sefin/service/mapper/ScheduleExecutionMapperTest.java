package br.com.sefin.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScheduleExecutionMapperTest {

    private ScheduleExecutionMapper scheduleExecutionMapper;

    @BeforeEach
    public void setUp() {
        scheduleExecutionMapper = new ScheduleExecutionMapperImpl();
    }
}
