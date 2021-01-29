package eu.darkcube.minigame.woolbattle.util.convertingrule;

import eu.darkcube.minigame.woolbattle.translation.Language;
import eu.darkcube.minigame.woolbattle.util.Arrays.ConvertingRule;

public class ConvertingRuleLanguage extends ConvertingRule<Language> {

	@Override
	public Class<Language> getConvertingClass() {
		return Language.class;
	}

	@Override
	public String convert(Language object) {
		return object.getLocale().getDisplayName(object.getLocale()).toLowerCase();
	}

}
