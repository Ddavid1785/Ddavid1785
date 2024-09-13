package org.david.minecraftv2;


import org.bukkit.*;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.david.minecraftv2.FileSaver.Colors;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Beacon_placed implements Listener {

    public Inventory inv;
    private String worldType;
    private String playerName;

    private double x;
    private double y;
    private double z;

    @EventHandler
    public void beaconPlaced(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        ItemStack itemheld = player.getInventory().getItemInMainHand();
        String displayname = ItemgetCustomName.getItemDisplayName(itemheld);
        if (e.getBlockPlaced().getType().equals(Material.BEACON) && (ChatColor.RED + "" + ChatColor.BOLD + "Teleport Setter").equals(displayname)) {
            e.getBlockPlaced().setType(Material.AIR);

            this.x = e.getBlockPlaced().getX();
            this.y = e.getBlockPlaced().getY();
            this.z = e.getBlockPlaced().getZ();
            this.playerName = e.getPlayer().getName();
            this.worldType = e.getBlockPlaced().getWorld().getName();
            createInv(worldType, player, x, y, z);
            player.openInventory(inv);

            }
        }




    public void createInv(String worldType, Player player, Double x, Double y, Double z) {

        inv = Bukkit.createInventory(null, 9 * 3, ChatColor.RED + "" + ChatColor.BOLD + "Teleportation Menu");

        //  String beaconLocation1 =
        //  String lore0 = ChatColor.GREEN + "x: " + x;
        //  String lore1 = ChatColor.BLUE + "y:" + y;
        // String lore2 = ChatColor.RED + "z:" + z;
        //  String lore4  = ChatColor.YELLOW + "World: " + worldType;
        //    String lore5 = ChatColor.AQUA + "Owner: " + playerName;
        //  List<String> list = new ArrayList<String>();
        // list.add(lore0);
        //  list.add(lore1);
        // list.add(lore2);
        // list.add(lore4);
        // list.add(lore5);
        //    meta.setLore(list);

        ItemStack glassPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta glassMeta = glassPane.getItemMeta();
        glassMeta.setDisplayName(ChatColor.BLACK + "");
        glassPane.setItemMeta(glassMeta);

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, glassPane);
        }

        int counter = 10;
        for (Colors color : Colors.values()) {
            ArrayList<String> loreList = new ArrayList<String>();

            try {
                Location loc = Main.getFileManager().getLocation(player, color);
                loreList.add("§aX: " + loc.getX());
                loreList.add("§9Y: " + loc.getY());
                loreList.add("§cZ: " + loc.getZ());
                loreList.add("§eWorld Name: " + loc.getWorld().getName());
            } catch (Exception e) {

            }

            ItemStack item = new ItemStack(Colors.getWoolFromColor(color));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.valueOf(color.toString().toUpperCase()) + color.toString().substring(0, 1).toUpperCase() + color.toString().substring(1) + " Teleporter");
            meta.setLore(loreList);
            item.setItemMeta(meta);
            inv.setItem(counter, item);

            counter++;
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!e.getInventory().equals(inv)) return;
        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getItemMeta() == null) return;

        e.setCancelled(true);

        if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
            try {

                Player player = (Player) e.getWhoClicked();

                if (e.getCurrentItem().getType() != Material.GRAY_STAINED_GLASS_PANE) {

                    ItemStack clickedItem = e.getCurrentItem();
                    ItemMeta metabluewool = clickedItem.getItemMeta();
                    metabluewool.addEnchant(Enchantment.KNOCKBACK, 1, true);
                    metabluewool.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    String lore0 = ChatColor.GREEN + "X: " + x;
                    String lore1 = ChatColor.BLUE + "Y:" + y;
                    String lore2 = ChatColor.RED + "Z:" + z;
                    String lore4 = ChatColor.YELLOW + "World: " + worldType;
                    List<String> list = new ArrayList<String>();
                    list.add(lore0);
                    list.add(lore1);
                    list.add(lore2);
                    list.add(lore4);
                    metabluewool.setLore(list);
                    // metabluewool.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    clickedItem.setItemMeta(metabluewool);
                    list.clear();

                    Main.getFileManager().setLocation(player, new Location(Bukkit.getWorld(worldType), x, y, z), Colors.getColorFromWool(e.getCurrentItem().getType()));
                }
            } catch (Exception ex) {

            }
        } else if (e.getAction().equals(InventoryAction.PICKUP_HALF)) {
            if (!(e.getCurrentItem().getItemMeta().getLore().size() <= 1)) {
                Player player = (Player) e.getWhoClicked();

                // List<String> lore = e.getCurrentItem().getItemMeta().getLore();

                // // X: 1000
                // // [""] ["1000"]

                // double x = Double.valueOf(lore.get(0).split("X: ")[1]);
                // double y = Double.valueOf(lore.get(1).split("Y: ")[1]);
                // double z = Double.valueOf(lore.get(2).split("Z: ")[1]);
                // String world = lore.get(3).split("World: ")[1];

                Location loc2 = Main.getFileManager().getLocation(player, Colors.getColorFromWool(e.getCurrentItem().getType()));
                player.teleport(loc2);
                player.sendMessage(ChatColor.RED+"You have been teleported to "+e.getCurrentItem().getItemMeta().getDisplayName());
            }
        }
    }
}



