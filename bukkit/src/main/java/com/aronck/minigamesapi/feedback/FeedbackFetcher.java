package com.aronck.minigamesapi.feedback;

import java.util.ArrayList;
import java.util.List;

public class FeedbackFetcher {

    /**
     * list of all the feedback that the fetcher already received and won't change even after a reevaluation
     * thus deeming it unnecessary to be able to reevaluate the feedback dynamically after the first time
      */
    private List<Feedback> feedbacks = new ArrayList<>();

    private List<FeedbackGenerator> feedbackGenerators = new ArrayList<>();


    public void addFeedbackGenerator(FeedbackGenerator feedbackGenerator){
        feedbackGenerators.add(feedbackGenerator);
    }

    public boolean add(Feedback feedback) {
        return feedbacks.add(feedback);
    }

    public List<Feedback> generateAllFeedbacks(){
        List<Feedback> totalFeedbacks = new ArrayList<>(feedbacks);
        feedbackGenerators.forEach(feedbackGenerator -> totalFeedbacks.addAll(feedbackGenerator.generateFeedback(this)));
        return totalFeedbacks;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

}
