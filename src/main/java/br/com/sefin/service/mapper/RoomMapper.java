package br.com.sefin.service.mapper;

import br.com.sefin.domain.HotelResult;
import br.com.sefin.domain.Room;
import br.com.sefin.service.dto.HotelResultDTO;
import br.com.sefin.service.dto.RoomDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Room} and its DTO {@link RoomDTO}.
 */
@Mapper(componentModel = "spring")
public interface RoomMapper extends EntityMapper<RoomDTO, Room> {
    @Mapping(target = "hotelResult", source = "hotelResult", qualifiedByName = "hotelResultId")
    RoomDTO toDto(Room s);

    @Named("hotelResultId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HotelResultDTO toDtoHotelResultId(HotelResult hotelResult);
}
