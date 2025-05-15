package com.me.koyere.rotatingheadsplus.placeholder;

import com.me.koyere.rotatingheadsplus.RotatingHeadsPlus;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * PlaceholderAPI expansion for RotatingHeadsPlus.
 * Supported placeholders:
 *   %rotatingheadsplus_count%     → Number of currently animated objects
 *   %rotatingheadsplus_enabled%   → Whether the plugin is active
 */
public class RotatingHeadsPlaceholder extends PlaceholderExpansion {

    private final RotatingHeadsPlus plugin;

    public RotatingHeadsPlaceholder(RotatingHeadsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "rotatingheadsplus";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Koyere";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true; // Keep registered across reloads
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String identifier) {

        switch (identifier.toLowerCase()) {
            case "count" -> {
                return String.valueOf(plugin.getAnimationScheduler().getAllAnimatedIds().size());
            }
            case "enabled" -> {
                return plugin.isEnabled() ? "true" : "false";
            }
        }

        return null;
    }
}
