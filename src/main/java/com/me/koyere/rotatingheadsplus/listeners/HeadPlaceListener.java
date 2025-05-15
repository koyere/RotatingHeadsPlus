package com.me.koyere.rotatingheadsplus.listeners;

import com.me.koyere.rotatingheadsplus.RotatingHeadsPlus;
import com.me.koyere.rotatingheadsplus.animation.CircularRotation;
import com.me.koyere.rotatingheadsplus.head.RotatingHead;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Listens for player head placement and registers it as a rotating head.
 */
public class HeadPlaceListener implements Listener {

    private final RotatingHeadsPlus plugin = RotatingHeadsPlus.getInstance();

    @EventHandler
    public void onHeadPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();

        // Only process player heads
        if (block.getType() != Material.PLAYER_HEAD) return;

        // Register the head as a RotatingHead
        RotatingHead head = new RotatingHead(block.getLocation());
        plugin.getHeadManager().registerHead(head);

        // Assign default animation
        float speed = plugin.getConfigManager().getDefaultSpeed();
        int interval = plugin.getConfigManager().getDefaultInterval();

        CircularRotation animation = new CircularRotation(0f, speed, true, interval);
        plugin.getAnimationScheduler().assignAnimation(head, animation);

        if (plugin.getConfigManager().isDebugEnabled()) {
            plugin.getLogger().info("New head placed and registered at: " + block.getLocation());
        }
    }
}
