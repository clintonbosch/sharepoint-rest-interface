package za.co.cmb.sharepoint;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SharepointWebserviceUserResponse {

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
        @JsonProperty(value = "Name")
        private String name;

        @JsonProperty(value = "Id")
        private String id;

        @JsonProperty(value = "Picture")
        private String picture;

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

        @Override
        public String toString() {
            return "Name = " + getName() + ", Id = " + getId() + ", Picure = " + getPicture();
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
        return getSpResultList().getResults().toString();
    }
}
