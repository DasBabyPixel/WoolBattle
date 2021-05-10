package eu.darkcube.minigame.woolbattle.command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import eu.darkcube.minigame.woolbattle.Main;
import eu.darkcube.minigame.woolbattle.mysql.MySQL;
import eu.darkcube.minigame.woolbattle.translation.Message;
import eu.darkcube.minigame.woolbattle.user.User;
import eu.darkcube.minigame.woolbattle.util.Arrays;
import eu.darkcube.system.commandapi.Argument;
import eu.darkcube.system.commandapi.Command;
import eu.darkcube.system.language.core.Language;

public class CommandLanguage extends Command {

	public CommandLanguage() {
		super(Main.getInstance(), "language", new Command[0], "Change your language",
				new Argument("language", "Your new language"));
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			User user = Main.getInstance().getUserWrapper().getUser(p.getUniqueId());
			if (args.length == 1) {
				String lang = args[0];
				Language language = null;
				for (Language l : Language.values()) {
					if (lang.equalsIgnoreCase(l.getLocale().getDisplayName(l.getLocale()))) {
						language = l;
						break;
					}
				}
				if (language == null) {
					sender.sendMessage(Message.UNKNOWN_LANGUAGE.getMessage(user, lang));
					return true;
				}
				user.setLanguage(language);
				sender.sendMessage(Message.CHANGED_LANGUAGE.getMessage(user,
						language.getLocale().getDisplayName(user.getLanguage().getLocale())));
				MySQL.saveUserData(user);
				if (Main.getInstance().getLobby().isEnabled())
					Main.getInstance().getLobby().listenerPlayerJoin.handle(new PlayerJoinEvent(p, null));
				if(Main.getInstance().getIngame().isEnabled())
					Main.getInstance().getIngame().setPlayerItems(user);
			} else {
				sender.sendMessage(Message.COMMAND_LANGUAGE_USAGE.getMessage(user));
			}
		} else {
			sender.sendMessage(Message.NO_PLAYER.getServerMessage());
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		if (args.length == 1) {
			return Arrays.toSortedStringList(Language.values(), args[0].toLowerCase());
		}
		return super.onTabComplete(args);
	}
}