package com.diamonddagger590.music.music;

import com.diamonddagger590.music.Music;
import com.diamonddagger590.music.util.Methods;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class SongManager{
  
  private static File songManagerFile;
  private static FileConfiguration songManagerConfiguration;
  
  private Map<String, Song> songMap;
  private Map<UUID, Album> creators;
  
  public SongManager(){
    if(songManagerFile == null){
      songManagerFile = new File(Music.getInstance().getDataFolder(), "song_manager_file.yml");
    }
    if(!songManagerFile.exists()){
      try{
        songManagerFile.createNewFile();
      }catch(IOException e){
        Bukkit.getLogger().log(Level.SEVERE, Methods.color("&cThere was an issue making the song manager file."));
      }
    }
    songManagerConfiguration = YamlConfiguration.loadConfiguration(songManagerFile);
    songMap = new HashMap<>();
    creators = new HashMap<>();
    if(songManagerConfiguration.contains("Composers")){
      for(String composerUUID : songManagerConfiguration.getConfigurationSection("Composers").getKeys(false)){
        UUID uuid = UUID.fromString(composerUUID);
        Album album = new Album(uuid);
        for(String song : songManagerConfiguration.getStringList("Composers." + uuid.toString() + ".Songs")){
          Song loadedSong = new Song(uuid, song);
          album.addSong(loadedSong);
          songMap.put(loadedSong.getSongName(), loadedSong);
        }
        creators.put(uuid, album);
      }
    }
  }
  
  public boolean isSongCreated(String songName){
    return songMap.containsKey(songName);
  }
  
  public Song getSong(String songName){
    return songMap.get(songName);
  }
  
  public void addSong(String songName, Song song){
    songMap.put(songName, song);
    UUID composer = song.getComposer();
    if(creators.containsKey(composer)){
      creators.get(composer).addSong(song);
    }
    else{
      Album album = new Album(composer, song);
      creators.put(composer, album);
    }
    saveComposers();
  }
  
  public void removeSong(String songName){
    if(songMap.containsKey(songName)){
      Song song = songMap.remove(songName);
      song.delete();
      creators.get(song.getComposer()).removeSong(song);
      if(creators.get(song.getComposer()).getSongs().size() == 0){
        creators.remove(song.getComposer());
      }
    }
    saveComposers();
  }
  
  public void saveComposers(){
    songManagerConfiguration.set("Composers", null);
    for(UUID uuid : creators.keySet()){
      List<String> songs = new ArrayList<>();
      for(Song song : creators.get(uuid).getSongs()){
        songs.add(song.getSongName());
      }
      songManagerConfiguration.set("Composers." + uuid.toString() + ".Songs", songs);
    }
    try{
      songManagerConfiguration.save(songManagerFile);
    }catch(IOException e){
      e.printStackTrace();
    }
  }
}
