package eu.darkcube.minigame.woolbattle.user;

import eu.darkcube.minigame.woolbattle.gadget.Gadget;
import eu.darkcube.minigame.woolbattle.perk.PlayerPerks;
import eu.darkcube.minigame.woolbattle.translation.Language;
import eu.darkcube.minigame.woolbattle.util.WoolSubtractDirection;

public interface UserData {

	Language getLanguage();

	PlayerPerks getPerks();

	Gadget getGadget();
	
	WoolSubtractDirection getWoolSubtractDirection();
	
	void setWoolSubtractDirection(WoolSubtractDirection dir);
	
	HeightDisplay getHeightDisplay();
	
	void setHeightDisplay(HeightDisplay display);

	void setGadget(Gadget gadget);

	void setLanguage(Language language);

	boolean isParticles();

	void setParticles(boolean particles);
}
