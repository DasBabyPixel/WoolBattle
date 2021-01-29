package eu.darkcube.minigame.woolbattle.translation;

import org.bukkit.ChatColor;

import eu.darkcube.minigame.woolbattle.Main;
import eu.darkcube.minigame.woolbattle.user.User;

public enum Message {

	NO_PLAYER, COMMAND_LANGUAGE_USAGE, UNKNOWN_LANGUAGE, CHANGED_LANGUAGE, ALREADY_IN_TEAM, CHANGED_TEAM, TEAM_IS_FULL,
	EP_GLITCH_ON, EP_GLITCH_OFF, VOTED_FOR_EP_GLITCH, VOTED_AGAINST_EP_GLITCH, ALREADY_VOTED_FOR_THIS, VOTED_FOR_MAP,
	NOT_IMPLEMENTED, ALREADY_VOTED_FOR_MAP, SELECTED, ALREADY_SELECTED_PERK, AT_ALL, TIMER_CHANGED,
	ENTER_POSITIVE_NUMBER, PLAYER_JOINED, PLAYER_LEFT, CHANGED_LIFES, PLAYER_DIED, PLAYER_WAS_KILLED_BY_PLAYER,

	INVENTORY_PERKS, INVENTORY_VOTING, INVENTORY_VOTING_MAPS, INVENTORY_VOTING_EP_GLITCH, INVENTORY_TEAMS,
	TEAM_WAS_ELIMINATED, INVENTORY_COMPASS, TEAM_HAS_WON, PLAYER_HAS_MOST_KILLS, STATS_PLACE_3, STATS_PLACE_2,
	STATS_PLACE_1, STATS_PLACE_TH, INVENTORY_VOTING_LIFES, SETTINGS_TITLE, HEIGHT_DISPLAY_SETTINGS_TITLE,
	HEIGHT_DISPLAY_COLOR_SETTINGS_TITLE, COSTS, COSTS_PER_BLOCK, COOLDOWN, CLICK_TO_SELECT, KILLSTREAK

	;

	public static final String ITEM_PREFIX = "ITEM_";
	public static final String LORE_PREFIX = "LORE_";
	public static final String TEAM_PREFIX = "TEAM_";

	private final String key;

	Message() {
		this.key = this.name();
	}

	public static final String getMessage(String messageKey, Language language, String... replacements) {
		try {
			String msg = language.getBundle().getString(messageKey);
			for (int i = 0; msg.contains("{}") && i < replacements.length; i++) {
				msg = msg.replaceFirst("\\{\\}", replacements[i]);
			}
			return ChatColor.translateAlternateColorCodes('&', msg);
		} catch (Exception ex) {
			StringBuilder builder = new StringBuilder();
			builder.append(messageKey);
			if (replacements.length > 0) {
				builder.append('[');
				for (int i = 0; i + 1 < replacements.length; i++) {
					builder.append(replacements[i]).append(',');
				}
				builder.append(replacements[replacements.length - 1]).append(']');
			}
			return builder.toString();
		}
	}

	public final String getMessage(Language language, String... replacements) {
		return getMessage(key, language, replacements);
	}

	public final String getServerMessage(String... replacements) {
		return getMessage(Main.getInstance().getServerLanguage(), replacements);
	}

	public final String getMessage(User user, String... replacements) {
		return getMessage(user.getLanguage(), replacements);
	}
}