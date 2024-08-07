package eu.darkcube.minigame.woolbattle.user;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.inventory.ItemStack;

import eu.darkcube.minigame.woolbattle.Main;
import eu.darkcube.minigame.woolbattle.gadget.Gadget;
import eu.darkcube.minigame.woolbattle.game.Ingame;
import eu.darkcube.minigame.woolbattle.mysql.MySQL;
import eu.darkcube.minigame.woolbattle.perk.Perk;
import eu.darkcube.minigame.woolbattle.perk.PerkEnderPearl;
import eu.darkcube.minigame.woolbattle.perk.PerkName;
import eu.darkcube.minigame.woolbattle.perk.PerkNumber;
import eu.darkcube.minigame.woolbattle.perk.PlayerPerks;
import eu.darkcube.minigame.woolbattle.team.Team;
import eu.darkcube.minigame.woolbattle.translation.Language;
import eu.darkcube.minigame.woolbattle.util.Arrays;
import eu.darkcube.minigame.woolbattle.util.InventoryId;
import eu.darkcube.minigame.woolbattle.util.WoolSubtractDirection;
import net.minecraft.server.v1_8_R3.Packet;

class DefaultUser implements User {

	private final UserData userData;
	private final UUID uuid;
	private final CraftPlayer player;
	private boolean trollmode;
	private int spawnProtectionTicks;
	private InventoryId openInventory;
	private int kills;
	private int deaths;
	private User lastHit;
	private int ticksAfterLastHit;
	private Perk activePerk1;
	private Perk activePerk2;
	private Perk passivePerk;
	private Perk enderPearl;

	public DefaultUser(UUID uuid, UserData userData, CraftPlayer player) {
		this.player = player;
		this.userData = new UserUserData(userData);
		this.uuid = uuid;
		spawnProtectionTicks = 0;
		trollmode = false;
		kills = 0;
		deaths = 0;
	}

	@Override
	public User getLastHit() {
		return lastHit;
	}

	@Override
	public String toString() {
		return getPlayerName();
	}

	@Override
	public void loadPerks() {
		activePerk1 = getData().getPerks().getActivePerk1().toType().newPerkTypePerk(this, PerkNumber.ACTIVE_1);
		activePerk2 = getData().getPerks().getActivePerk2().toType().newPerkTypePerk(this, PerkNumber.ACTIVE_2);
		passivePerk = getData().getPerks().getPassivePerk().toType().newPerkTypePerk(this, PerkNumber.PASSIVE);
		enderPearl = new PerkEnderPearl(this);
	}

	@Override
	public Perk getPerkByItemId(String itemId) {
		for (Perk p : Arrays.asList(activePerk1, activePerk2, passivePerk)) {
			if (p != null) {
				if (p.getItem().getItemId().equals(itemId) || p.getCooldownItem().getItemId().equals(itemId)) {
					return p;
				}
			}
		}
		return null;
	}

	@Override
	public void setLastHit(User user) {
		this.lastHit = user;
	}

	@Override
	public int getTicksAfterLastHit() {
		return ticksAfterLastHit;
	}

	@Override
	public void setTicksAfterLastHit(int ticks) {
		ticksAfterLastHit = ticks;
//		if (getBukkitEntity().getLocation().getY() < 50 && ticks == 0) {
//			getBukkitEntity().playSound(getBukkitEntity().getLocation(), Sound.NOTE_PLING, 1, 1);
//		}
	}

	private final class UserUserData implements UserData {

		private UserData data;

		private UserUserData(UserData data) {
			this.data = data;
		}

		@Override
		public Language getLanguage() {
			return data.getLanguage();
		}

		@Override
		public PlayerPerks getPerks() {
			return data.getPerks();
		}

		@Override
		public Gadget getGadget() {
			return data.getGadget();
		}

		@Override
		public void setGadget(Gadget gadget) {
			data.setGadget(gadget);
		}

		@Override
		public void setLanguage(Language language) {
			data.setLanguage(language);
		}

		@Override
		public boolean isParticles() {
			return data.isParticles();
		}

		@Override
		public String toString() {
			return data.toString();
		}

		@Override
		public void setParticles(boolean particles) {
			if (particles) {
				Main.getInstance().getIngame().particlePlayers.add(getBukkitEntity());
			} else {
				Main.getInstance().getIngame().particlePlayers.remove(getBukkitEntity());
			}
			data.setParticles(particles);
		}

		@Override
		public HeightDisplay getHeightDisplay() {
			return data.getHeightDisplay();
		}

		@Override
		public void setHeightDisplay(HeightDisplay display) {
			data.setHeightDisplay(display);
		}

