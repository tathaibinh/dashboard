package com.excilys.shoofleurs.dashboard.rest.dtos.mappers;

import android.util.Log;

import com.excilys.shoofleurs.dashboard.model.entities.Media;
import com.excilys.shoofleurs.dashboard.model.type.MediaType;
import com.excilys.shoofleurs.dashboard.rest.dtos.MediaDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by excilys on 09/06/16.
 */
public class MediaDtoMapper {
    private static final String TAG = "MediaDtoMapper";

    public static Media toMedia(MediaDto mediaDto) {
        if (mediaDto == null) {
            return null;
        }

        Media media = new Media();
        media.setValidity(ValidityDtoMapper.toValidity(mediaDto.getValidity()));
        media.setUuid(mediaDto.getUuid());
        Log.i(TAG, "toMedia:");
        media.setMediaType(MediaType.getMediaType(mediaDto.getMediaType()));
        media.setName(mediaDto.getName());
        media.setUrl(mediaDto.getUrl());
        media.setUuidBundle(mediaDto.getUuidBundle());
        media.setRevision(mediaDto.getRevision());
        media.setDuration(mediaDto.getDuration());
        return media;
    }

    public static List<Media> toMedias(List<MediaDto> mediaDtos) {
        if (mediaDtos == null) {
            return null;
        }
        List<Media> medias = new ArrayList<>();
        for (MediaDto mediaDto : mediaDtos) {
            medias.add(toMedia(mediaDto));
        }
        return medias;
    }
}