package br.com.sefin.service.mapper;

import br.com.sefin.domain.Agent;
import br.com.sefin.service.dto.AgentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Agent} and its DTO {@link AgentDTO}.
 */
@Mapper(componentModel = "spring")
public interface AgentMapper extends EntityMapper<AgentDTO, Agent> {}
