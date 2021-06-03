package eu.darkcube.minigame.woolbattle.listener.ingame;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import eu.darkcube.minigame.woolbattle.Main;
import eu.darkcube.minigame.woolbattle.game.Ingame;
import eu.darkcube.minigame.woolbattle.listener.Listener;
import eu.darkcube.minigame.woolbattle.user.User;

public class ListenerPlayerJoin extends Listener<PlayerJoinEvent> {
	@Override
	@EventHandler
	public void handle(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		final User user = Main.getInstance().getUserWrapper().getUser(p.getUniqueId());
		Ingame ingame = Main.getInstance().getIngame();
		p.setGameMode(GameMode.SURVIVAL);
		p.resetMaxHealth();
		p.resetPlayerTime();
		p.resetPlayerWeather();
		p.setAllowFlight(true);
		p.setExhaustion(0);
		p.setExp(0);
		p.setLevel(0);
		p.setFoodLevel(20);
		p.setHealth(20);
		p.setItemOnCursor(null);
		p.setSaturation(0);
		e.setJoinMessage(null);
		user.setTicksAfterLastHit(1200);
		Main.getInstance().getTeamManager().setTeam(user, Main.getInstance().getTeamManager().getSpectator());
//		ingame.setSpectator(user);
	}
}