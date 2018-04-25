package com.lang.sumukha.learnml.Models;

public class LessonQuestionModel {

    private String lessonType;
    private String lessonConceptName;
    private String lessonPronunciation;
    private String lessonTargetScript;
    private String lessonURL;

    public String getLessonType() {
        return lessonType;
    }

    public void setLessonType(String lessonType) {
        this.lessonType = lessonType;
    }

    public String getLessonConceptName() {
        return lessonConceptName;
    }

    public void setLessonConceptName(String lessonConceptName) {
        this.lessonConceptName = lessonConceptName;
    }

    public String getLessonPronunciation() {
        return lessonPronunciation;
    }

    public void setLessonPronunciation(String lessonPronunciation) {
        this.lessonPronunciation = lessonPronunciation;
    }

    public String getLessonTargetScript() {
        return lessonTargetScript;
    }

    public void setLessonTargetScript(String lessonTargetScript) {
        this.lessonTargetScript = lessonTargetScript;
    }

    public String getLessonURL() {
        return lessonURL;
    }

    public void setLessonURL(String lessonURL) {
        this.lessonURL = lessonURL;
    }
}
