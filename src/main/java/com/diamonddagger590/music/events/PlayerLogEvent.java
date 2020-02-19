package com.diamonddagger590.music.events;

import com.diamonddagger590.music.Music;
import com.diamonddagger590.music.players.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerLogEvent implements Listener{
  
  public static Map<UUID, BukkitTask> dataClear = new HashMap<>();
  
  @EventHandler
  public void logOut(PlayerQuitEvent e){
    Player p = e.getPlayer();
    PlayerManager playerManager = Music.getInstance().getPlayerManager();
    BukkitTask task =  new BukkitRunnable(){
      @Override
      public void run(){
        if(!p.isOnline() && playerManager.isPlayerLoaded(p.getUniqueId())){
          playerManager.removePlayer(p.getUniqueId());
        }
        dataClear.remove(p.getUniqueId());
      }
    }.runTaskLater(Music.getInstance(), 5 * 1200);
    dataClear.put(p.getUniqueId(), task);
  }
}
