package org.david.minecraftv1;

import com.sun.tools.javac.jvm.Items;
import org.bukkit.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin implements CommandExecutor {

    private static Main instance;
   // public static Inventory inv;
   // private Location blueCoords;
   // private String worldType;
  //  private String ownerName;

  //  public Location getBlueCoords() {
  //      return blueCoords;
   // }

  //  public void setBlueCoords(Location coords) {
   //     this.blueCoords = coords;
   // }

  //  public String getWorldName() {
   //     return worldType;
   // }

  //  public void setWorldName(String world) {
  //      this.worldType = world;
  //  }

 //   public String getOwnerName() {
   //     return ownerName;
   // }

   // public void setOwnerName(String owner) {
   //     this.ownerName = owner;
   // }


    @Override
    public void onEnable() {

        instance = this;

        getServer().getPluginCommand("hello").setExecutor(this);
        getServer().getPluginCommand("particles").setExecutor(this);
        getServer().getPluginCommand("funnyshovel").setExecutor(this);
        getServer().getPluginCommand("mobs").setExecutor(this);
        getServer().getPluginCommand("speed").setExecutor(this);
        getServer().getPluginCommand("aeroturtle").setExecutor(this);
        getServer().getPluginCommand("funny").setExecutor(this);
        getServer().getPluginCommand("sword").setExecutor(this);
        getServer().getPluginCommand("funnysheep").setExecutor(this);
        getServer().getPluginCommand("villager").setExecutor(new yes());
        getServer().getPluginCommand("bow").setExecutor(this);
        getServer().getPluginCommand("minerblock").setExecutor(this);
        getServer().getPluginCommand("tuneler").setExecutor(this);
        getServer().getPluginCommand("testmenu").setExecutor(this);
        getServer().getPluginCommand("teleportationmenu").setExecutor(this);
        // getServer().getPluginCommand("funnyforest").setExecutor(this);
        // getServer().getPluginCommand("war").setExecutor(this);
        getServer().getPluginManager().registerEvents(new funnyfile(), this);

    }

    public static Main getInstance() {
        return instance;

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if (cmd.getLabel().equals("hello")) {
            player.setHealth(0);
        } else if (cmd.getLabel().equals("particles")) {
            int radius = 5;

            for (int i = 0; i < 360; i++) {
                double rad = Math.toRadians(i);
                double x = Math.cos(rad) * radius;
                double z = Math.sin(rad) * radius;

                Location loc = player.getLocation().clone().add(x, 0, z);

                loc.getWorld().spawnParticle(Particle.FLAME, loc, 1, 0, 0, 0, 0);
            }
        } else if (cmd.getLabel().equals("funnyshovel")) {
            ItemStack shovel = new ItemStack(Material.DIAMOND_SHOVEL, 5);
            ItemMeta meta = shovel.getItemMeta();
            meta.setDisplayName("§cFunny Shovel");
            meta.addEnchant(Enchantment.DIG_SPEED, 5, true);
            shovel.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 50);
            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

            String lore = "§7This is the legendary spider    killer funny shovel.";
            List<String> list = new ArrayList<String>();
            list.add(lore);
            meta.setLore(list);

            shovel.setItemMeta(meta);

            player.getInventory().addItem(shovel);
        } else if (cmd.getLabel().equals("mobs")) {
            Location loc = player.getLocation();
            for (int i = 0; i < 3; i++) {
                loc.getWorld().spawn(loc, Skeleton.class);
                loc.getWorld().spawn(loc, Zombie.class);
                loc.getWorld().spawn(loc, Ghast.class);
                loc.getWorld().spawn(loc, PiglinBrute.class);
                loc.getWorld().spawn(loc, Enderman.class);
            }

        } else if (cmd.getLabel().equals("speed")) {

            player.addPotionEffect(PotionEffectType.SPEED.createEffect(200, 100));
        } else if (cmd.getLabel().equals("aeroturtle")) {
            Location loc = player.getLocation();
            Turtle turtle = loc.getWorld().spawn(loc, Turtle.class);
            turtle.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 200, 1));
        } else if (cmd.getLabel().equals("funny")) {
            Location loc = player.getLocation();
            for (int i = 0; i < 100; i++) {
                loc.getWorld().spawn(loc, Chicken.class);
            }

        } else if (cmd.getLabel().equals("sword")) {
            ItemStack sword = new ItemStack(Material.NETHERITE_SWORD, 64);
            ItemMeta meta1 = sword.getItemMeta();
            meta1.setDisplayName("§7good sword");
            meta1.setUnbreakable(true);
            meta1.addEnchant(Enchantment.DAMAGE_ALL, 999, true);
            meta1.addEnchant(Enchantment.FIRE_ASPECT, 20, true);
            meta1.addEnchant(Enchantment.LUCK, 1000, true);
            String lore1 = "§7yes";
            List<String> list = new ArrayList<String>();
            list.add(lore1);
            meta1.setLore(list);
            sword.setItemMeta(meta1);

            player.getInventory().addItem(sword);

        } else if (cmd.getLabel().equals("funnysheep")) {
            Location loc = player.getLocation();
            for (int i = 0; i < 3; i++) {
                loc.getWorld().spawn(loc, Sheep.class);


            }
        } else if (cmd.getLabel().equals("bow")) {
            ItemStack bow = new ItemStack(Material.BOW, 1);
            ItemMeta meta1 = bow.getItemMeta();
            meta1.setDisplayName("BRRRRRRRRRRRRRRRRRRR");
            meta1.setUnbreakable(true);
            meta1.addEnchant(Enchantment.ARROW_FIRE, 1, true);
            meta1.addEnchant(Enchantment.ARROW_DAMAGE, 69, true);
            meta1.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
            String lore1 = "§7skibidi";
            List<String> list = new ArrayList<String>();
            list.add(lore1);
            meta1.setLore(list);
            bow.setItemMeta(meta1);
            player.getInventory().addItem(bow);

        }else if (cmd.getLabel().equals("minerblock")) {
            ItemStack minerblock = new ItemStack(Material.NETHERITE_BLOCK, 64);
            ItemMeta meta1 = minerblock.getItemMeta();
            meta1.setDisplayName("Minerblock");
            String lore1 = "§7skibidi§7";
            List<String> list = new ArrayList<String>();
            list.add(lore1);
            meta1.setLore(list);
            minerblock.setItemMeta(meta1);
            player.getInventory().addItem(minerblock);

        }else if (cmd.getLabel().equals("tuneler")) {
            ItemStack tuneler = new ItemStack(Material.DIAMOND_BLOCK, 64);
            ItemMeta meta1 = tuneler.getItemMeta();
            meta1.setDisplayName("Tuneler");
            String lore1 = "§7skibidi7";
            List<String> list = new ArrayList<String>();
            list.add(lore1);
            meta1.setLore(list);
            tuneler.setItemMeta(meta1);
            player.getInventory().addItem(tuneler);
        }//else if(cmd.getLabel().equals("teleportationmenu")){
            //createInv();
           // player.openInventory(inv);
           // return true;
        //}


