package br.com.sefin.service.mapper;

import br.com.sefin.domain.Nfse;
import br.com.sefin.domain.Tag;
import br.com.sefin.domain.TagText;
import br.com.sefin.service.dto.NfseDTO;
import br.com.sefin.service.dto.TagDTO;
import br.com.sefin.service.dto.TagTextDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TagText} and its DTO {@link TagTextDTO}.
 */
@Mapper(componentModel = "spring")
public interface TagTextMapper extends EntityMapper<TagTextDTO, TagText> {
    @Mapping(target = "nfse", source = "nfse", qualifiedByName = "nfseIdNfse")
    @Mapping(target = "tag", source = "tag", qualifiedByName = "tagId")
    TagTextDTO toDto(TagText s);

    @Named("nfseIdNfse")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "idNfse", source = "idNfse")
    NfseDTO toDtoNfseIdNfse(Nfse nfse);

    @Named("tagId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TagDTO toDtoTagId(Tag tag);
}