		@Override
		public WoolSubtractDirection getWoolSubtractDirection() {
			return data.getWoolSubtractDirection();
		}

		@Override
		public void setWoolSubtractDirection(WoolSubtractDirection dir) {
			data.setWoolSubtractDirection(dir);
		}

	}

	private boolean hasPerk(PerkName perk) {
		return getData().getPerks().getActivePerk1().equals(perk) || getData().getPerks().getActivePerk2().equals(perk)
				|| getData().getPerks().getPassivePerk().equals(perk);
	}

	@Override
	public UUID getUniqueId() {
		return uuid;
	}

	@Override
	public Team getTeam() {
		return Main.getInstance().getTeamManager().getTeam(this);
	}

	@Override
	public int getMaxWoolSize() {
		if (hasPerk(PerkName.BACKPACK)) {
			return 64 * 6;
		}
		return 64 * 3;
	}

	@Override
	public int getSpawnProtectionTicks() {
		return spawnProtectionTicks;
	}

	@Override
	public void setSpawnProtectionTicks(int ticks) {
		this.spawnProtectionTicks = ticks;
		getBukkitEntity().setExp((float) getSpawnProtectionTicks() / (float) Ingame.SPAWNPROTECTION_TICKS);
	}

	@Override
	public int getWoolBreakAmount() {
		if (hasPerk(PerkName.DOUBLE_WOOL)) {
			return 4;
		}
		return 2;
	}

	@Override
	public Language getLanguage() {
		return getData().getLanguage();
	}

	@Override
	public void setLanguage(Language language) {
		getData().setLanguage(language);
		MySQL.saveUserData(this);
	}

	@Override
	public UserData getData() {
		return userData;
	}

	@Override
	public void sendPacket(Packet<?> packet) {
		getBukkitEntity().getHandle().playerConnection.sendPacket(packet);
	}

	@Override
	public CraftPlayer getBukkitEntity() {
		return player;
	}

	@Override
	public String getPlayerName() {
		return getBukkitEntity().getName();
	}

	@Override
	public String getTeamPlayerName() {
		return ChatColor.getByChar(getTeam().getType().getNameColor()).toString() + getPlayerName();
	}

	@Override
	public InventoryId getOpenInventory() {
		return openInventory;
	}

	@Override
	public void setOpenInventory(InventoryId id) {
		this.openInventory = id;
	}

	@Override
	public void setTeam(Team team) {
		Main.getInstance().getTeamManager().setTeam(this, team);
	}

	@Override
	public void setTrollMode(boolean trollmode) {
		this.trollmode = trollmode;
		Main.getInstance().sendMessage("§aTrollmode " + (trollmode ? "§aAn" : "§cAus"), getBukkitEntity());
	}

	@Override
	public boolean isTrollMode() {
		return trollmode;
	}

	@Override
	public boolean hasSpawnProtection() {
		return getSpawnProtectionTicks() > 0;
	}

	@Override
	public int getKills() {
		return kills;
	}

	@Override
	public int getDeaths() {
		return deaths;
	}

	@Override
	public void setKills(int kills) {
		this.kills = kills;
		if (getBukkitEntity() != null) {
			getBukkitEntity().setLevel(kills);
		}
	}

	@Override
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	@Override
	public Perk getActivePerk1() {
		return activePerk1;
	}

	@Override
	public Perk getActivePerk2() {
		return activePerk2;
	}

	@Override
	public Perk getPassivePerk() {
		return passivePerk;
	}

	@Override
	public Perk getEnderPearl() {
		return enderPearl;
	}

	@Override
	public double getKD() {
		return getDeaths() != 0 ? (double) getKills() / (double) getDeaths() : getKills();
	}

	@Override
	public ItemStack getSingleWoolItem() {
		return new ItemStack(Material.WOOL, 1, getTeam().getType().getWoolColor());
	}

	@Override
	public void setActivePerk1(Perk perk) {
		this.activePerk1 = perk;
	}

	@Override
	public void setActivePerk2(Perk perk) {
		this.activePerk2 = perk;
	}

	@Override
	public void setPassivePerk(Perk perk) {
		this.passivePerk = perk;
	}

	@Override
	public void setEnderPearl(Perk perk) {
		this.enderPearl = perk;
	}

	@Override
	public Perk getPerk(PerkNumber number) {
		switch (number) {
		case ACTIVE_1:
			return getActivePerk1();
		case ACTIVE_2:
			return getActivePerk2();
		case PASSIVE:
			return getPassivePerk();
		case ENDER_PEARL:
			return getEnderPearl();
		default:
			break;
		}
		return null;
	}
}
