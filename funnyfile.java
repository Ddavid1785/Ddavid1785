package org.david.minecraftv1;

import jdk.javadoc.internal.doclint.HtmlTag;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.*;
//import static org.david.minecraftv1.Main.inv;

public class funnyfile implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.GRASS_BLOCK) {
            Location loc = e.getBlock().getLocation();

            loc.getWorld().spawnPart\icle(Particle.EXPLOSION_HUGE, loc, 1);

        }

    }

    @EventHandler
    public void FallLava(PlayerMoveEvent e) {

        if (e.getPlayer().getLocation().getBlock().getType() == Material.LAVA) {
            for (int i = 0; i < 10; i++) {
                Player player = e.getPlayer();
                player.getLocation().getWorld().spawn(e.getPlayer().getLocation(), Zombie.class);
            }
        }

    }

    @EventHandler
    public void ChickenSpawn(EntitySpawnEvent e) {
        if (e.getEntityType() == EntityType.CHICKEN) {
            Entity entity = e.getEntity();
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000, 100));

            }
        }

    }

    @EventHandler
    public void onBlockExplode(EntityExplodeEvent e) {

        List<Block> blocks = e.blockList();
        for (Block block : e.blockList()) {

            if (block.getType() == Material.STONE) {

                Location loc1 = block.getLocation();
                loc1.getWorld().spawnEntity(loc1, EntityType.ZOMBIE);
            }
        }


    }


    @EventHandler
    public void bowShot(ProjectileHitEvent e) {

        if (e.getHitBlock() != null) {
            Location loc3 = e.getHitBlock().getLocation();
            for (int i = 0; i < 5; i++) {
                Entity entity = e.getEntity();
                Arrow arrow = (Arrow) entity;
                if (arrow.getShooter() instanceof Monster) {
                    loc3.getWorld().spawn(loc3, Creeper.class);
                    loc3.getWorld().spawn(loc3, Skeleton.class);
                    loc3.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc3, 1);

                }
            }
        }
    }

    // @EventHandler
    //   public void getbowShot(EntityDamageByEntityEvent e) {

    //Location loc3 = e.getEntity().getLocation();
    //    Entity entity = e.getEntity();

    // if (e.getEntity()instanceof Player){

    // for (int i=0;i<5;i++) {
    //  loc3.getWorld().spawn(loc3, Creeper.class);
    // loc3.getWorld().spawn(loc3, Skeleton.class);
    // loc3.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc3, 1);
    //   }

    //}
