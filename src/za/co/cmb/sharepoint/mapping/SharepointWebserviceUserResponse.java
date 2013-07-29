package za.co.cmb.sharepoint.mapping;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SharepointWebserviceUserResponse {

    @JsonProperty(value = "d")
    private SharepointResultList sharepointResultList;

    public SharepointResultList getSharepointResultList() {
        return sharepointResultList;
    }

    public void setSharepointResultList(SharepointResultList sharepointResultList) {
        this.sharepointResultList = sharepointResultList;
    }

}
