package com.aronck.minigamesapi.feedback;

public class Feedback {

    private FeedbackType feedbackType = FeedbackType.INFO;
    private String content;
    private String source;

    public Feedback(String content) {
        this.content = content;
    }

    public Feedback(FeedbackType feedbackType, String content) {
        this.feedbackType = feedbackType;
        this.content = content;
    }

    public Feedback(FeedbackType feedbackType, String content, String source){
        this.feedbackType = feedbackType;
        this.content = content;
        this.source = source;
    }

    public FeedbackType getFeedbackType() {
        return feedbackType;
    }

    public String getContent() {
        return content;
    }

    public String getSource(){
        return source;
    }
}