//    }
    @EventHandler
    public void SheepSpawn(EntitySpawnEvent e) {
        if (e.getEntityType() == EntityType.SHEEP) {
            Entity entity = e.getEntity();
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 80, 1));
                Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
                    Location location = livingEntity.getLocation();
                    float yaw = location.getYaw() + 100;

                    yaw = yaw % 360;
                    if (yaw > 180) {
                        yaw -= 360;
                    } else if (yaw <= -180) {
                        yaw += 360;
                    }


                    location.setYaw(yaw);
                    livingEntity.teleport(location);
                }, 0L, 1L);


                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                    livingEntity.setHealth(0);
                    Location loc = e.getLocation();
                    Fireworkutils.spawnRainbowFireworks(loc);

                }, 80L);

            }
        }

    }

    @EventHandler
    public void Playershotbow(ProjectileHitEvent e) {
        Entity entity = e.getEntity();
        if (e.getHitBlock() != null) {

            if (entity instanceof Arrow) {
                Arrow arrow = (Arrow) entity;
                if (arrow.getShooter() instanceof Player) {
                    Location loc3 = e.getHitBlock().getLocation();
                    World world = loc3.getWorld();
                    TNTPrimed tnt = (TNTPrimed) world.spawn(loc3, TNTPrimed.class);
                    tnt.setFuseTicks(0);
                    arrow.remove();
                }
            }
        }
        Entity target = e.getHitEntity();

        Arrow arrow = (Arrow) e.getEntity();
        if (target instanceof LivingEntity) {

            if (arrow.getShooter() instanceof Player && (e.getHitEntity() instanceof LivingEntity)) {
                Location loc4 = e.getHitEntity().getLocation();
                World world1 = loc4.getWorld();
                TNTPrimed tnt = (TNTPrimed) world1.spawn(loc4, TNTPrimed.class);
                tnt.setFuseTicks(0);
                arrow.remove();

            }
        }

    }

    @EventHandler
    public void playerClickWithBow(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            Player player = e.getPlayer();
            ItemStack itemheld = player.getInventory().getItemInMainHand();
            String displayname = ItemgetCustomName.getItemDisplayName(itemheld);
            if ("BRRRRRRRRRRRRRRRRRRR".equals(displayname)) {
                Location loc4 = e.getPlayer().getLocation();
                Arrow arrow = player.launchProjectile(Arrow.class);

            }


        }
    }

    @EventHandler
    public void playerGetoutofBed(PlayerBedLeaveEvent e) {
        Location loc = e.getPlayer().getLocation();
        loc.getWorld().spawnEntity(loc, EntityType.WITHER);
    }


    @EventHandler
    public void PlayerPlaceBlock(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        ItemStack itemheld = player.getInventory().getItemInMainHand();
        String displayname = ItemgetCustomName.getItemDisplayName(itemheld);
        if ("Minerblock".equals(displayname)) {
            Location loc = e.getBlock().getLocation();
            int x = loc.getBlockX();
            int y = loc.getBlockY();
            int z = loc.getBlockZ();
            Block block = e.getBlock();
            Material blockType = block.getType();
            World world = e.getBlock().getWorld();
            for (int i = -1; i < 2; i++) {
                Block block1 = world.getBlockAt(x, y + i, z);
                Block block2 = world.getBlockAt(x - 1, y + i, z);
                Block block3 = world.getBlockAt(x + 1, y + i, z);
                if ((block1.getType().equals(Material.COAL_ORE)) ||
                        (block1.getType().equals(Material.COPPER_ORE)) ||
                        (block1.getType().equals(Material.LAPIS_ORE)) ||
                        (block1.getType().equals(Material.EMERALD_ORE))
                        || (block1.getType().equals(Material.DIAMOND_ORE))
                        || (block1.getType().equals(Material.ANCIENT_DEBRIS))
                        || (block1.getType().equals(Material.GOLD_ORE))
                        || (block1.getType().equals(Material.IRON_ORE)) ||
                        (block1.getType().equals(Material.REDSTONE_ORE))) {

                } else {
                    block1.setType(Material.AIR);
                }
                if ((block2.getType().equals(Material.COAL_ORE))
                        || (block2.getType().equals(Material.COPPER_ORE))
                        || (block2.getType().equals(Material.LAPIS_ORE))
                        || (block2.getType().equals(Material.EMERALD_ORE))
                        || (block2.getType().equals(Material.DIAMOND_ORE))
                        || (block2.getType().equals(Material.ANCIENT_DEBRIS))
                        || (block2.getType().equals(Material.GOLD_ORE))
                        || (block2.getType().equals(Material.IRON_ORE)) ||
                        (block2.getType().equals(Material.REDSTONE_ORE))) {

                } else {
                    block2.setType(Material.AIR);
                }

                if ((block3.getType().equals(Material.COAL_ORE)) ||
                        (block3.getType().equals(Material.COPPER_ORE)) ||
                        (block3.getType().equals(Material.LAPIS_ORE)) ||
                        (block3.getType().equals(Material.EMERALD_ORE)) ||
                        (block3.getType().equals(Material.DIAMOND_ORE)) ||
                        (block3.getType().equals(Material.ANCIENT_DEBRIS)) ||
                        (block3.getType().equals(Material.GOLD_ORE)) ||
                        (block3.getType().equals(Material.IRON_ORE)) ||
                        (block3.getType().equals(Material.REDSTONE_ORE))) {

                } else {
                    block3.setType(Material.AIR);
                }

                Block block4 = world.getBlockAt(x, y + i, z + 1);
                Block block5 = world.getBlockAt(x, y + i, z - 1);
                Block block6 = world.getBlockAt(x - 1, y + i, z - 1);
                Block block7 = world.getBlockAt(x + 1, y + i, z + 1);
                Block block8 = world.getBlockAt(x + 1, y + i, z - 1);
                Block block9 = world.getBlockAt(x + 1, y + i, z + 1);
                Block block10 = world.getBlockAt(x - 1, y + i, z + 1);
                if ((block4.getType().equals(Material.COAL_ORE)) ||
                        (block4.getType().equals(Material.COPPER_ORE)) ||
                        (block4.getType().equals(Material.LAPIS_ORE)) ||
                        (block4.getType().equals(Material.EMERALD_ORE)) ||
                        (block4.getType().equals(Material.DIAMOND_ORE)) ||
                        (block4.getType().equals(Material.ANCIENT_DEBRIS)) ||
                        (block4.getType().equals(Material.GOLD_ORE)) ||
                        (block4.getType().equals(Material.IRON_ORE)) ||
                        (block4.getType().equals(Material.REDSTONE_ORE))) {

                } else {
                    block4.setType(Material.AIR);
                }
                if ((block5.getType().equals(Material.COAL_ORE)) ||
                        (block5.getType().equals(Material.COPPER_ORE)) ||
                        (block5.getType().equals(Material.LAPIS_ORE)) ||
                        (block5.getType().equals(Material.EMERALD_ORE)) ||
                        (block5.getType().equals(Material.DIAMOND_ORE)) ||
                        (block5.getType().equals(Material.ANCIENT_DEBRIS)) ||
                        (block5.getType().equals(Material.GOLD_ORE)) ||
                        (block5.getType().equals(Material.IRON_ORE)) ||
                        (block5.getType().equals(Material.REDSTONE_ORE))) {

                } else {
                    block5.setType(Material.AIR);
                }
                if ((block6.getType().equals(Material.COAL_ORE)) ||
                        (block6.getType().equals(Material.COPPER_ORE)) ||
                        (block6.getType().equals(Material.LAPIS_ORE)) ||
                        (block6.getType().equals(Material.EMERALD_ORE)) ||
                        (block6.getType().equals(Material.DIAMOND_ORE)) ||
                        (block6.getType().equals(Material.ANCIENT_DEBRIS)) ||
                        (block6.getType().equals(Material.GOLD_ORE)) ||
                        (block6.getType().equals(Material.IRON_ORE)) ||
                        (block6.getType().equals(Material.REDSTONE_ORE))) {

                } else {
                    block6.setType(Material.AIR);
                }
                if ((block7.getType().equals(Material.COAL_ORE)) ||
                        (block7.getType().equals(Material.COPPER_ORE)) ||
                        (block7.getType().equals(Material.LAPIS_ORE)) ||
                        (block7.getType().equals(Material.EMERALD_ORE)) ||
                        (block7.getType().equals(Material.DIAMOND_ORE)) ||
                        (block7.getType().equals(Material.ANCIENT_DEBRIS)) ||
                        (block7.getType().equals(Material.GOLD_ORE)) ||
                        (block7.getType().equals(Material.IRON_ORE)) ||
                        (block7.getType().equals(Material.REDSTONE_ORE))) {

                } else {
                    block7.setType(Material.AIR);
                }
                if ((block8.getType().equals(Material.COAL_ORE)) ||
                        (block8.getType().equals(Material.COPPER_ORE)) ||
                        (block8.getType().equals(Material.LAPIS_ORE)) ||
                        (block8.getType().equals(Material.EMERALD_ORE)) ||
                        (block8.getType().equals(Material.DIAMOND_ORE)) ||
                        (block8.getType().equals(Material.ANCIENT_DEBRIS)) ||
                        (block8.getType().equals(Material.GOLD_ORE)) ||
                        (block8.getType().equals(Material.IRON_ORE)) ||
                        (block8.getType().equals(Material.REDSTONE_ORE))) {

                } else {
                    block8.setType(Material.AIR);
                }
                if ((block9.getType().equals(Material.COAL_ORE)) ||
                        (block9.getType().equals(Material.COPPER_ORE)) ||
                        (block9.getType().equals(Material.LAPIS_ORE)) ||
                        (block9.getType().equals(Material.EMERALD_ORE)) ||
                        (block9.getType().equals(Material.DIAMOND_ORE)) ||
                        (block9.getType().equals(Material.ANCIENT_DEBRIS)) ||
                        (block9.getType().equals(Material.GOLD_ORE)) ||
                        (block9.getType().equals(Material.IRON_ORE)) ||
                        (block9.getType().equals(Material.REDSTONE_ORE))) {

                } else {
                    block9.setType(Material.AIR);
                }

                if ((block10.getType().equals(Material.COAL_ORE)) ||
                        (block10.getType().equals(Material.COPPER_ORE)) ||
                        (block10.getType().equals(Material.LAPIS_ORE)) ||
                        (block10.getType().equals(Material.EMERALD_ORE)) ||
                        (block10.getType().equals(Material.DIAMOND_ORE)) ||
                        (block10.getType().equals(Material.ANCIENT_DEBRIS)) ||
                        (block10.getType().equals(Material.GOLD_ORE)) ||
                        (block10.getType().equals(Material.IRON_ORE)) ||
                        (block10.getType().equals(Material.REDSTONE_ORE))) {

                } else {
                    block10.setType(Material.AIR);
                }


            }
        }
    }

    @EventHandler
    public void PlayerPlaceTunel(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        ItemStack itemheld = player.getInventory().getItemInMainHand();
        String displayname = ItemgetCustomName.getItemDisplayName(itemheld);

        if ("Tuneler".equals(displayname)) {
            Location loc = e.getBlock().getLocation();
            World world = loc.getWorld();


            int dx = 1;
            int dy = 0;
            int dz = 0;


            destroy3x3Area(loc, world);


            for (int j = 0; j < 20; j++) {
                Location newLoc = loc.clone().add(dx + j, dy, dz);
                Block movedBlock = world.getBlockAt(newLoc);
                movedBlock.setType(e.getBlock().getType());
                destroy3x3Area(newLoc, world);
            }
        }
    }

    private void destroy3x3Area(Location loc, World world) {
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    Block block = world.getBlockAt(x + i, y + j, z + k);

                    block.setType(Material.AIR);
                }
            }
        }
    }

    @EventHandler
    public void SpawnEnderDragon(EntityDeathEvent e) {
        Location loc = e.getEntity().getLocation();
        Entity entity = e.getEntity();
        if (entity.getType().equals(EntityType.WITHER)) {
            loc.getWorld().spawnEntity(loc, EntityType.ENDER_DRAGON);

        }


    }

   // @EventHandler
  //  public void onClick(InventoryClickEvent e) {
      //  if (!e.getInventory().equals(inv))
         //   return;
      //  e.setCancelled(true);
      //  if (e.getCurrentItem() == null) return;
      //  if (e.getCurrentItem().getItemMeta() == null) return;


       // e.setCancelled(true);
      //  Player player = (Player) e.getWhoClicked();


      //  if (e.getSlot() == 0) {
//BLUE team

         //   Main.getInstance().setBlueCoords(player.getLocation());

         //   String worldType = e.getWhoClicked().getWorld().getName();
        //    Main.getInstance().setWorldName(worldType); // Set worldName in Main

          //  String ownerName = e.getWhoClicked().getName();
          //  Main.getInstance().setOwnerName(ownerName);
          //  player.sendMessage("You have set the blue teleporter!");


      //  }


    //}
}



























