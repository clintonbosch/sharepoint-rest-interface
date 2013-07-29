package za.co.cmb.sharepoint.mapping;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SharepointWebserviceDocumentResponse {

    @JsonProperty(value = "d")
    private SPResultList spResultList;

    public SPResultList getSpResultList() {
        return spResultList;
    }

    public void setSpResultList(SPResultList spResultList) {
        this.spResultList = spResultList;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class SPResultList {
        @JsonProperty(value = "results")
        private List<SPResult> results;

        public List<SPResult> getResults() {
            return results;
        }

        public void setResults(List<SPResult> results) {
            this.results = results;
        }

        @Override
        public String toString() {
            return getResults().toString();
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class SPResult {
        @JsonProperty(value = "__metadata")
        private SPMetadata metadata;

        @JsonProperty(value = "Created")
        private String dateCreated;

        @JsonProperty(value = "Name")
        private String name;

        public SPMetadata getMetadata() {
            return metadata;
        }

        public void setMetadata(SPMetadata metadata) {
            this.metadata = metadata;
        }

        public String getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(String dateCreated) {
            this.dateCreated = dateCreated;
        }

        String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Name = " + getName() + ", Date created = " + getDateCreated() + ", Metadata = " + getMetadata();
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class SPMetadata {

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

    @Override
    public String toString() {
        return getSpResultList().toString();
    }
}
