package com.hzkans.crm.modules.wechat.message;

/**
 * @author jc
 * @description
 * @create 2018/12/13
 */
public class Image {

    private String MediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    @Override
    public String toString() {
        return "Image [MediaId=" + MediaId + "]";
    }
}
