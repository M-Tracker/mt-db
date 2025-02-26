package Mtracker.project.backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_problem_data")
public class UserProblemsDataModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String category;

    private Integer count;

    private String description;

    private String lesson;

    private String imgPath; // Path to the image file

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AuthModel user; // Reference to User


    public UserProblemsDataModel(String title, String category, Integer count, String description, String lesson, AuthModel user) {
        this.title = title;
        this.category = category;
        this.count = count;
        this.description = description;
        this.lesson = lesson;
        this.user = user;
    }

    public UserProblemsDataModel(String title, String category, Integer count, String description, String lesson, AuthModel user, String imgPath) {
        this.title = title;
        this.category = category;
        this.count = count;
        this.description = description;
        this.lesson = lesson;
        this.user = user;
        this.imgPath = imgPath;
    }

    public UserProblemsDataModel() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCount() {
        return count;
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public AuthModel getUser() {
        return user;
    }

    public void setUser(AuthModel user) {
        this.user = user;
    }
}
