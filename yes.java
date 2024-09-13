package org.david.minecraftv1;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftMerchantRecipe;
import org.bukkit.entity.Player;
import net.minecraft.world.level.Level;
import org.bukkit.inventory.ItemStack;

public class yes implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player)commandSender;

        Level world = ((CraftWorld)player.getWorld()).getHandle();
        Location location = player.getLocation();

        Villager villager = new Villager(EntityType.VILLAGER, world);
        villager.setPos(location.getX(), location.getY(), location.getZ());
        villager.setVariant(VillagerType.SNOW);

        VillagerData data = villager.getVillagerData();
        data.setProfession(VillagerProfession.WEAPONSMITH);

        villager.setVillagerData(data);

        MerchantOffers offers = new MerchantOffers();

        ItemStack costA = new ItemStack(Material.DIRT, 1);
        net.minecraft.world.item.ItemStack finalCostA = CraftItemStack.asNMSCopy(costA);

        ItemStack costB = new ItemStack(Material.STICK, 1);
        net.minecraft.world.item.ItemStack finalCostB = CraftItemStack.asNMSCopy(costB);

        ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD, 1);
        net.minecraft.world.item.ItemStack finalSword = CraftItemStack.asNMSCopy(diamondSword);

        CraftMerchantRecipe recipe = new CraftMerchantRecipe(diamondSword, 1, 5, true, 1000, 1, 0, 0);
        MerchantOffer sword = new MerchantOffer(finalCostA, finalCostB, finalSword, 1, 5, 1000, 1f, recipe);

        offers.add(sword);
        villager.setOffers(offers);

        villager.setVillagerXp(1);

        world.addFreshEntity(villager);

        return true;
    }
}
