package za.co.cmb.sharepoint.mapping;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Wrapper class representing the actual result set line item metadata, this class caters for all fields we may need for
 * documents, users etc
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SharepointMetadata {

    /**
     * The sharepoint path to the document
     */
    @JsonProperty(value = "media_src")
    private String path;

    String getPath() {
        return path;
    }

    void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Path = " + getPath();
    }
}
