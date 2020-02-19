package com.diamonddagger590.music.players;

import com.diamonddagger590.music.Music;
import com.diamonddagger590.music.util.Methods;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerManager{
  
  private Map<UUID, MusicPlayer> musicPlayers;
  private Map<UUID, FileConfiguration> playerFiles;
  private File playerDataFolder;
  
  public PlayerManager(){
    this.musicPlayers = new HashMap<>();
    this.playerFiles = new HashMap<>();
    this.playerDataFolder = new File(Music.getInstance().getDataFolder(), File.separator + "player_data_storage");
    if(!playerDataFolder.exists()){
      playerDataFolder.mkdirs();
    }
  }
  
  public MusicPlayer getMusicPlayer(UUID uuid){
    return musicPlayers.get(uuid);
  }
  
  public boolean isPlayerLoaded(UUID uuid){
    return musicPlayers.containsKey(uuid);
  }
  
  public void addPlayer(UUID uuid){
    this.musicPlayers.put(uuid, new MusicPlayer(uuid));
    this.playerFiles.put(uuid, createPlayerFile(uuid));
  }
  
  public void removePlayer(UUID uuid){
    this.musicPlayers.remove(uuid);
    this.playerFiles.remove(uuid);
  }
  
  private FileConfiguration createPlayerFile(UUID uuid){
    File playerFile = new File(playerDataFolder, File.separator + uuid.toString() + ".yml");
    if(!playerFile.exists()){
      try{
        playerFile.createNewFile();
      }catch(IOException e){
        Bukkit.getLogger().log(Level.SEVERE, Methods.color("&cThere was a problem making a player data folder for player with UUID of " + uuid.toString()));
        return null;
      }
    }
    return YamlConfiguration.loadConfiguration(playerFile);
  }
  
  public FileConfiguration getPlayerFile(UUID uuid){
    return playerFiles.containsKey(uuid) ? playerFiles.get(uuid) : createPlayerFile(uuid);
  }
}
