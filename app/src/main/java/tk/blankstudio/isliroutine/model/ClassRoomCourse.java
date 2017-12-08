package tk.blankstudio.isliroutine.model;

import com.google.api.services.classroom.model.Course;

/**
 * Created by deadsec on 11/27/17.
 */

public class ClassRoomCourse {
    String id;
    String name;
    String section;
    String descriptionHeading;
    String alternateLink;
    String enrollmentCode;
    String googleDriveLink;
    String description;
    String courseState;

    public ClassRoomCourse(String id, String name, String section, String descriptionHeading, String alternateLink, String enrollmentCode, String googleDriveLink, String description, String courseState) {
        this.id = id;
        this.name = name;
        this.section = section;
        this.descriptionHeading = descriptionHeading;
        this.alternateLink = alternateLink;
        this.enrollmentCode = enrollmentCode;
        this.googleDriveLink = googleDriveLink;
        this.description = description;
        this.courseState = courseState;
    }

    public ClassRoomCourse(String id, String name, String enrollmentCode, String description, String courseState) {
        this.id = id;
        this.name = name;
        this.enrollmentCode = enrollmentCode;
        this.description = description;
        this.courseState = courseState;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDescriptionHeading() {
        return descriptionHeading;
    }

    public void setDescriptionHeading(String descriptionHeading) {
        this.descriptionHeading = descriptionHeading;
    }

    public String getAlternateLink() {
        return alternateLink;
    }

    public void setAlternateLink(String alternateLink) {
        this.alternateLink = alternateLink;
    }

    public String getGoogleDriveLink() {
        return googleDriveLink;
    }

    public void setGoogleDriveLink(String googleDriveLink) {
        this.googleDriveLink = googleDriveLink;
    }

    public ClassRoomCourse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnrollmentCode() {
        return enrollmentCode;
    }

    public void setEnrollmentCode(String enrollmentCode) {
        this.enrollmentCode = enrollmentCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseState() {
        return courseState;
    }

    public void setCourseState(String courseState) {
        this.courseState = courseState;
    }
}