package com.forgestorm.spigotcore.entity;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.Messages;
import com.forgestorm.spigotcore.entity.mount.MountEntity;

public class MountTimer extends BukkitRunnable {
	
	private final SpigotCore PLUGIN;
	private final Integer MAX_TIME = 8;
	
	private final String NAME = Messages.MOUNT_SUMMONING_NAME.toString();
	private final String SUMMONING = Messages.MOUNT_SUMMONING.toString();
	private final String MOVED = Messages.MOUNT_MOVED.toString();
	private final String LIQUID = Messages.MOUNT_LIQUID.toString();
	private final String HAS_MOUNT = Messages.MOUNT_HAS_MOUNT.toString();
	
	private Player player;
	private MountEntity entity;
	private boolean isCanceled = false;
	private String cancelReason;
	private Location location;
	private double x;
	private double y;
	private double z;
	private int timeLeft = MAX_TIME;
	
	public MountTimer(SpigotCore plugin, Player player, MountEntity entity) {
		PLUGIN = plugin;
		this.player = player;
		this.entity = entity;
	}
	
	@Override
	public void run() {
		Location newLoc = player.getLocation();
		double newX = newLoc.getX();
		double newY = newLoc.getY();
		double newZ = newLoc.getZ();
		
		//Lets do some checks first.
		if (timeLeft == MAX_TIME) {
			alreadyHasMount();
			toggleMountSummon();
		}
		
		//Summoning was canceled.
		if (isCanceled) {
			cancel();
			
			//Show a canceled reason, if one exists.
			if (cancelReason != null) {
				player.sendMessage(cancelReason);
			} else {
				//Show particle effect for successful mount.
				player.spawnParticle(Particle.CRIT, location.add(0, 1.5, 0), 60);
				
				//Mount the player.
				PLUGIN.getMountManager().addPlayerMount(player, entity);
			}
		}
		
		//Test if player moved.
		if (x != newX || y != newY || z != newZ) {
			isCanceled = true;
			cancelReason = MOVED;
		}
		
		//Do the countdown.
		if (!isCanceled) {
			
			if (timeLeft == MAX_TIME) {
				player.sendMessage(NAME.replace("%f", entity.getName()).replace("%s", Integer.toString(timeLeft)));
			} else {
				player.sendMessage(SUMMONING.replace("%s", Integer.toString(timeLeft)));
			}
			
			player.spawnParticle(Particle.SPELL, location, 60);
			timeLeft--;
		}
		
		//Countdown is over!
		if (timeLeft <= 0) {
			isCanceled = true;
		}
	}
	
	private void alreadyHasMount() {
		boolean hasMount = PLUGIN.getMountManager().getPlayerMounts().containsKey(player);
		if (hasMount) {
			isCanceled = true;
			cancelReason = HAS_MOUNT;
		}
	}

	private void toggleMountSummon() {
		Block block = player.getLocation().getBlock();
		
		//Prevent mount from being spawned in lava or water.
		if (!block.isLiquid()) {
			location = player.getLocation();
			x = location.getX();
			y = location.getY();
			z = location.getZ();
		} else {
			isCanceled = true;
			cancelReason = LIQUID;
		}
	}
}
