package com.me.koyere.rotatingheadsplus.config;

import com.me.koyere.rotatingheadsplus.RotatingHeadsPlus;
import com.me.koyere.rotatingheadsplus.animation.*;
import com.me.koyere.rotatingheadsplus.head.RotatingHead;
import com.me.koyere.rotatingheadsplus.armorstand.RotatingArmorStand;
import com.me.koyere.rotatingheadsplus.entity.RotatingEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

import java.io.File;
import java.util.UUID;

public class DataManager {

    private final RotatingHeadsPlus plugin = RotatingHeadsPlus.getInstance();

    /**
     * Loads all .yml files from /data and applies the defined animations.
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
     * Loads a rotating head from a fixed block location.
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
     * Loads and animates an entity (ArmorStand or any Entity) by UUID.
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
     * Parses the animation section from YAML and creates a valid HeadAnimation.
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
     * (Optional) Persists all registered heads/entities to disk in the future.
     */
    public void saveAll() {
        // Not implemented yet. Can be added later if persistence is needed.
    }
}
