package com.aronck.minigamesapi;


import com.aronck.minigamesapi.feedback.FeedbackFetcher;
import com.aronck.minigamesapi.feedback.StandardFeedbackHandler;
import com.aronck.minigamesapi.minigame.FeedbackHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

	private static Main instance;
	private InternalDataBridgeKey key;
	private FeedbackHandler feedbackHandler = new StandardFeedbackHandler();

	@Override
	public void onEnable() {
		super.onEnable();
		instance = this;
		System.out.println("enabled");
	}

	public static Main getInstance() {
		return instance;
	}

}
