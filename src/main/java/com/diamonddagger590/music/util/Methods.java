package com.diamonddagger590.music.util;

import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

public class Methods {
  
  private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();
  
  static {
    
    map.put(1000, "M");
    map.put(900, "CM");
    map.put(500, "D");
    map.put(400, "CD");
    map.put(100, "C");
    map.put(90, "XC");
    map.put(50, "L");
    map.put(40, "XL");
    map.put(10, "X");
    map.put(9, "IX");
    map.put(5, "V");
    map.put(4, "IV");
    map.put(1, "I");
    
  }
  
  public static String convertToNumeral(int number) {
    int l = map.floorKey(number);
    if(number == l) {
      return map.get(number);
    }
    return map.get(l) + convertToNumeral(number - l);
  }
  
  
  /**
   * @param numeral The numeral to convert
   * @return The integer representation of the numeral
   */
  public static int convertToNumber(String numeral) {
    switch(numeral) {
      case "I":
        return 1;
      case "II":
        return 2;
      case "III":
        return 3;
      case "IV":
        return 4;
      case "V":
        return 5;
      default:
        return 0;
    }
  }
  
  /**
   * @param s String to test
   * @return true if the string is an int or false if not
   */
  public static boolean isInt(String s) {
    try {
      Integer.parseInt(s);
    } catch(NumberFormatException nfe) {
      return false;
    }
    return true;
  }
  
  /**
   * @param s String to test
   * @return true if the string is a long or false if not
   */
  public static boolean isLong(String s) {
    try {
      Long.parseLong(s);
    } catch(NumberFormatException nfe) {
      return false;
    }
    return true;
  }
  
  /**
   * @param msg String to colour
   * @return The coloured string
   */
  public static String color(String msg) {
    return ChatColor.translateAlternateColorCodes('&', msg);
  }
  
  /**
   * @param minutes The number of minutes to convert
   * @return The ticks equal to minute amount
   */
  public static int convertMinToTicks(int minutes) {
    int ticks = minutes * 1200;
    return ticks;
  }
  
  /**
   * @param uuid UUID to test
   * @return true if the player has logged in before or false if they have not
   */
  public static boolean hasPlayerLoggedInBefore(UUID uuid) {
    OfflinePlayer targ = Bukkit.getOfflinePlayer(uuid);
    if(!(targ.isOnline() || targ.hasPlayedBefore())) {
      return false;
    }
    else {
      return true;
    }
  }
  
  @SuppressWarnings("deprecation")
  public static boolean hasPlayerLoggedInBefore(String playerName) {
    OfflinePlayer targ = Bukkit.getOfflinePlayer(playerName);
    if(!(targ.isOnline() || targ.hasPlayedBefore())) {
      return false;
    }
    else {
      return true;
    }
  }
  
  /**
   * @param lore The list of strings to colour
   * @return The list of coloured strings
   */
  public static List<String> colorLore(List<String> lore) {
    for(int i = 0; i < lore.size(); i++) {
      String s = lore.get(i);
      lore.set(i, Methods.color(s));
    }
    return lore;
  }
  
  /**
   * @return Current time in millis
   */
  public static long getCurrentTimeInMillis() {
    Calendar cal = Calendar.getInstance();
    return cal.getTimeInMillis();
  }
  
  public static Location lookAt(Location loc, Location lookat) {
    //Clone the loc to prevent applied changes to the input loc
    loc = loc.clone();
    
    // Values of change in distance (make it relative)
    double dx = lookat.getX() - loc.getX();
    double dy = lookat.getY() - loc.getY();
    double dz = lookat.getZ() - loc.getZ();
    
    // Set yaw
    if(dx != 0) {
      // Set yaw start value based on dx
      if(dx < 0) {
        loc.setYaw((float) (1.5 * Math.PI));
      }
      else {
        loc.setYaw((float) (0.5 * Math.PI));
      }
      loc.setYaw((float) loc.getYaw() - (float) Math.atan(dz / dx));
    }
    else if(dz < 0) {
      loc.setYaw((float) Math.PI);
    }
    
    // Get the distance from dx/dz
    double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));
    
    // Set pitch
    loc.setPitch((float) -Math.atan(dy / dxz));
    
    // Set values, convert to degrees (invert the yaw since Bukkit uses a different yaw dimension format)
    loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
    loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);
    
    return loc;
  }
  
  public static String locToString(Location loc) {
    return loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ() + ":" + loc.getWorld().getName();
  }
  
  public static String chunkToLoc(Chunk chunk){
    return chunk.getX() + ":" + chunk.getZ() + ":" + chunk.getWorld().getName();
  }
  
  public static Location stringToLoc(String loc) {
    String[] args = loc.split(":");
    World w = Bukkit.getWorld(args[3]);
    return new Location(w, Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
  }
  
  //Following three methods is from the following thread: https://www.spigotmc.org/threads/how-to-get-players-exp-points.239171/#post-2406336
  
  // Calculate amount of EXP needed to level up
  public static int getExpToLevelUp(int level){
    if(level <= 15){
      return 2*level+7;
    } else if(level <= 30){
      return 5*level-38;
    } else {
      return 9*level-158;
    }
  }
  
  // Calculate total experience up to a level
  public static int getExpAtLevel(int level){
    if(level <= 16){
      return (int) (Math.pow(level,2) + 6*level);
    } else if(level <= 31){
      return (int) (2.5*Math.pow(level,2) - 40.5*level + 360.0);
    } else {
      return (int) (4.5*Math.pow(level,2) - 162.5*level + 2220.0);
    }
  }
  
  // Calculate player's current EXP amount
  public static int getPlayerExp(Player player){
    int exp = 0;
    int level = player.getLevel();
    
    // Get the amount of XP in past levels
    exp += getExpAtLevel(level);
    
    // Get amount of XP towards next level
    exp += Math.round(getExpToLevelUp(level) * player.getExp());
    
    return exp;
  }
  
}
