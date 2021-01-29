package eu.darkcube.minigame.woolbattle.user;

import java.util.UUID;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.inventory.ItemStack;

import eu.darkcube.minigame.woolbattle.Main;
import eu.darkcube.minigame.woolbattle.gadget.Gadget;
import eu.darkcube.minigame.woolbattle.perk.Perk;
import eu.darkcube.minigame.woolbattle.perk.PlayerPerks;
import eu.darkcube.minigame.woolbattle.team.Team;
import eu.darkcube.minigame.woolbattle.translation.Language;
import eu.darkcube.minigame.woolbattle.util.InventoryId;
import net.minecraft.server.v1_8_R3.Packet;

public class ConsoleUser implements User {

	private UserData data = new ConsoleUserData();

	@Override
	public UUID getUniqueId() {
		return UUID.randomUUID();
	}

	@Override
	public String getPlayerName() {
		return "console";
	}

	@Override
	public String getTeamPlayerName() {
		return "console";
	}

	@Override
	public Language getLanguage() {
		return data.getLanguage();
	}

	@Override
	public void setLanguage(Language language) {
	}

	@Override
	public Team getTeam() {
		return null;
	}

	@Override
	public void setTeam(Team team) {

	}

	@Override
	public UserData getData() {
		return null;
	}

	@Override
	public void sendPacket(Packet<?> packet) {
	}

	@Override
	public int getMaxWoolSize() {
		return 0;
	}

	@Override
	public int getWoolBreakAmount() {
		return 0;
	}

	@Override
	public int getSpawnProtectionTicks() {
		return 0;
	}

	@Override
	public boolean hasSpawnProtection() {
		return false;
	}

	@Override
	public void setSpawnProtectionTicks(int ticks) {
	}

	@Override
	public void setTrollMode(boolean trollmode) {
	}

	@Override
	public boolean isTrollMode() {
		return false;
	}

	@Override
	public CraftPlayer getBukkitEntity() {
		return null;
	}

	@Override
	public InventoryId getOpenInventory() {
		return null;
	}

	@Override
	public void setOpenInventory(InventoryId id) {
	}

	@Override
	public int getKills() {
		return 0;
	}

	@Override
	public int getDeaths() {
		return 0;
	}

	@Override
	public void setKills(int kills) {
	}

	@Override
	public void setDeaths(int deaths) {
	}

	private final class ConsoleUserData implements UserData {

		@Override
		public Language getLanguage() {
			return Main.getInstance().getServerLanguage();
		}

		@Override
		public PlayerPerks getPerks() {
			return null;
		}

		@Override
		public Gadget getGadget() {
			return null;
		}

		@Override
		public void setGadget(Gadget gadget) {

		}

		@Override
		public void setLanguage(Language language) {
		}

		@Override
		public boolean isParticles() {
			return false;
		}

		@Override
		public void setParticles(boolean particles) {
		}

		@Override
		public HeightDisplay getHeightDisplay() {
			return null;
		}

		@Override
		public void setHeightDisplay(HeightDisplay display) {
		}

	}

	@Override
	public User getLastHit() {
		return null;
	}

	@Override
	public void setLastHit(User user) {

	}

	@Override
	public int getTicksAfterLastHit() {
		return 0;
	}

	@Override
	public void setTicksAfterLastHit(int ticks) {

	}

	@Override
	public void loadPerks() {

	}

	@Override
	public Perk getActivePerk1() {
		return null;
	}

	@Override
	public Perk getActivePerk2() {
		return null;
	}

	@Override
	public Perk getPassivePerk() {
		return null;
	}

	@Override
	public Perk getEnderPearl() {
		return null;
	}

	@Override
	public Perk getPerkByItemId(String itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getKD() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getSingleWoolItem() {
		// TODO Auto-generated method stub
		return null;
	}

}