package eu.darkcube.minigame.woolbattle.listener.ingame;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import eu.darkcube.minigame.woolbattle.Main;
import eu.darkcube.minigame.woolbattle.game.Ingame;
import eu.darkcube.minigame.woolbattle.listener.Listener;
import eu.darkcube.minigame.woolbattle.listener.ingame.standard.ListenerDoubleJump;
import eu.darkcube.minigame.woolbattle.team.TeamType;
import eu.darkcube.minigame.woolbattle.user.User;
import eu.darkcube.minigame.woolbattle.util.ItemManager;

public class ListenerBlockBreak extends Listener<BlockBreakEvent> {

	@SuppressWarnings("deprecation")
	@Override
	@EventHandler
	public synchronized void handle(BlockBreakEvent e) {
		Player p = e.getPlayer();
		User user = Main.getInstance().getUserWrapper().getUser(p.getUniqueId());
		Block block = e.getBlock();
		e.setExpToDrop(0);
		if (!user.isTrollMode()) {
			if (user.getTeam().getType() == TeamType.SPECTATOR) {
				e.setCancelled(true);
				return;
			}
		} else {
			if (e.getBlock().getType() != Material.WOOL) {
				e.getBlock().setType(Material.AIR);
				return;
			}
		}
		Material type = block.getType();
		if (type == Material.WOOL) {
			Ingame ingame = Main.getInstance().getIngame();
			if (!ingame.placedBlocks.contains(block)) {
				ingame.breakedWool.put(block, block.getData());
			} else {
				ingame.placedBlocks.remove(block);
			}
			Ingame.resetBlockDamage(block);
			int count = ItemManager.countItems(type, p.getInventory());
			int tryadd = user.getWoolBreakAmount();
			int fullInv = user.getMaxWoolSize();
			int freeSpace = fullInv - count;
			int remaining = tryadd - freeSpace;
			boolean shallAdd = false;
			ItemStack addItem = null;
			if (remaining > 0) {
				if (tryadd - remaining > 0) {
					addItem = new ItemStack(Material.WOOL, tryadd - remaining, user.getTeam().getType().getWoolColor());
					shallAdd = true;
				}
			} else if (freeSpace > 0) {
				addItem = new ItemStack(Material.WOOL, tryadd, user.getTeam().getType().getWoolColor());
				shallAdd = true;
			}
			if (shallAdd) {
				p.getInventory().addItem(addItem);
				if (ItemManager.countItems(Material.WOOL, p.getInventory()) >= ListenerDoubleJump.COST)
					ingame.listenerDoubleJump.refresh(p);
				playSound(p);
			}
			e.getBlock().setType(Material.AIR);
			return;
		}
		if (!Main.getInstance().getIngame().placedBlocks.contains(block)) {
			e.setCancelled(true);
		}
	}

	private void playSound(Player p) {
		p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1, 1);
	}
}