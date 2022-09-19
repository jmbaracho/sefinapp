package br.com.sefin.service.mapper;

import br.com.sefin.domain.Schedule;
import br.com.sefin.domain.ScheduleExecution;
import br.com.sefin.service.dto.ScheduleDTO;
import br.com.sefin.service.dto.ScheduleExecutionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ScheduleExecution} and its DTO {@link ScheduleExecutionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ScheduleExecutionMapper extends EntityMapper<ScheduleExecutionDTO, ScheduleExecution> {
    @Mapping(target = "schedule", source = "schedule", qualifiedByName = "scheduleId")
    ScheduleExecutionDTO toDto(ScheduleExecution s);

    @Named("scheduleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ScheduleDTO toDtoScheduleId(Schedule schedule);
}
