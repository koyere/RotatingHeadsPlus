package com.me.koyere.rotatingheadsplus.commands.subcommands;

import com.me.koyere.rotatingheadsplus.RotatingHeadsPlus;
import com.me.koyere.rotatingheadsplus.animation.AnimationScheduler;
import com.me.koyere.rotatingheadsplus.animation.Rotatable;
import com.me.koyere.rotatingheadsplus.commands.SubCommand;
import com.me.koyere.rotatingheadsplus.head.HeadManager;
import com.me.koyere.rotatingheadsplus.head.RotatingHead;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RemoveSubCommand implements SubCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(RotatingHeadsPlus.getInstance().getLangManager().get("command.only_players"));
            return true;
        }

        // Obtener gestores necesarios
        HeadManager headManager = RotatingHeadsPlus.getInstance().getHeadManager();
        AnimationScheduler scheduler = RotatingHeadsPlus.getInstance().getAnimationScheduler();

        // Obtener radio desde el config.yml
        double radius = RotatingHeadsPlus.getInstance().getConfigManager().getMaxRemovalDistance();

        // Buscar la cabeza más cercana
        Rotatable nearest = null;
        double closest = Double.MAX_VALUE;

        for (UUID id : scheduler.getAllAnimatedIds()) {
            Rotatable candidate = scheduler.getRotatableById(id);
            if (candidate == null || !candidate.isValid()) continue;

            double distance = candidate.getLocation().distance(player.getLocation());
            if (distance <= radius && distance < closest) {
                closest = distance;
                nearest = candidate;
            }
        }

        if (nearest == null) {
            player.sendMessage(RotatingHeadsPlus.getInstance().getLangManager().get("command.remove.not_found"));
            return true;
        }

        // Si es una cabeza registrada, eliminar también del gestor de cabezas
        if (nearest instanceof RotatingHead head) {
            headManager.unregisterHead(head);
        }

        scheduler.removeAnimation(nearest);
        player.sendMessage(RotatingHeadsPlus.getInstance().getLangManager().get("command.remove.success"));
        return true;
    }
}

