package com.forgestorm.spigotcore.world;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class will replace a block with another block.  After the time expires the block will switch back to its previous form.
 * This is great for block regeneration.  Professions, explosions, and more can be tracked here and regenerated after some time.
 */
public class BlockRegenerationManager extends BukkitRunnable {

    private final int blockRegenTime = 60 * 3; // Time till a block respawn's.
    private final Map<Integer, RegenerationInfo> regenInfo = new ConcurrentHashMap<>(); // <ID, RegenInfo>
    private int blockID = 0;
    private int blockIDsRemoved = 0;


    /**
     * This will onDisable the block regeneration and replace all blocks back to original state.
     */
    public void onDisable() {
        resetAllBlocks();
    }

    /**
     * Resets a block back to its original state.
     */
    @Override
    public void run() {
        // Lets loop through the hashMaps to find any blocks that need to be reverted.
        for (int i = blockIDsRemoved; i < blockID; i++) {

            //Make sure the map for i exists.
            if (regenInfo.containsKey(i)) {
                RegenerationInfo regenerationInfo = regenInfo.get(i);
                int timeLeft = regenerationInfo.getTimeLeft();

                if (timeLeft <= 0) {
                    // Set the block in the world.
                    Block block = regenerationInfo.getBlockLocation().getBlock();
                    block.setType(regenerationInfo.getBlockMaterial());
                    block.setData(regenerationInfo.getData());

                    // Remove entry
                    regenInfo.remove(i);
                    blockIDsRemoved++;
                } else {
                    // Adjust the time left.
                    regenerationInfo.setTimeLeft(timeLeft - 1);
                }
            }
        }
    }

    /**
     * Resets all blocks back to their original state.
     * This is used for server reloads.
     */
    private void resetAllBlocks() {
        for (int i = blockIDsRemoved; i < blockID; i++) {
            RegenerationInfo regenerationInfo = regenInfo.get(i);

            // Set the block in the world
            Block block = regenerationInfo.getBlockLocation().getBlock();
            block.setType(regenerationInfo.getBlockMaterial());
            block.setData(regenerationInfo.getData());

            // Remove this entry
            regenInfo.remove(i);

            blockIDsRemoved++;
        }
    }

    /**
     * This will set a temporary block in a broken blocks location.
     *
     * @param type      The type of block broken.
     * @param data      The original block data.
     * @param tempBlock The temporary block to replace the broken block.
     * @param location  The XYZ location in the world the block was broken.
     */
    public void setBlock(Material type, byte data, Material tempBlock, Location location) {
        setBlock(type, data, tempBlock, (byte) 0, location);
    }

    /**
     * This will set a temporary block in a broken blocks location.
     *
     * @param type      The type of block broken.
     * @param data      The original block data.
     * @param tempBlock The temporary block to replace the broken block.
     * @param tempData  The temporary block data.
     * @param location  The XYZ location in the world the block was broken.
     */
    public void setBlock(Material type, byte data, Material tempBlock, byte tempData, Location location) {
        Block block = location.getBlock();

        // Replace the broken block with a temporary block, until it has regenerated.
        block.setType(tempBlock);
        block.setData(tempData);

        // Save the block info for replacement later.
        regenInfo.put(blockID, new RegenerationInfo(type, data, location, blockRegenTime));

        // Increment the block counter (used to get the block's ID number.
        blockID++;
    }

    @Getter
    @AllArgsConstructor
    class RegenerationInfo {
        private final Material blockMaterial;
        private final byte data;
        private final Location blockLocation;

        @Setter
        private int timeLeft;
    }
}
