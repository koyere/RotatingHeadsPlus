package com.me.koyere.rotatingheadsplus.animation;

import com.me.koyere.rotatingheadsplus.rotating.Rotatable;
import com.me.koyere.rotatingheadsplus.rotating.RotatingArmorStand;
import com.me.koyere.rotatingheadsplus.rotating.RotatingEntity;
import com.me.koyere.rotatingheadsplus.rotating.RotatingHead;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Zombie;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Carga animaciones desde archivos YAML ubicados en la carpeta "animations".
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

        String type = config.getString("type", "armorstand").toLowerCase();
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

        switch (type) {
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

        AnimationExecutor executor = new AnimationExecutor(plugin, rotatable, frames, interval, loop);
        executor.start();

        logger.info("Loaded animation: " + file.getName() + " [" + type + "] with " + frames.size() + " frames.");
    }
}
