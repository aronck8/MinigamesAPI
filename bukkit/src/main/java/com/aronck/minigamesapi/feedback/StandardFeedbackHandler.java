package com.aronck.minigamesapi.feedback;

import com.aronck.minigamesapi.minigame.FeedbackHandler;
import com.aronck.minigamesapi.minigame.Minigame;
import com.aronck.minigamesapi.minigame.Minigames;

import java.util.List;

public class StandardFeedbackHandler extends FeedbackHandler {


    public StandardFeedbackHandler() {
        super(new FeedbackFetcher(), true);
    }

    @Override
    public void handleFeedback() {
        List<Feedback> feedbacks = feedbackFetcher.generateAllFeedbacks();
        System.out.println("-----------Feedback for plugin containing the following minigames: "
                + Minigames.getMinigames().stream().map(Minigame::getName).reduce((s, s2) -> s + "," + s2).get()
                + "-----------");
        if(feedbacks.isEmpty()){
            System.out.println("No feedback found!");
            System.out.println("---------------------------------");
            return;
        }
        feedbacks.forEach(feedback -> System.out.println("[" + feedback.getFeedbackType().name() + "]:" + feedback.getContent() + " | " + feedback.getSource()));
        System.out.println("---------------------------------------------------------------------------------------------------");
    }

}
