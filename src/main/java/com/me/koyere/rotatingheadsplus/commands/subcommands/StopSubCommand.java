package com.me.koyere.rotatingheadsplus.commands.subcommands;

import com.me.koyere.rotatingheadsplus.RotatingHeadsPlus;
import com.me.koyere.rotatingheadsplus.commands.SubCommand;
import com.me.koyere.rotatingheadsplus.animation.Rotatable;
import com.me.koyere.rotatingheadsplus.animation.HeadAnimation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.Location;

/**
 * /rhead stop
 * Stops the animation of the nearest rotating object.
 */
public class StopSubCommand implements SubCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        // Get nearest animated object
        Rotatable nearest = RotatingHeadsPlus.getInstance()
                .getAnimationScheduler()
                .getNearestRotatable(player.getLocation(), RotatingHeadsPlus.getInstance().getConfigManager().getMaxRemovalDistance());

        if (nearest == null) {
            player.sendMessage(RotatingHeadsPlus.getInstance().getLangManager().get("command.remove.not_found"));
            return true;
        }

        // Remove animation from the object
        RotatingHeadsPlus.getInstance().getAnimationScheduler().removeAnimation(nearest);

        player.sendMessage(ChatColor.GREEN + "Rotation stopped for object at " +
                formatLoc(nearest.getLocation()));
        return true;
    }

    private String formatLoc(Location loc) {
        return ChatColor.YELLOW + "(" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ")";
    }
}
