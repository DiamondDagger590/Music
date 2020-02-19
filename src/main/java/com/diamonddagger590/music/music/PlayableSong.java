package com.diamonddagger590.music.music;

import com.diamonddagger590.music.Music;
import com.diamonddagger590.music.players.MusicPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayableSong{
  
  private BukkitTask songTask;
  private MusicPlayer player;
  private Location loc;
  
  public PlayableSong(List<MusicNote> notes, MusicPlayer target){
    player = target;
    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target.getUuid());
    if(!offlinePlayer.isOnline()){
      return;
    }
    Player p = (Player) offlinePlayer;
    AtomicInteger iterator = new AtomicInteger(0);
    player.setCurrentPlayingSong(this);
    loc = p.getLocation();
    songTask = new BukkitRunnable(){
      @Override
      public void run(){
        if(p.isOnline()){
          if(iterator.get() >= notes.size()){
            cancel();
            player.setCurrentPlayingSong(null);
            return;
          }
          else{
            MusicNote musicNote = notes.get(iterator.get());
            if(musicNote.getSound() != null){
              p.playSound(p.getLocation(), musicNote.getSound(), musicNote.getVolume(), musicNote.getPitch());
            }
            iterator.addAndGet(1);
          }
        }
        else{
          songTask = null;
          cancel();
          player.setCurrentPlayingSong(null);
          return;
        }
      }
    }.runTaskTimer(Music.getInstance(), 0, 20);
  }
  
  public void cancelSong(){
    songTask.cancel();
    songTask = null;
    player.setCurrentPlayingSong(null);
  }
}
