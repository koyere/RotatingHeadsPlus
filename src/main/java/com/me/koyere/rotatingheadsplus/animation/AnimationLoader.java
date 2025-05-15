package com.me.koyere.rotatingheadsplus.animation;

import com.me.koyere.rotatingheadsplus.actions.ActionEntry;
import com.me.koyere.rotatingheadsplus.actions.ClickAction;
import com.me.koyere.rotatingheadsplus.actions.ClickTrigger;
import com.me.koyere.rotatingheadsplus.rotating.Rotatable;
import com.me.koyere.rotatingheadsplus.rotating.RotatingArmorStand;
import com.me.koyere.rotatingheadsplus.rotating.RotatingEntity;
import com.me.koyere.rotatingheadsplus.rotating.RotatingHead;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Zombie;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;

/**
 * Loads animations from YAML files in the /animations folder.
 * Supports type, location, frames, conditions and click actions.
 */
public class AnimationLoader {

    private final Plugin plugin;
    private final Logger logger;

    public AnimationLoader(Plugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
    }

    public void loadAllAnimations() {
        File folder = new File(plugin.getDataFolder(), "animations");
        if (!folder.exists()) {
            folder.mkdirs();
            logger.info("Created animations folder: " + folder.getPath());
            return;
        }

        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".yml"));
        if (files == null || files.length == 0) {
            logger.info("No animation files found in: " + folder.getPath());
            return;
        }

        for (File file : files) {
            try {
                loadAnimationFromFile(file);
            } catch (Exception e) {
                logger.warning("Failed to load animation from " + file.getName() + ": " + e.getMessage());
            }
        }
    }

    private void loadAnimationFromFile(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String animType = config.getString("type", "armorstand").toLowerCase();
        String worldName = config.getString("location.world");
        double x = config.getDouble("location.x");
        double y = config.getDouble("location.y");
        double z = config.getDouble("location.z");

        boolean loop = config.getBoolean("loop", true);
        long interval = config.getLong("interval", 10);

        List<?> rawFrames = config.getList("frames");
        if (rawFrames == null || rawFrames.isEmpty()) {
            logger.warning("No frames found in " + file.getName());
            return;
        }

        List<float[]> frames = new ArrayList<>();
        for (Object obj : rawFrames) {
            if (obj instanceof List<?>) {
                List<?> pair = (List<?>) obj;
                if (pair.size() == 2) {
                    float yaw = ((Number) pair.get(0)).floatValue();
                    float pitch = ((Number) pair.get(1)).floatValue();
                    frames.add(new float[]{yaw, pitch});
                }
            }
        }

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            logger.warning("World '" + worldName + "' not found for " + file.getName());
            return;
        }

        Location location = new Location(world, x, y, z);
        Rotatable rotatable;

        switch (animType) {
            case "head":
                rotatable = new RotatingHead(location);
                break;
            case "entity":
                rotatable = new RotatingEntity(world.spawn(location, Zombie.class));
                break;
            case "armorstand":
            default:
                rotatable = new RotatingArmorStand(location);
                break;
        }

        // Condiciones (opcional)
        AnimationCondition condition = null;
        ConfigurationSection condSection = config.getConfigurationSection("conditions");
        if (condSection != null) {
            String requiredWorld = condSection.getString("world", null);
            double minDistance = condSection.getDouble("minDistance", 0.0);
            double maxDistance = condSection.getDouble("maxDistance", 5.0);
            String startTime = condSection.getString("startTime", null);
            String endTime = condSection.getString("endTime", null);

            condition = new AnimationCondition(requiredWorld, minDistance, maxDistance, startTime, endTime);
        }

        AnimationExecutor executor = new AnimationExecutor(plugin, rotatable, frames, interval, loop, condition);

        // Acciones al hacer clic (opcional)
        ConfigurationSection clickSection = config.getConfigurationSection("click-actions");
        if (clickSection != null) {
            for (String key : clickSection.getKeys(false)) {
                ClickTrigger trigger;
                try {
                    trigger = ClickTrigger.valueOf(key.toUpperCase());
                } catch (IllegalArgumentException e) {
                    logger.warning("Invalid click trigger: " + key + " in " + file.getName());
                    continue;
                }

                List<String> actions = clickSection.getStringList(key);
                for (String rawAction : actions) {
                    String[] parts = rawAction.split(":", 2);
                    if (parts.length < 2) continue;

                    ClickAction.ActionType actionType;
                    try {
                        actionType = ClickAction.ActionType.valueOf(parts[0].trim().toUpperCase());
                    } catch (IllegalArgumentException e) {
                        logger.warning("Invalid action type: " + parts[0] + " in " + file.getName());
                        continue;
                    }

                    String value = parts[1].trim();
                    executor.addClickAction(trigger, new ActionEntry(actionType, value));
                }
            }
        }

        executor.start();
        logger.info("Loaded animation: " + file.getName() + " [" + animType + "] with " + frames.size() + " frames.");
    }
}
