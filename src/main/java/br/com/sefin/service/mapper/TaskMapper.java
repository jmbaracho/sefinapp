package br.com.sefin.service.mapper;

import br.com.sefin.domain.ScheduleExecution;
import br.com.sefin.domain.Task;
import br.com.sefin.domain.TaskStatus;
import br.com.sefin.service.dto.ScheduleExecutionDTO;
import br.com.sefin.service.dto.TaskDTO;
import br.com.sefin.service.dto.TaskStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Task} and its DTO {@link TaskDTO}.
 */
@Mapper(componentModel = "spring")
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {
    @Mapping(target = "status", source = "status", qualifiedByName = "taskStatusId")
    @Mapping(target = "scheduleExecution", source = "scheduleExecution", qualifiedByName = "scheduleExecutionId")
    TaskDTO toDto(Task s);

    @Named("taskStatusId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TaskStatusDTO toDtoTaskStatusId(TaskStatus taskStatus);

    @Named("scheduleExecutionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ScheduleExecutionDTO toDtoScheduleExecutionId(ScheduleExecution scheduleExecution);
}
