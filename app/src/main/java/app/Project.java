package app;

public class Project {
    private String imageUrl;
    private String title;
    private String caption;

    public Project(String imageUrl, String title, String caption) {
        this.setImageUrl(imageUrl);
        this.setTitle(title);
        this.setCaption(caption);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}