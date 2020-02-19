package com.diamonddagger590.music.music;

import org.bukkit.Note;

import java.util.HashMap;
import java.util.Map;

public class ToneMap{
  
  private static Map<String, Note.Tone> toneMap = new HashMap<>();
  private static Map<Note.Tone, String> toneMapInverse = new HashMap<>();
  
  
  public ToneMap(){
    setup();
  }
  
  public void setup(){
    toneMap.put("A", Note.Tone.A);
    toneMap.put("B", Note.Tone.B);
    toneMap.put("C", Note.Tone.C);
    toneMap.put("D", Note.Tone.D);
    toneMap.put("E", Note.Tone.E);
    toneMap.put("F", Note.Tone.F);
    toneMap.put("G", Note.Tone.G);
    
    toneMapInverse.put(Note.Tone.A, "A");
    toneMapInverse.put(Note.Tone.B, "B");
    toneMapInverse.put(Note.Tone.C, "C");
    toneMapInverse.put(Note.Tone.D, "D");
    toneMapInverse.put(Note.Tone.E, "E");
    toneMapInverse.put(Note.Tone.F, "F");
    toneMapInverse.put(Note.Tone.G, "G");
  
  }
  
  public static boolean isTone(String tone){
    return toneMap.containsKey(tone.toUpperCase());
  }
  
  public static Note.Tone getTone(String tone){
    return toneMap.get(tone.toUpperCase());
  }
  
  public static String getToneLetter(Note.Tone tone){
    return toneMapInverse.get(tone);
  }
}
