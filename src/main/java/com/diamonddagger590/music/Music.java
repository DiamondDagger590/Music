package com.diamonddagger590.music;

import com.diamonddagger590.music.commands.MusicCommand;
import com.diamonddagger590.music.events.PlayerLogEvent;
import com.diamonddagger590.music.events.PlayerLoginEvent;
import com.diamonddagger590.music.music.InstrumentMap;
import com.diamonddagger590.music.music.SongManager;
import com.diamonddagger590.music.music.ToneMap;
import com.diamonddagger590.music.players.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Music extends JavaPlugin{
  
  private static Music instance;
  
  private PlayerManager playerManager;
  private SongManager songManager;
  
  @Override
  public void onEnable(){
    instance = this;
    this.playerManager = new PlayerManager();
    this.songManager = new SongManager();
    getCommand("music").setExecutor(new MusicCommand());
    new ToneMap();
    new InstrumentMap();
    Bukkit.getPluginManager().registerEvents(new PlayerLogEvent(), this);
    Bukkit.getPluginManager().registerEvents(new PlayerLoginEvent(), this);
  }
  
  @Override
  public void onDisable(){
    // Plugin shutdown logic
  }
  
  public static Music getInstance(){
    return instance;
  }
  
  public PlayerManager getPlayerManager(){
    return this.playerManager;
  }
  
  public String getPluginPrefix(){
    return "&7[&1Music&7]";
  }
  
  public SongManager getSongManager(){
    return songManager;
  }
}
