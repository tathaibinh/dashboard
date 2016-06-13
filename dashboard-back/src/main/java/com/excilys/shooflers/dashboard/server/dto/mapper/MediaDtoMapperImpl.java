package com.excilys.shooflers.dashboard.server.dto.mapper;

import com.excilys.shooflers.dashboard.server.dto.MediaMetadataDto;
import com.excilys.shooflers.dashboard.server.model.metadata.MediaMetadata;
import com.excilys.shooflers.dashboard.server.model.type.MediaType;
import com.excilys.shooflers.dashboard.server.service.exception.JsonMalformedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MediaDtoMapperImpl implements MediaDtoMapper {

    @Autowired
    private ValidityDtoMapperImpl validityDtoMapper;

    @Override
    public MediaMetadataDto toDto(MediaMetadata model) {
        return model != null ? new MediaMetadataDto.Builder()
                .uuid(model.getUuid())
                .name(model.getName())
                .duration(model.getDuration())
                .mediaType(model.getMediaType())
                .validity(validityDtoMapper.toDto(model.getValidity()))
                .url(model.getUrl())
                .bundleTag(model.getBundleTag())
                .build() : null;
    }

    @Override
    public MediaMetadata fromDto(MediaMetadataDto dto) {
        return dto != null ? MediaMetadata.builder()
                .uuid(dto.getUuid())
                .name(dto.getName())
                .duration(dto.getDuration())
                .mediaType(MediaType.valueOf(dto.getMediaType()))
                .validity(validityDtoMapper.fromDto(dto.getValidity()))
                .url(dto.getUrl())
                .bundleTag(dto.getBundleTag())
                .build() : null;
    }

    @Override
    public MediaMetadataDto toDto(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        MediaMetadataDto mediaMetadataDto;
        try {
            mediaMetadataDto = objectMapper.readValue(json, MediaMetadataDto.class);
        } catch (IOException e) {
            throw new JsonMalformedException("JSON malformed");
        }
        return mediaMetadataDto;
    }
    
}
