package com.excilys.shoofleurs.dashboard.model.entities;

import com.excilys.shoofleurs.dashboard.model.type.MediaType;

/**
 * Created by excilys on 09/06/16.
 */
public class Media {
    private String mUuid;

    private String mName;

    private MediaType mMediaType;

    private Validity mValidity;

    private String mUrl;

    public String getUuid() {
        return mUuid;
    }

    public void setUuid(String uuid) {
        mUuid = uuid;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public MediaType getMediaType() {
        return mMediaType;
    }

    public void setMediaType(MediaType mediaType) {
        mMediaType = mediaType;
    }

    public Validity getValidity() {
        return mValidity;
    }

    public void setValidity(Validity validity) {
        mValidity = validity;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
