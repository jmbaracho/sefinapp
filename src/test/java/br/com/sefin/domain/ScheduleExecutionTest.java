package br.com.sefin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.sefin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ScheduleExecutionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduleExecution.class);
        ScheduleExecution scheduleExecution1 = new ScheduleExecution();
        scheduleExecution1.setId(1L);
        ScheduleExecution scheduleExecution2 = new ScheduleExecution();
        scheduleExecution2.setId(scheduleExecution1.getId());
        assertThat(scheduleExecution1).isEqualTo(scheduleExecution2);
        scheduleExecution2.setId(2L);
        assertThat(scheduleExecution1).isNotEqualTo(scheduleExecution2);
        scheduleExecution1.setId(null);
        assertThat(scheduleExecution1).isNotEqualTo(scheduleExecution2);
    }
}
