package eu.darkcube.minigame.woolbattle.command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.darkcube.minigame.woolbattle.Main;
import eu.darkcube.minigame.woolbattle.translation.Message;
import eu.darkcube.minigame.woolbattle.user.User;
import eu.darkcube.minigame.woolbattle.util.Arrays;
import eu.darkcube.system.commandapi.Command;

public class CommandSetLifes extends Command {

	public CommandSetLifes() {
		super(Main.getInstance(), "setlifes", new Command[0],
						"Setzt die Rundenleben", CommandArgument.LIFES);
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		if (args.length == 1) {
			return Arrays.toSortedStringList(Arrays.asList(0, 6, 12, 17, 22, 99), args[0]);
		}
		return super.onTabComplete(args);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length == 1) {
			User user = null;
			if (sender instanceof Player)
				user = Main.getInstance().getUserWrapper().getUser(((Player) sender).getUniqueId());
			Integer lifes = null;
			try {
				lifes = Integer.parseInt(args[0]);
				if (lifes < 0) {
					lifes = 0;
				} else if (lifes > 999) {
					lifes = 999;
				}
			} catch (Exception ex) {
			}
			if (lifes == null) {
				if (user != null)
					sender.sendMessage(Message.ENTER_POSITIVE_NUMBER.getMessage(user));
				else
					sender.sendMessage(Message.ENTER_POSITIVE_NUMBER.getServerMessage());
				return true;
			}
			Main.getInstance().baseLifes = lifes;
			if (user != null) {
				sender.sendMessage(Message.CHANGED_LIFES.getMessage(user, Integer.toString(lifes)));
			} else {
				sender.sendMessage(Message.CHANGED_LIFES.getServerMessage(Integer.toString(lifes)));
			}
			return true;
		}
		return false;
	}
}