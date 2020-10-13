package me.zh.gambit.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author ziheng
 * @since 2020.10.12
 */
public class PlayerUtil {
    /**
     * Determine whether the player has items
     *
     * @param player player
     * @param target item
     * @return yes/no
     */
    public static boolean hasItem(Player player, ItemStack target) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item.isSimilar(target)) {
                return true;
            }
        }
        return false;
    }

    /**
     * give player the item by drop it
     *
     * @param player
     * @param item
     */
    public static void dropItem(Player player, ItemStack item) {
        if (item == null) {
            return;
        }
        player.getWorld().dropItem(player.getLocation(), item);
    }

    /**
     * send item to player's inventory
     *
     * @param player
     * @param item
     */
    public static void addItem(Player player, ItemStack item) {
        if (isInvFull(player)) {
            dropItem(player, item);
        } else {
            player.getInventory().addItem(new ItemStack[] { item });
        }
    }

    /**
     * send amount of items to player's inventory
     *
     * @param player
     * @param item
     * @param amount
     */
    public static void addItem(Player player, ItemStack item, int amount) {
        item.setAmount(item.getAmount() + amount);
        if (isInvFull(player)) {
            dropItem(player, item);
        } else {
            player.getInventory().addItem(new ItemStack[] { item });
        }
    }

    /**
     * remove player's item in main hand
     *
     * @param player
     */
    public static void delItemInMainHand(Player player) {
        player.getInventory().setItemInMainHand(null);
    }

    /**
     * remove player's amount of item in main hand
     *
     * @param player
     * @param amount
     */
    public static void delItemInMainHand(Player player, int amount) {
        ItemStack item = player.getInventory().getItemInMainHand();
        item.setAmount(item.getAmount() - amount);
        player.getInventory().setItemInMainHand(item);
    }


    /**
     * Determine whether the inv is full or not
     *
     * @param player
     * @return boolean
     */
    public static boolean isInvFull(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item.isSimilar(new ItemStack(Material.AIR))) {
                return true;
            }
        }
        return false;
    }

    /**
     * execute cmd as an OP
     *
     * @param player
     * @param command
     * @param var
     * @param target
     */
    public static void performCmd(Player player, List<String> command, String var, String target) {
        player.setOp(true);
        for (String cmd : command) {
            player.performCommand(cmd.replace(var, target));
        }
        player.setOp(false);
    }
}