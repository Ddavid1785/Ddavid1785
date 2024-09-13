package org.david.minecraftv1;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemgetCustomName{

    public static String getItemDisplayName(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return null;
        }

        ItemMeta meta = itemStack.getItemMeta();
        if (meta.hasDisplayName()) {
            return meta.getDisplayName();
        } else {
            return null;
        }
    }
}