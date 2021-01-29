package eu.darkcube.minigame.woolbattle.util;

import com.google.gson.Gson;

public abstract class Serializable {

	public String serialize() {
		return new Gson().toJson(this);
	}
}