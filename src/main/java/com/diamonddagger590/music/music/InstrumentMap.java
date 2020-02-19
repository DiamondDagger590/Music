package com.diamonddagger590.music.music;

import org.bukkit.Instrument;

import java.util.HashMap;
import java.util.Map;

public class InstrumentMap{
  
  private static Map<String, Instrument> instrumentMap;
  
  public InstrumentMap(){
    setup();
  }
  
  private void setup(){
    instrumentMap = new HashMap<>();
    instrumentMap.put("piano", Instrument.PIANO);
    instrumentMap.put("bass_drum", Instrument.BASS_DRUM);
    instrumentMap.put("snare_drum", Instrument.SNARE_DRUM);
    instrumentMap.put("sticks", Instrument.STICKS);
    instrumentMap.put("bass_guitar", Instrument.BASS_GUITAR);
    instrumentMap.put("flute", Instrument.FLUTE);
    instrumentMap.put("bell", Instrument.BELL);
    instrumentMap.put("guitar", Instrument.GUITAR);
    instrumentMap.put("chime", Instrument.CHIME);
    instrumentMap.put("xylophone", Instrument.XYLOPHONE);
    instrumentMap.put("iron_xylophone", Instrument.IRON_XYLOPHONE);
    instrumentMap.put("cow_bell", Instrument.COW_BELL);
    instrumentMap.put("didgeridoo", Instrument.DIDGERIDOO);
    instrumentMap.put("bit", Instrument.BIT);
    instrumentMap.put("banjo", Instrument.BANJO);
    instrumentMap.put("pling", Instrument.PLING);
  }
  
  public static boolean isInstrument(String instrument){
    return instrumentMap.containsKey(instrument.toLowerCase());
  }
  
  public static Instrument getInstrument(String insturment){
    return instrumentMap.get(insturment.toLowerCase());
  }
}
