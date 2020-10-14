package me.zh.gambit.event;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

/**
 * @author ziheng
 * @since 2020.10.14
 */
public class PlayerInvadeEvent extends Event implements Cancellable {
    
    @Getter private static final HandlerList HANDLERS_LIST = new HandlerList();

    @Getter private Player player;
    @Getter @Setter private boolean isCancelled;
    
    public PlayerInvadeEvent() {
        
    }
    
    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
    
}
