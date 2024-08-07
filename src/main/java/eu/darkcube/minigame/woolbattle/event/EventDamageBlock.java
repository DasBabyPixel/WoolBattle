package eu.darkcube.minigame.woolbattle.event;

import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

public class EventDamageBlock extends BlockEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	private final int oldDamage;
	private int newDamage;
	private boolean cancel = false;

	public EventDamageBlock(Block theBlock, int oldDamage, int newDamage) {
		super(theBlock);
		this.oldDamage = oldDamage;
		this.newDamage = newDamage;
	}

	public int getNewDamage() {
		return newDamage;
	}

	public int getOldDamage() {
		return oldDamage;
	}

	public void setNewDamage(int newDamage) {
		this.newDamage = newDamage;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
}