package br.com.sefin.service.mapper;

import br.com.sefin.domain.Agent;
import br.com.sefin.domain.Schedule;
import br.com.sefin.service.dto.AgentDTO;
import br.com.sefin.service.dto.ScheduleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Schedule} and its DTO {@link ScheduleDTO}.
 */
@Mapper(componentModel = "spring")
public interface ScheduleMapper extends EntityMapper<ScheduleDTO, Schedule> {
    @Mapping(target = "agent", source = "agent", qualifiedByName = "agentId")
    ScheduleDTO toDto(Schedule s);

    @Named("agentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AgentDTO toDtoAgentId(Agent agent);
}
