package com.me.koyere.rotatingheadsplus.commands.subcommands;

import com.me.koyere.rotatingheadsplus.RotatingHeadsPlus;
import com.me.koyere.rotatingheadsplus.animation.CircularRotation;
import com.me.koyere.rotatingheadsplus.armorstand.RotatingArmorStand;
import com.me.koyere.rotatingheadsplus.commands.SubCommand;
import com.me.koyere.rotatingheadsplus.entity.RotatingEntity;
import com.me.koyere.rotatingheadsplus.head.RotatingHead;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.util.BlockIterator;

public class CreateSubCommand implements SubCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(RotatingHeadsPlus.getInstance().getLangManager().get("command.only_players"));
            return true;
        }

        float speed = RotatingHeadsPlus.getInstance().getConfigManager().getDefaultSpeed();
        int interval = RotatingHeadsPlus.getInstance().getConfigManager().getDefaultInterval();
        CircularRotation animation = new CircularRotation(0f, speed, true, interval);

        // Primero: verificar si el jugador mira un bloque de cabeza
        Block targetBlock = player.getTargetBlockExact(5);
        if (targetBlock != null && targetBlock.getType() == Material.PLAYER_HEAD) {
            RotatingHead head = new RotatingHead(targetBlock.getLocation());
            RotatingHeadsPlus.getInstance().getHeadManager().registerHead(head);
            RotatingHeadsPlus.getInstance().getAnimationScheduler().assignAnimation(head, animation);
            player.sendMessage(RotatingHeadsPlus.getInstance().getLangManager().get("command.create.success"));
            return true;
        }

        // Segundo: verificar si el jugador está mirando una entidad
        Entity targetEntity = getTargetEntity(player, 5);
        if (targetEntity != null) {
            if (targetEntity instanceof ArmorStand armorStand) {
                RotatingArmorStand rotating = new RotatingArmorStand(armorStand);
                RotatingHeadsPlus.getInstance().getAnimationScheduler().assignAnimation(rotating, animation);
                player.sendMessage(RotatingHeadsPlus.getInstance().getLangManager().get("command.create.success_armorstand"));
                return true;
            } else {
                RotatingEntity rotating = new RotatingEntity(targetEntity);
                RotatingHeadsPlus.getInstance().getAnimationScheduler().assignAnimation(rotating, animation);
                player.sendMessage(RotatingHeadsPlus.getInstance().getLangManager().get("command.create.success_entity"));
                return true;
            }
        }

        // Si no encontró nada
        player.sendMessage(RotatingHeadsPlus.getInstance().getLangManager().get("command.create.invalid_target"));
        return true;
    }

    /**
     * Obtiene la entidad más cercana que el jugador está mirando en línea recta.
     */
    private Entity getTargetEntity(Player player, int range) {
        BlockIterator iterator = new BlockIterator(player, range);
        while (iterator.hasNext()) {
            Block block = iterator.next();
            for (Entity entity : player.getWorld().getNearbyEntities(block.getLocation(), 0.5, 0.5, 0.5)) {
                if (entity instanceof LivingEntity || entity instanceof ArmorStand) {
                    return entity;
                }
            }
        }
        return null;
    }
}

