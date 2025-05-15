package com.me.koyere.rotatingheadsplus.rotating;

import com.me.koyere.rotatingheadsplus.compat.CompatProvider;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;
import java.util.UUID;

/**
 * Clase que representa una cabeza rotatable (skull colocada en el mundo).
 */
public class RotatingHead implements Rotatable {

    private Location location;

    /**
     * Crea una cabeza rotativa en una ubicación del mundo.
     *
     * @param location Ubicación del bloque donde se colocará la cabeza.
     */
    public RotatingHead(Location location) {
        this.location = location;
        placeSkull(location);
    }

    /**
     * Coloca un bloque tipo skull en la ubicación dada.
     *
     * @param location Ubicación para colocar la cabeza.
     */
    private void placeSkull(Location location) {
        Block block = location.getBlock();
        block.setType(Material.PLAYER_HEAD, false);

        // Asegura que tenga orientación
        if (block.getBlockData() instanceof Directional) {
            Directional directional = (Directional) block.getBlockData();
            directional.setFacing(BlockFace.NORTH);
            block.setBlockData(directional, false);
        }
    }

    @Override
    public void rotate(float yaw, float pitch) {
        // Determinar la dirección cardinal más cercana según yaw
        BlockFace direction = getBlockFaceFromYaw(yaw);

        Block block = location.getBlock();
        if (block.getBlockData() instanceof Directional) {
            Directional directional = (Directional) block.getBlockData();
            directional.setFacing(direction);
            block.setBlockData(directional, false);
        }
    }

    @Override
    public void lookAt(Location target) {
        float yaw = (float) Math.toDegrees(Math.atan2(
                target.getX() - location.getX(),
                target.getZ() - location.getZ()
        ));
        rotate(yaw, 0f);
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
        placeSkull(location);
    }

    /**
     * Elimina la cabeza del mundo.
     */
    public void remove() {
        location.getBlock().setType(Material.AIR, false);
    }

    /**
     * Convierte un yaw a un BlockFace cardinal.
     *
     * @param yaw Ángulo horizontal en grados.
     * @return BlockFace más cercano.
     */
    private BlockFace getBlockFaceFromYaw(float yaw) {
        yaw = (yaw + 360) % 360;

        if (yaw >= 315 || yaw < 45) {
            return BlockFace.SOUTH;
        } else if (yaw < 135) {
            return BlockFace.WEST;
        } else if (yaw < 225) {
            return BlockFace.NORTH;
        } else {
            return BlockFace.EAST;
        }
    }
}
