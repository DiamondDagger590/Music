package com.diamonddagger590.music.music;

import org.bukkit.Sound;

public class MusicNote{

  private Sound sound;
  private int pitch;
  private int volume;
  
  public MusicNote(Sound sound, int pitch, int volume){
    this.sound = sound;
    this.pitch = pitch;
    this.volume = volume;
  }
  
  public MusicNote(Sound sound){
   this.sound = sound;
  }
  
  public Sound getSound(){
    return sound;
  }
  
  public void setSound(Sound sound){
    this.sound = sound;
  }
  
  public int getPitch(){
    return pitch;
  }
  
  public void setPitch(int pitch){
    this.pitch = pitch;
  }
  
  public int getVolume(){
    return volume;
  }
  
  public void setVolume(int volume){
    this.volume = volume;
  }
}
