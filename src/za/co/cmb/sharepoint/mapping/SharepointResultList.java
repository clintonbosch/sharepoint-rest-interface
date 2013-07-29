package za.co.cmb.sharepoint.mapping;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Wrapper class representing the result[] in the JSON response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SharepointResultList {

    @JsonProperty(value = "results")
    private List<SharepointResult> results;

    public List<SharepointResult> getResults() {
        return results;
    }

    public void setResults(List<SharepointResult> results) {
        this.results = results;
    }

}