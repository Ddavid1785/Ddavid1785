package org.david.minecraftv2;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Beacon_get implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        if(cmd.getLabel().equals("beacon")) {
            ItemStack beacon = new ItemStack(Material.BEACON);
            ItemMeta meta = beacon.getItemMeta();
            meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Teleport Setter");
            beacon.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 1);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            String lore = ChatColor.RED + "" + ChatColor.BOLD + "Place this anywhere to open a teleportation menu.";
            List<String> list = new ArrayList<String>();
            list.add(lore);
            meta.setLore(list);
            beacon.setItemMeta(meta);
            Player player = (Player) sender;
            player.getInventory().addItem(beacon);
            
        }
        return false;
    }
}
