package eu.darkcube.minigame.woolbattle.command;

import org.bukkit.command.CommandSender;

import eu.darkcube.minigame.woolbattle.Main;
import eu.darkcube.minigame.woolbattle.util.StatsLink;
import eu.darkcube.system.commandapi.Command;

public class CommandIsStats extends Command {

	public CommandIsStats() {
		super(Main.getInstance(), "isStats", new Command[0], "IsStats");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		Main.getInstance().sendMessage(
				"§7Statistiken sind " + (StatsLink.enabled ? "§aaktiviert" : "§cdeaktiviert") + "!", sender);
		return true;
	}
}