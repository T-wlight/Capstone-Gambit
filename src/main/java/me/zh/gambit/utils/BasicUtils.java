package me.zh.gambit.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ziheng
 * @since 2020.10.12
 */
public class BasicUtils {

    public static String convert(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> convert(List<String> messages) {
        List<String> msg = new ArrayList<>();
        for (String string : messages) {
            msg.add(convert(string));
        }
        return msg;
    }

    public static List<Player> getOnlinePlayers() {
        List <Player> players = new ArrayList<Player>();
        List<World> worlds = new ArrayList<>();
        worlds.addAll(Bukkit.getWorlds());
        for (int i = 0;i < worlds.size();i++) {
            if(worlds.get(i).getPlayers().isEmpty()) {
                continue;
            } else {
                players.addAll(worlds.get(i).getPlayers());
            }
        }
        return players;
    }

    public static boolean isNull(Object object) {
        if (object != null && object != "" && object != " ") { return  true; } return false;
    }


}
