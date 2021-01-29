package eu.darkcube.minigame.woolbattle.map;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.darkcube.minigame.woolbattle.Main;
import eu.darkcube.minigame.woolbattle.util.Locations;
import eu.darkcube.minigame.woolbattle.util.MaterialAndId;
import eu.darkcube.minigame.woolbattle.util.gson.TypeAdapterLocation;
import eu.darkcube.minigame.woolbattle.util.gson.TypeAdapterMap;

public class DefaultMap implements Map {

	static final Gson GSON;

	static {
		GSON = new GsonBuilder().registerTypeAdapter(Location.class, TypeAdapterLocation.INSTANCE)
				.registerTypeAdapter(Map.class, TypeAdapterMap.INSTANCE).create();
	}

	private String name;
	private int deathHeight;
	private boolean enabled;
	private MaterialAndId icon;
	private final java.util.Map<String, Location> spawns;

	DefaultMap() {
		spawns = null;
	}

	public DefaultMap(String name) {
		this.name = name;
		spawns = new HashMap<>();
		enabled = false;
		icon = new MaterialAndId(Material.GRASS);
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public int getDeathHeight() {
		return deathHeight;
	}

	@Override
	public MaterialAndId getIcon() {
		return icon;
	}

	@Override
	public void setDeathHeight(int height) {
		this.deathHeight = height;
		save();
	}

	@Override
	public void setIcon(MaterialAndId icon) {
		this.icon = icon;
		save();
	}

	@Override
	public void delete() {
		Main.getInstance().getMapManager().deleteMap(this);
	}

	private void save() {
		Main.getInstance().getMapManager().saveMaps();
	}

	@Override
	public void enable() {
		enabled = true;
		save();
	}

	@Override
	public void disable() {
		enabled = false;
		save();
	}

	@Override
	public void setSpawn(String name, Location loc) {
		spawns.put(name, loc);
		save();
	}

	@Override
	public Location getSpawn(String name) {
		if (spawns == null)
			return null;
		Location spawn = spawns.get(name);
		return spawn != null ? spawn : Locations.DEFAULT_LOCATION;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String serialize() {
		return GSON.toJson(this);
	}
}