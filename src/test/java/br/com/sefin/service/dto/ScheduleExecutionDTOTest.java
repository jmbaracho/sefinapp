package br.com.sefin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.sefin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ScheduleExecutionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduleExecutionDTO.class);
        ScheduleExecutionDTO scheduleExecutionDTO1 = new ScheduleExecutionDTO();
        scheduleExecutionDTO1.setId(1L);
        ScheduleExecutionDTO scheduleExecutionDTO2 = new ScheduleExecutionDTO();
        assertThat(scheduleExecutionDTO1).isNotEqualTo(scheduleExecutionDTO2);
        scheduleExecutionDTO2.setId(scheduleExecutionDTO1.getId());
        assertThat(scheduleExecutionDTO1).isEqualTo(scheduleExecutionDTO2);
        scheduleExecutionDTO2.setId(2L);
        assertThat(scheduleExecutionDTO1).isNotEqualTo(scheduleExecutionDTO2);
        scheduleExecutionDTO1.setId(null);
        assertThat(scheduleExecutionDTO1).isNotEqualTo(scheduleExecutionDTO2);
    }
}
