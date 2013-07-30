package za.co.cmb.sharepoint.dto;

import java.io.Serializable;

public class SharepointSearchResult implements Serializable {

    private String author;
    private String title;
    private String path;
    private double rank;
    private String hitHighlightedSummary;
    private String siteName;
    private String lastModified;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public String getHitHighlightedSummary() {
        return hitHighlightedSummary;
    }

    public void setHitHighlightedSummary(String hitHighlightedSummary) {
        this.hitHighlightedSummary = hitHighlightedSummary;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return "[Title:" + getTitle() + ", Author:" + getAuthor() + ", Rank:" + getRank() + ", Site:" + getSiteName() +
                ", Modified:" + getLastModified();
    }
}
