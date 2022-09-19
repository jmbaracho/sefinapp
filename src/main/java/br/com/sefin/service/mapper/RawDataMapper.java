package br.com.sefin.service.mapper;

import br.com.sefin.domain.RawData;
import br.com.sefin.domain.Task;
import br.com.sefin.service.dto.RawDataDTO;
import br.com.sefin.service.dto.TaskDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RawData} and its DTO {@link RawDataDTO}.
 */
@Mapper(componentModel = "spring")
public interface RawDataMapper extends EntityMapper<RawDataDTO, RawData> {
    @Mapping(target = "task", source = "task", qualifiedByName = "taskId")
    RawDataDTO toDto(RawData s);

    @Named("taskId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TaskDTO toDtoTaskId(Task task);
}
