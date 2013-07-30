package za.co.cmb.sharepoint.dto;

import java.io.Serializable;

public class SharepointUser implements Serializable {

    private String id;
    private String name;
    private String pictureUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @Override
    public String toString() {
        return "[ID:" + getId() + ", Name:" + getName() + ", Picture:" + getPictureUrl() + "]";
    }
}
