package me.zh.gambit;

import lombok.Getter;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author ziheng
 * @since 2020.10.12
 */
public final class Gambit extends JavaPlugin {

    @Getter
    private static Gambit instance;

    @Override
    public void onEnable() {
        instance = this;

    }

    @Override
    public void onDisable() {
        
    }
}
