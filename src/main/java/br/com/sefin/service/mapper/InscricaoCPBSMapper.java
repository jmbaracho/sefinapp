package br.com.sefin.service.mapper;

import br.com.sefin.domain.InscricaoCPBS;
import br.com.sefin.service.dto.InscricaoCPBSDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link InscricaoCPBS} and its DTO {@link InscricaoCPBSDTO}.
 */
@Mapper(componentModel = "spring")
public interface InscricaoCPBSMapper extends EntityMapper<InscricaoCPBSDTO, InscricaoCPBS> {}
