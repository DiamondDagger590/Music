package com.diamonddagger590.music.players;

import com.diamonddagger590.music.music.PlayableSong;
import com.diamonddagger590.music.music.Song;

import java.util.UUID;

public class MusicPlayer{
  
  private Song currentSong;
  private PlayableSong currentPlayingSong;
  private UUID uuid;
  
  public MusicPlayer(UUID uuid){
    this.uuid = uuid;
  }
  
  public MusicPlayer(UUID uuid, Song currentSong){
    this.uuid = uuid;
    this.currentSong = currentSong;
  }
  
  public Song getCurrentSong(){
    return currentSong;
  }
  
  public void setCurrentSong(Song currentSong){
    this.currentSong = currentSong;
  }
  
  public UUID getUuid(){
    return uuid;
  }
  
  public void setUuid(UUID uuid){
    this.uuid = uuid;
  }
  
  public PlayableSong getCurrentPlayingSong(){
    return currentPlayingSong;
  }
  
  public void setCurrentPlayingSong(PlayableSong currentPlayingSong){
    this.currentPlayingSong = currentPlayingSong;
  }
}
