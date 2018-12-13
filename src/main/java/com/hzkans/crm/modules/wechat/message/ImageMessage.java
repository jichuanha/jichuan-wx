package com.hzkans.crm.modules.wechat.message;

/**
 * @author jc
 * @description
 * @create 2018/12/12
 */
public class ImageMessage extends BaseMessage {

    private Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        this.Image = image;
    }

    @Override
    public String toString() {
        return "return ImageMessage [Image= + Image + ];";
    }
}
