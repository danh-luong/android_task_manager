package com.hfad.taskmanagement.dto;

import java.io.Serializable;

public class ReviewDTO implements Serializable {

    private String feedback, rate, createdFeedback, summary;

    public ReviewDTO() {
    }

    public ReviewDTO(String feedback, String rate, String createdFeedback, String summary) {
        this.feedback = feedback;
        this.rate = rate;
        this.createdFeedback = createdFeedback;
        this.summary = summary;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCreatedFeedback() {
        return createdFeedback;
    }

    public void setCreatedFeedback(String createdFeedback) {
        this.createdFeedback = createdFeedback;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
