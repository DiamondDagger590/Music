package com.diamonddagger590.music.events;

import com.diamonddagger590.music.Music;
import com.diamonddagger590.music.players.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerLoginEvent implements Listener{
  
  @EventHandler
  public void login(PlayerJoinEvent e){
    PlayerManager playerManager = Music.getInstance().getPlayerManager();
    if(playerManager.isPlayerLoaded(e.getPlayer().getUniqueId())){
      if(PlayerLogEvent.dataClear.containsKey(e.getPlayer().getUniqueId())){
        PlayerLogEvent.dataClear.remove(e.getPlayer().getUniqueId()).cancel();
      }
      return;
    }
    else{
      playerManager.addPlayer(e.getPlayer().getUniqueId());
    }
  }
}
