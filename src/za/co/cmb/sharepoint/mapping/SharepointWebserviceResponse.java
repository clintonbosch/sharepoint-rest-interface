package za.co.cmb.sharepoint.mapping;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Wrapper class representing the strange d parent element in the JSON response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SharepointWebserviceResponse {

    @JsonProperty(value = "d")
    private SharepointResultList sharepointResultList;

    public SharepointResultList getSharepointResultList() {
        return sharepointResultList;
    }

    public void setSharepointResultList(SharepointResultList sharepointResultList) {
        this.sharepointResultList = sharepointResultList;
    }

}
