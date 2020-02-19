package com.diamonddagger590.music.music;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Album{
  
  private List<Song> songs;
  private UUID composer;
  
  public Album(UUID uuid){
    this.composer = uuid;
    this.songs = new ArrayList<>();
  }
  
  public Album(UUID uuid, Song song){
    this.composer = uuid;
    this.songs = new ArrayList<>();
    this.songs.add(song);
  }
  
  public Album(UUID uuid, List<Song> songs){
    this.composer = uuid;
    this.songs = songs;
  }
  
  public List<Song> getSongs(){
    return this.songs;
  }
  
  public void addSong(Song song){
    this.songs.add(song);
  }
  
  public void removeSong(Song song){
    this.songs.remove(song);
  }
  
  public UUID getComposer(){
    return this.composer;
  }
}
