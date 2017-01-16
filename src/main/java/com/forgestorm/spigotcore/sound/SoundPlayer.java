package com.forgestorm.spigotcore.sound;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.constants.Sounds;
import com.xxmicloxx.NoteBlockAPI.NBSDecoder;
import com.xxmicloxx.NoteBlockAPI.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.Song;
import com.xxmicloxx.NoteBlockAPI.SongPlayer;

public class SoundPlayer {
	
	public void playLocalSound(Location location, int radius) {
		Song song = NBSDecoder.parse(new File(Sounds.SECRET.toString()));
		SongPlayer songPlayer = new RadioSongPlayer(song);
		songPlayer.setAutoDestroy(true);
		
		for(Player player : location.getWorld().getPlayers()) {
		    if(player.getLocation().distance(location) <= radius) {
		    	songPlayer.addPlayer(player);
		    }
		}
		
		songPlayer.setPlaying(true);
	}
}
