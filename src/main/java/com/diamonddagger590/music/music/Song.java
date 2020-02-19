package com.diamonddagger590.music.music;

import com.diamonddagger590.music.Music;
import com.diamonddagger590.music.util.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class Song{
  
  private static File songFolder;
  
  private List<MusicNote> musicNotes;
  private UUID composer;
  private String songName;
  private File songFile;
  private FileConfiguration songConfiguration;
  
  public Song(UUID composer, String songName){
    if(songFolder == null){
      songFolder = new File(Music.getInstance().getDataFolder(), File.separator + "song");
      if(!songFolder.exists()){
        songFolder.mkdirs();
      }
    }
    this.songFile = new File(songFolder, File.separator + songName + ".yml");
    boolean exists = this.songFile.exists();
    if(!exists){
      try{
        songFile.createNewFile();
      }catch(IOException e){
        Bukkit.getLogger().log(Level.SEVERE, Methods.color("&cThere was an error making a file for the song named " + songName));
      }
    }
    songConfiguration = YamlConfiguration.loadConfiguration(songFile);
    this.composer = composer;
    this.songName = songName;
    this.musicNotes = new ArrayList<>();
    if(exists){
      if(songConfiguration.contains("Notes")){
        for(String noteName : songConfiguration.getConfigurationSection("Notes").getKeys(false)){
          String key = "Notes." + noteName + ".";
          Sound sound = songConfiguration.getString(key + "Sound").equalsIgnoreCase("NA") ? null : Sound.valueOf(songConfiguration.getString(key + "Sound"));
          MusicNote musicNote;
          if(sound != null){
            int volume = songConfiguration.getInt(key + "Volume");
            int pitch = songConfiguration.getInt(key + "Pitch");
            musicNote = new MusicNote(sound, pitch, volume);
          }
          else{
             musicNote = new MusicNote(null);
          }
          this.musicNotes.add(musicNote);
        }
      }
      else{
        Bukkit.getLogger().log(Level.WARNING, Methods.color("&cThere is no notes saved for song " + songName));
      }
    }
  }
  public void delete(){
    songFile.delete();
  }
  
  public UUID getComposer(){
    return composer;
  }
  
  public String getSongName(){
    return songName;
  }
  
  public void addNote(MusicNote musicNote){
    musicNotes.add(musicNote);
  }
  
  public List<MusicNote> getNotes(){
    return musicNotes;
  }
  
  public void saveSong(){
    songConfiguration.set("Notes", null);
    songConfiguration.set("SongName", songName);
    songConfiguration.set("Composer", composer.toString());
    for(int i = 0; i < musicNotes.size(); i++){
      MusicNote musicNote = musicNotes.get(i);
      if(musicNote.getSound() == null){
        songConfiguration.set("Notes.Note" + i + ".Sound", "NA");
        continue;
      }
      else{
        songConfiguration.set("Notes.Note" + i + ".Sound", musicNote.getSound().toString());
      }
      songConfiguration.set("Notes.Note" + i + ".Volume", musicNote.getVolume());
      songConfiguration.set("Notes.Note" + i + ".Pitch", musicNote.getPitch());
    }
    try{
      songConfiguration.save(songFile);
    }catch(IOException e){
      e.printStackTrace();
    }
  }
}
