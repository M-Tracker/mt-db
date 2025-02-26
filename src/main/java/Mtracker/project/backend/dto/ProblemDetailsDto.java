package Mtracker.project.backend.dto;

public class ProblemDetailsDto {
    private String title;

    private String category;

    private Integer count;

    private String description;

    private String lesson;

    private String imgExists;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCount() {
        return count;
    }

    public String isImgExists() {
        return imgExists;
    }

    public void setImgExists(String imgExists) {
        this.imgExists = imgExists;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

}
