package br.com.sefin.service.mapper;

import br.com.sefin.domain.Facility;
import br.com.sefin.domain.HotelResult;
import br.com.sefin.domain.HotelResultFacility;
import br.com.sefin.service.dto.FacilityDTO;
import br.com.sefin.service.dto.HotelResultDTO;
import br.com.sefin.service.dto.HotelResultFacilityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HotelResultFacility} and its DTO {@link HotelResultFacilityDTO}.
 */
@Mapper(componentModel = "spring")
public interface HotelResultFacilityMapper extends EntityMapper<HotelResultFacilityDTO, HotelResultFacility> {
    @Mapping(target = "hotelResult", source = "hotelResult", qualifiedByName = "hotelResultId")
    @Mapping(target = "facility", source = "facility", qualifiedByName = "facilityId")
    HotelResultFacilityDTO toDto(HotelResultFacility s);

    @Named("hotelResultId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HotelResultDTO toDtoHotelResultId(HotelResult hotelResult);

    @Named("facilityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FacilityDTO toDtoFacilityId(Facility facility);
}
