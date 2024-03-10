package com.aronck.minigamesapi.feedback;

import java.util.List;

public interface FeedbackGenerator {

    List<Feedback> generateFeedback(FeedbackFetcher feedbackFetcher);

}
