package com.forgestorm.spigotcore.listeners;

import com.forgestorm.spigotcore.SpigotCore;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class EntityDamage implements Listener {
	
	private final SpigotCore plugin;
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();

		if (entity instanceof Player) {
            Player player = (Player) event.getEntity();

            switch(event.getCause()) {

                case CONTACT:
                    break;
                //case ENTITY_ATTACK:
                //    break;
                //case PROJECTILE:
                //   break;
                case SUFFOCATION:
                    break;
                case FALL:
                    break;
                case FIRE:
                    break;
                case FIRE_TICK:
                    break;
                case MELTING:
                    break;
                case LAVA:
                    break;
                case DROWNING:

                    break;
                case BLOCK_EXPLOSION:
                    break;
                case ENTITY_EXPLOSION:
                    break;

                /*--- VOID DAMAGE ---*/
                case VOID:
                    //Cancel annoying void damage.
                    event.setCancelled(true);

                        new BukkitRunnable() {
                            public void run() {

                                player.setFallDistance(0F);
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 1F, .5F);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1 * 20, 100));

                                //If the player is in the main world, tp them to spawn.
                                if (Bukkit.getWorlds().get(0).equals(player.getWorld())) {
                                    //Teleport the player.
                                    plugin.getTeleportSpawn().teleportSpawn(player);
                                } else {
                                    //If the player was in a realm, make them leave the realm.
                                    plugin.getRealmManager().leaveRealm(player);
                                }

                                cancel();
                            }
                        }.runTaskLater(plugin, 1L);
                    break;

                case LIGHTNING:
                    break;
                case SUICIDE:
                    break;
                case STARVATION:
                    break;
                case POISON:
                    break;
                case MAGIC:
                    break;
                case WITHER:
                    break;
                case FALLING_BLOCK:
                    break;
                case THORNS:
                    break;
                case DRAGON_BREATH:
                    break;
                case CUSTOM:
                    break;
                case FLY_INTO_WALL:
                    break;
                case HOT_FLOOR:
                    break;
                case CRAMMING:
                    break;
            }
        }

		//Mount Check
		if (entity instanceof Horse || entity instanceof Pig) {
			
			//This mount should be a player mount.
			if (entity.getPassenger() != null && entity.getPassenger() instanceof Player) {
				Player player = (Player) entity.getPassenger();
				plugin.getMountManager().removePlayerMount(player);
			}
		}
	}

	private void applyDmg(int dmg, EntityDamageEvent event, boolean cancel) {
        //Cancel regular mc damage.
        event.setDamage(1);
        event.setCancelled(cancel);
    }
}