//else if(cmd.getLabel().equals("funnyforest")){
        // @EventHandler




       // }
// /funnyforest i onda onak kad unistis logove dobijes 10% za emeraldde bla bla neke rude i kad ih unistis neki cool particlsi
// bomb (player) onak 50 stijeli iznad i okolo njih koje eksplodioraju




        //else if(cmd.getLabel().equals("war")){
            //float x = player.getLocation().getBlockX();
          //  float z = player.getLocation().getBlockZ();
           // float y =player.getLocation().getBlockY();
          //  Location loc2 = player.getLocation();

           // loc2.getWorld().spawnEntity((x+10,y,z+10,),EntityType.ZOMBIE);

       // }







        // par varijabli u koje imas random value i onda spawna random mobove protiv kojih se moras borit
        return true;
    }
    //public void createInv(){
       // inv = Bukkit.createInventory(null,9, ChatColor.RED+"TeleportationMenu");

       // ItemStack item = new ItemStack(Material.BLUE_WOOL);
       // ItemMeta meta = item.getItemMeta();

        //BLUE teleporter

       // meta.setDisplayName(ChatColor.BLUE+"Blue teleporter");
       // String lore = ChatColor.GREEN+"Cordinates:"+blueCoords+ChatColor.YELLOW+"\nWorld:"+worldType+ChatColor.AQUA+"\nOwner:"+ownerName;
      //  List<String> list = new ArrayList<String>();
      //  list.add(lore);
       // meta.setLore(list);
       // item.setItemMeta(meta);
//inv.setItem(0, item);

        //CLOSE button

     //   item.setType(Material.BEDROCK);
       // meta.setDisplayName(ChatColor.RED+""+ChatColor.BOLD+"Close Menu");
      //  list.clear();
        //meta.setLore(list);
       // item.setItemMeta(meta);
     //   inv.setItem(8, item);
   // }

    @Override
    public void onDisable() {

    }
}
