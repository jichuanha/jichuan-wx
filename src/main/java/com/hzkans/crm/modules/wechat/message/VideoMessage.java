package com.hzkans.crm.modules.wechat.message;

/**
 * @author jc
 * @description
 * @create 2018/12/13
 */
public class VideoMessage {

    private Image Video;
    private String Title;
    private String Description;


    public Image getVideo() {
        return Video;
    }

    public void setVideo(Image video) {
        Video = video;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
