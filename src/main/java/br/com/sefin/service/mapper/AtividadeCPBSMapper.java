package br.com.sefin.service.mapper;

import br.com.sefin.domain.AtividadeCPBS;
import br.com.sefin.domain.InscricaoCPBS;
import br.com.sefin.service.dto.AtividadeCPBSDTO;
import br.com.sefin.service.dto.InscricaoCPBSDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AtividadeCPBS} and its DTO {@link AtividadeCPBSDTO}.
 */
@Mapper(componentModel = "spring")
public interface AtividadeCPBSMapper extends EntityMapper<AtividadeCPBSDTO, AtividadeCPBS> {
    @Mapping(target = "inscricaoCpbs", source = "inscricaoCpbs", qualifiedByName = "inscricaoCPBSInscricao")
    AtividadeCPBSDTO toDto(AtividadeCPBS s);

    @Named("inscricaoCPBSInscricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "inscricao", source = "inscricao")
    InscricaoCPBSDTO toDtoInscricaoCPBSInscricao(InscricaoCPBS inscricaoCPBS);
}
