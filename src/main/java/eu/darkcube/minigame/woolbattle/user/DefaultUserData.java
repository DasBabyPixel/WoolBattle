package eu.darkcube.minigame.woolbattle.user;

import com.google.gson.Gson;

import eu.darkcube.minigame.woolbattle.gadget.Gadget;
import eu.darkcube.minigame.woolbattle.perk.PlayerPerks;
import eu.darkcube.minigame.woolbattle.perk.DefaultPlayerPerks;
import eu.darkcube.minigame.woolbattle.translation.Language;

public class DefaultUserData implements UserData {

	private Language language = Language.ENGLISH;
	private Gadget gadget = Gadget.HOOK_ARROW;
	private DefaultPlayerPerks perks = new DefaultPlayerPerks();
	private boolean particles = false;
	private HeightDisplay display;
	
	@Override
	public Language getLanguage() {
		return language;
	}

	@Override
	public PlayerPerks getPerks() {
		return perks;
	}

	@Override
	public Gadget getGadget() {
		return gadget;
	}

	@Override
	public void setGadget(Gadget gadget) {
		this.gadget = gadget;
	}

	@Override
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	@Override
	public boolean isParticles() {
		return particles;
	}

	@Override
	public void setParticles(boolean particles) {
		this.particles = particles;
	}

	@Override
	public HeightDisplay getHeightDisplay() {
		return display;
	}

	@Override
	public void setHeightDisplay(HeightDisplay display) {
		this.display = display;
	}
}