package demo.app.com.recyclerviewdemo.data;

import java.io.Serializable;

/**
 * Created by mausam.
 * Model class for holding Article data.
 */
public class ArticleData implements Serializable{

    private static final long serialVersionUID = 1L;
    private String thumbnail="";
    private String title="";
    private String fullImageUrl="";
    private String description="";

    public ArticleData() {
    }

    public ArticleData(String thumbnail, String title, String description, String fullImageUrl) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.description=description;
        this.fullImageUrl=fullImageUrl;

    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullImageUrl() {
        return fullImageUrl;
    }

    public void setFullImageUrl(String fullImageUrl) {
        this.fullImageUrl = fullImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
