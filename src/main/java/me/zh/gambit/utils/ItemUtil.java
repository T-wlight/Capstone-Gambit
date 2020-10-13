package me.zh.gambit.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * @author ziheng
 * @since 2020.10.12
 */
public class ItemUtil {
    /**
     * build an item
     *
     * @param material material
     * @param durability child id
     * @param amount amount
     * @param display display name
     * @param lore lore
     * @param flags flags
     * @param unbreakable unbrokenable
     * @return ItemStack
     */
    public static ItemStack buildItem(String material, short durability, int amount, String display, List<String> lore, ItemFlag[] flags, boolean unbreakable) {
        ItemStack item = new ItemStack(Material.getMaterial(material));;
        item.setAmount(amount);
        item.setDurability(durability);
        ItemMeta meta = item.getItemMeta();
        if (display != null) {
            meta.setDisplayName(BasicUtils.convert(display));
        }
        if (lore != null) {
            meta.setLore(BasicUtils.convert(lore));
        }
    //    if (unbreakable) {
    //        NbtUtil.setUnbreakable(item, true);
    //    }
        meta.addItemFlags(flags);
        item.setItemMeta(meta);
        return item;
    }
}
