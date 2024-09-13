package org.david.minecraftv2.FileSaver;

import org.bukkit.Material;

public enum Colors {
    RED, LIGHT_PURPLE, DARK_PURPLE, GOLD, YELLOW, GREEN, BLUE;

    public static Colors getColorFromWool(Material material) {
        switch(material) {
            case RED_WOOL:
            return Colors.RED;

            case BLUE_WOOL:
            return Colors.BLUE;

            case PINK_WOOL:
            return Colors.LIGHT_PURPLE;

            case ORANGE_WOOL:
            return Colors.GOLD;

            case YELLOW_WOOL:
            return Colors.YELLOW;

            case LIME_WOOL:
            return Colors.GREEN;

            case PURPLE_WOOL:
            return Colors.DARK_PURPLE;
        }

        return Colors.RED;
    }

    public static Material getWoolFromColor(Colors material) {
        switch(material) {
            case RED:
            return Material.RED_WOOL;

            case BLUE:
            return Material.BLUE_WOOL;

            case LIGHT_PURPLE:
            return Material.PINK_WOOL;

            case GOLD:
            return Material.ORANGE_WOOL;

            case YELLOW:
            return Material.YELLOW_WOOL;

            case GREEN:
            return Material.LIME_WOOL;

            case DARK_PURPLE:
            return Material.PURPLE_WOOL;
        }

        return Material.RED_WOOL;
    }
}
