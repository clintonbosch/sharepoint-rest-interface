package za.co.cmb.sharepoint.mapping;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Wrapper class representing the actual result set line item, this class caters for all fields we may need for
 * documents, users etc
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SharepointResult {

    @JsonProperty(value = "__metadata")
    private SharepointMetadata metadata;

    @JsonProperty(value = "Created")
    private String dateCreated;

    @JsonProperty(value = "Name")
    private String name;

    @JsonProperty(value = "Id")
    private String id;

    @JsonProperty(value = "Picture")
    private String picture;

    public SharepointMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(SharepointMetadata metadata) {
        this.metadata = metadata;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}
