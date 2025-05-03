package com.me.koyere.rotatingheadsplus.listeners;

import com.me.koyere.rotatingheadsplus.RotatingHeadsPlus;
import com.me.koyere.rotatingheadsplus.head.RotatingHead;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class HeadPlaceListener implements Listener {

    @EventHandler
    public void onHeadPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (block.getType() != Material.PLAYER_HEAD) return;

        // Verifica que sea una cabeza colocada por un jugador y válida
        BlockState state = block.getState();
        if (!(state instanceof Skull)) return;

        // Aquí puedes aplicar lógica adicional (ej: si el jugador tiene permiso)
        // if (!event.getPlayer().hasPermission("rotatingheadsplus.auto")) return;

        // Por defecto, no se registra automáticamente para evitar animaciones no deseadas
        // Pero puedes dejar el código comentado para activarlo más tarde:

        /*
        RotatingHead head = new RotatingHead(block.getLocation());
        RotatingHeadsPlus.getInstance().getHeadManager().registerHead(head);

        float speed = RotatingHeadsPlus.getInstance().getConfigManager().getDefaultSpeed();
        int interval = RotatingHeadsPlus.getInstance().getConfigManager().getDefaultInterval();

        CircularRotation animation = new CircularRotation(0f, speed, true, interval);
        RotatingHeadsPlus.getInstance().getAnimationScheduler().assignAnimation(head, animation);

        event.getPlayer().sendMessage("§aAnimated head created automatically.");
        */
    }
}
