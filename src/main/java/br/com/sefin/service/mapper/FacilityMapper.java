package br.com.sefin.service.mapper;

import br.com.sefin.domain.Facility;
import br.com.sefin.service.dto.FacilityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Facility} and its DTO {@link FacilityDTO}.
 */
@Mapper(componentModel = "spring")
public interface FacilityMapper extends EntityMapper<FacilityDTO, Facility> {}
