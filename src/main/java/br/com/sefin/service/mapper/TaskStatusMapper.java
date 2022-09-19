package br.com.sefin.service.mapper;

import br.com.sefin.domain.TaskStatus;
import br.com.sefin.service.dto.TaskStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskStatus} and its DTO {@link TaskStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface TaskStatusMapper extends EntityMapper<TaskStatusDTO, TaskStatus> {}
