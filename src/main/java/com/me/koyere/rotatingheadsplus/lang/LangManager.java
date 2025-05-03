package com.me.koyere.rotatingheadsplus.lang;

import com.me.koyere.rotatingheadsplus.RotatingHeadsPlus;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LangManager {

    private final Map<String, String> messages = new HashMap<>();

    public void load(String langCode) {
        File folder = new File(RotatingHeadsPlus.getInstance().getDataFolder(), "lang");
        if (!folder.exists()) folder.mkdirs();

        File langFile = new File(folder, langCode + ".yml");

        // Si no existe, se copia desde resources/lang/langCode.yml
        if (!langFile.exists()) {
            RotatingHeadsPlus.getInstance().saveResource("lang/" + langCode + ".yml", false);
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(langFile);
        for (String key : config.getKeys(true)) {
            String value = config.getString(key);
            if (value != null) {
                messages.put(key, ChatColor.translateAlternateColorCodes('&', value));
            }
        }

        RotatingHeadsPlus.getInstance().getLogger().info("Loaded language: " + langCode);
    }

    public String get(String key) {
        return messages.getOrDefault(key, ChatColor.RED + "Missing lang: " + key);
    }
}

