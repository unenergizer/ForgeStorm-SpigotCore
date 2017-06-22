package com.forgestorm.spigotcore.world.animate;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.TimesOfTheDay;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
@Setter
public class WorldTimer extends BukkitRunnable {

    private final SpigotCore plugin;
    private int day = 0;
    private long time = 0;
    private long lastTime = Long.MAX_VALUE;
    private TimesOfTheDay timesOfTheDay;

    public WorldTimer(final SpigotCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        time = Bukkit.getWorlds().get(0).getTime();
		
		//Check if something should animate.
		//If world is paused, only animate once.		
		if (time != lastTime) {


            // DAWN
            if (time == 22916) {
                timesOfTheDay = TimesOfTheDay.DAWN;
                triggerEvent(timesOfTheDay);
            }

            // AFTERNOON
            if (time == 0) {
                day++;
                System.out.println("Day: " + day);
                timesOfTheDay = TimesOfTheDay.AFTERNOON;
                triggerEvent(timesOfTheDay);
            }

            // DUSK
            if (time == 11616) {
                timesOfTheDay = TimesOfTheDay.DUSK;
                triggerEvent(timesOfTheDay);
            }

            // MIDNIGHT
            if (time == 18000) {
                timesOfTheDay = TimesOfTheDay.MIDNIGHT;
                triggerEvent(timesOfTheDay);
            }

            //Animate blocks
            plugin.getWorldAnimator().shouldAnimate(time);
            lastTime = time;
        }
    }

    /**
     * This will trigger a new TimeOfTHeDayEvent.
     *
     * @param timesOfTheDay
     */
    private void triggerEvent(TimesOfTheDay timesOfTheDay) {
        TimeOfDayEvent exampleEvent = new TimeOfDayEvent(timesOfTheDay);
        Bukkit.getPluginManager().callEvent(exampleEvent);
    }
}
