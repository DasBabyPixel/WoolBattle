package eu.darkcube.minigame.woolbattle.listener.ingame.perk;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import eu.darkcube.minigame.woolbattle.Main;
import eu.darkcube.minigame.woolbattle.game.Ingame;
import eu.darkcube.minigame.woolbattle.listener.Listener;
import eu.darkcube.minigame.woolbattle.perk.Perk;
import eu.darkcube.minigame.woolbattle.perk.PerkType;
import eu.darkcube.minigame.woolbattle.user.User;
import eu.darkcube.minigame.woolbattle.util.Item;
import eu.darkcube.minigame.woolbattle.util.ItemManager;
import eu.darkcube.minigame.woolbattle.util.scheduler.Scheduler;

public class ListenerSafetyPlatformInteract extends Listener<PlayerInteractEvent> {

	public static final Item PLATFORM = PerkType.SAFETY_PLATFORM.getItem();
	public static final Item PLATFORM_COOLDOWN = PerkType.SAFETY_PLATFORM.getCooldownItem();

	@Override
	@EventHandler
	public void handle(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		Player p = e.getPlayer();
		User user = Main.getInstance().getUserWrapper().getUser(p.getUniqueId());

		ItemStack item = e.getItem();
		if (item == null) {
			return;
		}
		String itemid = ItemManager.getItemId(item);
		Perk perk = user.getPerkByItemId(itemid);
		if (perk == null) {
			return;
		}
		if (PLATFORM_COOLDOWN.getItemId().equals(itemid)) {
			deny(user, perk);
			e.setCancelled(true);
			return;
		} else if (!PLATFORM.getItemId().equals(itemid)) {
			return;
		}
		if (perk.getCooldown() > 0 || !p.getInventory().contains(Material.WOOL, PerkType.SAFETY_PLATFORM.getCost())) {
			deny(user, perk);
			e.setCancelled(true);
			return;
		}
		e.setCancelled(true);

		ItemManager.removeItems(p.getInventory(), user.getSingleWoolItem(), PerkType.SAFETY_PLATFORM.getCost());

		setBlocks(user);

		new Scheduler() {
			int cd = perk.getMaxCooldown();

			@Override
			public void run() {
				if (cd <= 0) {
					this.cancel();
					perk.setCooldown(0);
					return;
				}
				perk.setCooldown(cd--);
			}
		}.runTaskTimer(20);

	}

	private void setBlocks(User p) {

		for (int x = -2; x < 3; x++) {
			for (int z = -2; z < 3; z++) {
				if (isCorner(x, z, 2))
					continue;
				block(p.getBukkitEntity().getLocation().add(x, -1, z), p);
			}
		}
		p.getBukkitEntity().teleport(p.getBukkitEntity().getLocation().getBlock().getLocation().add(.5, .25, .5)
				.setDirection(p.getBukkitEntity().getLocation().getDirection()));
	}

	private boolean isCorner(int x, int z, int r) {
		return (x == -r && z == -r) || (x == r && z == -r) || (x == -r && z == r) || (x == r && z == r);
	}

	@SuppressWarnings("deprecation")
	private void block(Location loc, User u) {
		if (loc.getBlock().getType() == Material.AIR) {
			loc.getBlock().setType(Material.WOOL);
			loc.getBlock().setData(u.getTeam().getType().getWoolColor());
			Main.getInstance().getIngame().placedBlocks.add(loc.getBlock());
		}
	}

	private void deny(User user, Perk perk) {
		Ingame.playSoundNotEnoughWool(user);
		setItem(perk);
	}

	private void setItem(Perk perk) {
		perk.getOwner().getBukkitEntity().setItemInHand(perk.calculateItem());
	}
}
