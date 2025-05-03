package com.me.koyere.rotatingheadsplus.animation;

import org.bukkit.Location;
import java.util.UUID;

public interface Rotatable {
    UUID getUniqueId();
    float getCurrentYaw();
    void setCurrentYaw(float yaw);
    boolean isValid();
    void rotate();
    Location getLocation();
}
