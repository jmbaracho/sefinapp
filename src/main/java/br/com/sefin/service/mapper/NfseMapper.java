package br.com.sefin.service.mapper;

import br.com.sefin.domain.InscricaoCPBS;
import br.com.sefin.domain.Nfse;
import br.com.sefin.service.dto.InscricaoCPBSDTO;
import br.com.sefin.service.dto.NfseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nfse} and its DTO {@link NfseDTO}.
 */
@Mapper(componentModel = "spring")
public interface NfseMapper extends EntityMapper<NfseDTO, Nfse> {
    @Mapping(target = "inscricaoCpbs", source = "inscricaoCpbs", qualifiedByName = "inscricaoCPBSInscricao")
    NfseDTO toDto(Nfse s);

    @Named("inscricaoCPBSInscricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "inscricao", source = "inscricao")
    InscricaoCPBSDTO toDtoInscricaoCPBSInscricao(InscricaoCPBS inscricaoCPBS);
}
