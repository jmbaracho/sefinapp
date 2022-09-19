package br.com.sefin.service.mapper;

import br.com.sefin.domain.Agent;
import br.com.sefin.domain.Company;
import br.com.sefin.domain.Source;
import br.com.sefin.service.dto.AgentDTO;
import br.com.sefin.service.dto.CompanyDTO;
import br.com.sefin.service.dto.SourceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Source} and its DTO {@link SourceDTO}.
 */
@Mapper(componentModel = "spring")
public interface SourceMapper extends EntityMapper<SourceDTO, Source> {
    @Mapping(target = "agent", source = "agent", qualifiedByName = "agentId")
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    SourceDTO toDto(Source s);

    @Named("agentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AgentDTO toDtoAgentId(Agent agent);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);
}
