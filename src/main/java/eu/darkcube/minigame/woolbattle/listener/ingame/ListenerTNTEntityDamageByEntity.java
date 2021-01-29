package eu.darkcube.minigame.woolbattle.listener.ingame;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import eu.darkcube.minigame.woolbattle.Main;
import eu.darkcube.minigame.woolbattle.listener.Listener;
import eu.darkcube.minigame.woolbattle.perk.PerkType;
import eu.darkcube.minigame.woolbattle.user.User;

public class ListenerTNTEntityDamageByEntity extends Listener<EntityDamageByEntityEvent> {

	@Override
	@EventHandler
	public void handle(EntityDamageByEntityEvent e) {
		if (e.getEntityType() != EntityType.PLAYER) {
			return;
		}
		Player p = (Player) e.getEntity();
		User user = Main.getInstance().getUserWrapper().getUser(p.getUniqueId());
		if (e.getDamager().getType() == EntityType.PRIMED_TNT) {
			TNTPrimed tnt = (TNTPrimed) e.getDamager();
			if (tnt.getLocation().distance(p.getLocation()) > tnt.getYield()) {
				e.setCancelled(true);
				return;
			}
			if (!(tnt.getSource() instanceof Player)) {
				return;
			}
			Player a = (Player) tnt.getSource();
			User attacker = Main.getInstance().getUserWrapper().getUser(a.getUniqueId());
			e.setCancelled(true);
			double x = p.getLocation().getX() - tnt.getLocation().getX();
//			double y = Math.abs(p.getLocation().getY() - tnt.getLocation().getY()) * .3 + 1;
//			double y = 1;
			double y = p.getLocation().getY() - tnt.getLocation().getY();
			y = y < 0.7 ? 0.7 : y;
			double z = p.getLocation().getZ() - tnt.getLocation().getZ();
//			x = Math.max(Math.min(x, 3), -3);
//			y = Math.max(Math.min(y, 3), -0) + .5;
//			z = Math.max(Math.min(z, 3), -3);
			double str = tnt.getYield() - p.getLocation().distance(tnt.getLocation());
			str = str < 0.2 ? 0.2 : str;
			Vector v = new Vector(x, y, z).normalize().multiply(str);
			v.multiply(tnt.getMetadata("boost").get(0).asDouble());
			p.setVelocity(v);
//					.multiply(tnt.getYield() - p.getLocation().distance(tnt.getLocation())));
//					.multiply(calc(tnt.getLocation().distance(p.getLocation()), tnt.getYield() + 1)));

			if (user.getTeam() != attacker.getTeam() && !attacker.isTrollMode()) {
				user.setLastHit(attacker);
				user.setTicksAfterLastHit(0);
			}
		} else if (e.getDamager().getType() == EntityType.SNOWBALL) {
			Snowball bomb = (Snowball) e.getDamager();
			if (bomb.getMetadata("perk").size() != 0
					&& bomb.getMetadata("perk").get(0).asString().equals(PerkType.WOOL_BOMB.getPerkName().getName())) {
				e.setCancelled(true);
			}
		}
	}
//
//	private static double calc(double dist, double rad) {
////		return rad - dist <= 0 ? 0 : dist == 0 ? 1 : Math.pow(Math.pow(1 - dist / 1.3 / rad, 3), .6);
//		return rad - dist <= 0 ? 0 : dist == 0 ? 1 : Math.pow(1 - dist / 1.4 / rad, .7);
//	}
}