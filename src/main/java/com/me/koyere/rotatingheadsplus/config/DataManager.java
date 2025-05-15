package com.me.koyere.rotatingheadsplus.config;

import com.me.koyere.rotatingheadsplus.RotatingHeadsPlus;
import com.me.koyere.rotatingheadsplus.animation.*;
import com.me.koyere.rotatingheadsplus.armorstand.RotatingArmorStand;
import com.me.koyere.rotatingheadsplus.entity.RotatingEntity;
import com.me.koyere.rotatingheadsplus.head.RotatingHead;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ArmorStand;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * Loads and saves rotating entity animation data from/to YAML files.
 */
public class DataManager {

    private final RotatingHeadsPlus plugin = RotatingHeadsPlus.getInstance();

    /**
     * Loads all YAML animation files from the /data/ folder.
     */
    public void loadHeads() {
        File dataFolder = new File(plugin.getDataFolder(), "data");
        if (!dataFolder.exists()) dataFolder.mkdirs();

        File[] files = dataFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) return;

        for (File file : files) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            if (config.contains("location")) {
                loadHeadFromLocation(config);
            } else if (config.contains("entity")) {
                loadEntityAnimation(config);
            }
        }
    }

    /**
     * Loads a RotatingHead from block coordinates.
     */
    private void loadHeadFromLocation(YamlConfiguration config) {
        String worldName = config.getString("location.world");
        int x = config.getInt("location.x");
        int y = config.getInt("location.y");
        int z = config.getInt("location.z");

        World world = Bukkit.getWorld(worldName);
        if (world == null) return;

        Location location = new Location(world, x, y, z);
        RotatingHead head = new RotatingHead(location);
        plugin.getHeadManager().registerHead(head);

        HeadAnimation animation = parseAnimation(config, head.getCurrentYaw());
        if (animation != null) {
            plugin.getAnimationScheduler().assignAnimation(head, animation);
        }
    }

    /**
     * Loads a RotatingArmorStand or RotatingEntity by UUID and world.
     */
    private void loadEntityAnimation(YamlConfiguration config) {
        String worldName = config.getString("entity.world");
        String uuidStr = config.getString("entity.uuid");
        String type = config.getString("entity.type", "entity").toLowerCase();

        World world = Bukkit.getWorld(worldName);
        if (world == null || uuidStr == null) return;

        UUID uuid;
        try {
            uuid = UUID.fromString(uuidStr);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid UUID in config: " + uuidStr);
            return;
        }

        Entity entity = Bukkit.getEntity(uuid);
        if (entity == null || !entity.isValid()) return;

        HeadAnimation animation = parseAnimation(config, entity.getLocation().getYaw());
        if (animation == null) return;

        switch (type) {
            case "armorstand" -> {
                if (entity instanceof ArmorStand armorStand) {
                    RotatingArmorStand stand = new RotatingArmorStand(armorStand);
                    plugin.getAnimationScheduler().assignAnimation(stand, animation);
                }
            }
            case "entity", "mob", "zombie", "villager" -> {
                RotatingEntity rotating = new RotatingEntity(entity);
                plugin.getAnimationScheduler().assignAnimation(rotating, animation);
            }
        }
    }

    /**
     * Parses the animation section of a YAML config and returns a HeadAnimation.
     */
    private HeadAnimation parseAnimation(YamlConfiguration config, float defaultYaw) {
        String type = config.getString("animation.type", "circular").toLowerCase();

        switch (type) {
            case "pingpong" -> {
                float minYaw = (float) config.getDouble("animation.minYaw", defaultYaw - 45);
                float maxYaw = (float) config.getDouble("animation.maxYaw", defaultYaw + 45);
                float step = (float) config.getDouble("animation.step", 3.0);
                int interval = config.getInt("animation.interval", 2);
                return new PingPongRotation(minYaw, maxYaw, step, interval);
            }
            case "circular" -> {
                float startYaw = (float) config.getDouble("animation.startYaw", defaultYaw);
                float speed = (float) config.getDouble("animation.speed", 5.0);
                int interval = config.getInt("animation.interval", 2);
                return new CircularRotation(startYaw, speed, true, interval);
            }
            case "static" -> {
                float yaw = (float) config.getDouble("animation.yaw", defaultYaw);
                return new StaticRotation(yaw);
            }
        }

        return null;
    }

    /**
     * Saves all currently animated entities to individual YAML files inside /data/
     */
    public void saveAll() {
        File dataFolder = new File(plugin.getDataFolder(), "data");
        if (!dataFolder.exists()) dataFolder.mkdirs();

        for (UUID id : plugin.getAnimationScheduler().getAllAnimatedIds()) {
            Rotatable obj = plugin.getAnimationScheduler().getRotatableById(id);
            HeadAnimation animation = plugin.getAnimationScheduler().getAnimation(obj);

            if (obj == null || animation == null) continue;

            YamlConfiguration config = new YamlConfiguration();

            Location loc = obj.getLocation();
            String world = loc.getWorld() != null ? loc.getWorld().getName() : "world";

            if (obj instanceof RotatingHead) {
                config.set("location.world", world);
                config.set("location.x", loc.getBlockX());
                config.set("location.y", loc.getBlockY());
                config.set("location.z", loc.getBlockZ());

            } else {
                config.set("entity.world", world);
                config.set("entity.uuid", obj.getUniqueId().toString());

                if (obj instanceof RotatingArmorStand) {
                    config.set("entity.type", "armorstand");
                } else {
                    config.set("entity.type", "entity");
                }
            }

            config.set("animation.type", animation.getName());

            // Basic fallbacks since getters like getMinYaw() may not exist
            if (animation instanceof PingPongRotation) {
                config.set("animation.minYaw", 0f);
                config.set("animation.maxYaw", 180f);
                config.set("animation.step", 3.0);
                config.set("animation.interval", animation.getTickInterval());

            } else if (animation instanceof CircularRotation) {
                config.set("animation.startYaw", 0f);
                config.set("animation.speed", 5.0);
                config.set("animation.interval", animation.getTickInterval());

            } else if (animation instanceof StaticRotation) {
                config.set("animation.yaw", 90f);
            }

            File outFile = new File(dataFolder, id.toString() + ".yml");
            try {
                config.save(outFile);
            } catch (IOException e) {
                plugin.getLogger().warning("Could not save animation data for UUID: " + id);
            }
        }
    }
}
