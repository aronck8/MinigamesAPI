package com.aronck.minigamesapi.minigame;

import com.aronck.minigamesapi.feedback.FeedbackFetcher;

public abstract class FeedbackHandler {

    protected FeedbackFetcher feedbackFetcher;
    protected boolean shouldAllowUnhandledFeedback;

    public FeedbackHandler(FeedbackFetcher feedbackFetcher, boolean shouldAllowUnhandledFeedback) {
        this.feedbackFetcher = feedbackFetcher;
        this.shouldAllowUnhandledFeedback = shouldAllowUnhandledFeedback;
    }

    public abstract void handleFeedback();

    public FeedbackFetcher getFeedbackFetcher() {
        return feedbackFetcher;
    }

    public boolean allowsUnhandledFeedback(){
        return shouldAllowUnhandledFeedback;
    }
}
