package br.com.sefin.service.mapper;

import br.com.sefin.domain.Agent;
import br.com.sefin.domain.Company;
import br.com.sefin.domain.HotelResult;
import br.com.sefin.domain.Task;
import br.com.sefin.service.dto.AgentDTO;
import br.com.sefin.service.dto.CompanyDTO;
import br.com.sefin.service.dto.HotelResultDTO;
import br.com.sefin.service.dto.TaskDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HotelResult} and its DTO {@link HotelResultDTO}.
 */
@Mapper(componentModel = "spring")
public interface HotelResultMapper extends EntityMapper<HotelResultDTO, HotelResult> {
    @Mapping(target = "task", source = "task", qualifiedByName = "taskId")
    @Mapping(target = "agent", source = "agent", qualifiedByName = "agentId")
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    HotelResultDTO toDto(HotelResult s);

    @Named("taskId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TaskDTO toDtoTaskId(Task task);

    @Named("agentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AgentDTO toDtoAgentId(Agent agent);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);
}
