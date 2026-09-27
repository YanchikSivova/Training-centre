package org.example.model;

import java.time.LocalDate;

public class TrainingApplication {

    private int id;
    private User user;
    private String courseName;
    private LocalDate applicationDate;
    private ApplicationStatus status;
    private EducationType educationType;
    private String comment;

    public TrainingApplication() {
    }

    public TrainingApplication(
            User user,
            String courseName,
            LocalDate applicationDate,
            ApplicationStatus status,
            EducationType educationType,
            String comment
    ) {
        this.user = user;
        this.courseName = courseName;
        this.applicationDate = applicationDate;
        this.status = status;
        this.educationType = educationType;
        this.comment = comment;
    }

    public TrainingApplication(
            int id,
            User user,
            String courseName,
            LocalDate applicationDate,
            ApplicationStatus status,
            EducationType educationType,
            String comment
    ) {
        this.id = id;
        this.user = user;
        this.courseName = courseName;
        this.applicationDate = applicationDate;
        this.status = status;
        this.educationType = educationType;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public EducationType getEducationType() {
        return educationType;
    }

    public void setEducationType(EducationType educationType) {
        this.educationType = educationType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "TrainingApplication{" +
                "id=" + id +
                ", user=" + user.getFullName() +
                ", courseName='" + courseName + '\'' +
                ", applicationDate=" + applicationDate +
                ", status=" + status +
                ", educationType=" + educationType +
                ", comment='" + comment + '\'' +
                '}';
    }
}