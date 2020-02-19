package com.diamonddagger590.music.commands;

import com.diamonddagger590.music.Music;
import com.diamonddagger590.music.music.MusicNote;
import com.diamonddagger590.music.music.PlayableSong;
import com.diamonddagger590.music.music.Song;
import com.diamonddagger590.music.music.SongManager;
import com.diamonddagger590.music.players.MusicPlayer;
import com.diamonddagger590.music.util.Methods;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MusicCommand implements CommandExecutor{
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
    
    if(sender instanceof Player){
      Player p = (Player) sender;
      MusicPlayer musicPlayer = Music.getInstance().getPlayerManager().getMusicPlayer(p.getUniqueId());
      if(args.length > 0){
        if(args[0].equalsIgnoreCase("finish")){
          if(musicPlayer.getCurrentSong() != null){
            SongManager songManager = Music.getInstance().getSongManager();
            songManager.addSong(musicPlayer.getCurrentSong().getSongName(), musicPlayer.getCurrentSong());
            musicPlayer.getCurrentSong().saveSong();
            musicPlayer.setCurrentSong(null);
            p.sendMessage(Methods.color(Music.getInstance().getPluginPrefix() + "&aYou have finished composing your song!"));
            return true;
          }
          else{
            p.sendMessage(Methods.color(Music.getInstance().getPluginPrefix() + "&cYou are not composing a song at this moment."));
            return true;
          }
        }
        else if(args[0].equalsIgnoreCase("stop")){
          if(musicPlayer.getCurrentPlayingSong() != null){
            musicPlayer.getCurrentPlayingSong().cancelSong();
            musicPlayer.setCurrentPlayingSong(null);
            p.sendMessage(Methods.color(Music.getInstance().getPluginPrefix() + "&aYou have stopped the playback"));
            return true;
          }
          else{
            p.sendMessage(Methods.color(Music.getInstance().getPluginPrefix() + "&cYou do not have a song currently playing."));
            return true;
          }
        }
      }
      if(args.length > 1){
        if(args[0].equalsIgnoreCase("compose")){
          if(musicPlayer.getCurrentSong() != null){
            p.sendMessage(Methods.color(Music.getInstance().getPluginPrefix() + "&cYou are already composing a song. Please do &7/music finish &cto end your current song"));
            return true;
          }
          else{
            Song newSong = new Song(p.getUniqueId(), args[1]);
            musicPlayer.setCurrentSong(newSong);
            p.sendMessage(Methods.color(Music.getInstance().getPluginPrefix() + "&aYou have begun composing song &7" + newSong.getSongName() + ". &aTo add a note, please do /music addnote %Sound% %Volume% %Pitch%. " +
                                          "If you would like to add a pause, do /music addnote null."));
            return true;
          }
        }
        else if(args[0].equalsIgnoreCase("play")){
          SongManager songManager = Music.getInstance().getSongManager();
          if(songManager.isSongCreated(args[1])){
            PlayableSong playableSong = new PlayableSong(songManager.getSong(args[1]).getNotes(), musicPlayer);
            musicPlayer.setCurrentPlayingSong(playableSong);
            p.sendMessage(Methods.color(Music.getInstance().getPluginPrefix() + "&aYou are now playing: " + args[1]));
            return true;
          }
          else{
            p.sendMessage(Methods.color(Music.getInstance().getPluginPrefix() + "&cThe song you have provided does not exist."));
            return true;
          }
        }
        else if(args[0].equalsIgnoreCase("delete")){
          SongManager songManager = Music.getInstance().getSongManager();
          if(songManager.isSongCreated(args[1])){
            Song song = songManager.getSong(args[1]);
            if(song.getComposer().equals(p.getUniqueId()) || p.hasPermission("music.*") || p.hasPermission("music.delete")){
              songManager.removeSong(song.getSongName());
            }
            else{
              p.sendMessage(Methods.color(Music.getInstance().getPluginPrefix() + "&cYou do not have permission to delete that song."));
            }
            return true;
          }
          else{
            p.sendMessage(Methods.color(Music.getInstance().getPluginPrefix() + "&cThe song you have provided does not exist."));
            return true;
          }
        }
      }
      if(args.length > 2){
        if(args[0].equalsIgnoreCase("addnote")){
          if(musicPlayer.getCurrentSong() == null){
            p.sendMessage(Methods.color(Music.getInstance().getPluginPrefix() + "&cYou are not composing a song at this moment."));
            return true;
          }
          if(args[1].equalsIgnoreCase("null")){
            musicPlayer.getCurrentSong().addNote(new MusicNote(null));
            p.sendMessage(Methods.color(Music.getInstance().getPluginPrefix() + "&aYou have added a note to the song."));
            return true;
          }
        }
      }
      if(args.length >= 4){
        //music addnote letter octave instrument sharpened
        if(args[0].equalsIgnoreCase("addnote")){
          if(musicPlayer.getCurrentSong() == null){
            p.sendMessage(Methods.color(Music.getInstance().getPluginPrefix() + "&cYou are not composing a song at this moment."));
            return true;
          }
          Sound sound = Sound.valueOf(args[1]);
          if(Methods.isInt(args[2])){
            if(Methods.isInt(args[3])){
              int volume = Integer.parseInt(args[2]);
              int pitch = Integer.parseInt(args[3]);
              musicPlayer.getCurrentSong().addNote(new MusicNote(sound, pitch, volume));
              p.sendMessage(Methods.color(Music.getInstance().getPluginPrefix() + "&aNote added."));
              return true;
            }
          }
          p.sendMessage(Methods.color(Music.getInstance().getPluginPrefix() + "&cYou had incorrect syntax while adding a note. Please do /music addnote note octave instrument sharp, " +
                                        "ex) &7/music addnote BLOCK_ANVIL_PLACE 1 1"));
          return true;
        }
      }
    }
    return false;
  }
}