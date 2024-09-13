package org.david.minecraftv1;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class Fireworkutils {







    public static void spawnRainbowFireworks(Location location) {
        Firework firework = location.getWorld().spawn(location, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();
        FireworkEffect effect = FireworkEffect.builder()
                .flicker(false)
                .trail(true)
                .with(FireworkEffect.Type.BALL_LARGE)
                .withColor(Color.RED)
                .withColor(Color.ORANGE)
                .withColor(Color.YELLOW)
                .withColor(Color.GREEN)
                .withColor(Color.BLUE)
                .withColor(Color.PURPLE)
                .build();
        meta.addEffect(effect);
        meta.setPower(1);
        firework.setFireworkMeta(meta);


        firework.detonate();
    }
}
