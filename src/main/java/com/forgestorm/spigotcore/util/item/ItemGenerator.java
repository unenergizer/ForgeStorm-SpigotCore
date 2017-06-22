package com.forgestorm.spigotcore.util.item;

import com.forgestorm.spigotcore.constants.ItemTypes;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;

public class ItemGenerator {

    public ItemStack generateItem(String itemConfigName, ItemTypes type) {
        return generateItem(itemConfigName, type, 1);
    }

    public ItemStack generateItem(String itemConfigName, ItemTypes type, int amount) {

        //System.out.println("-------------- "+ itemConfigName + ":" + type.toString() + " --------------");
        File file = new File(type.getFilePaths().toString());
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        final String path = "." + itemConfigName;

        String name = config.getString(path + ".name");
        String material = config.getString(path + ".material");
        boolean enchanted = config.getBoolean(path + ".enchant");
        int data = config.getInt(path + ".data");
        List<String> desc = config.getStringList(path + ".desc");
        Material mat = Material.valueOf(material);

        //System.out.println("NAME:" + path + "." + name);
        //System.out.println("MATE:" + path + "." + material);
        //System.out.println("ENCH:" + path + "." + enchanted);
        //System.out.println("DATA:" + path + "." + data);
        //System.out.println("DESC:" + path + "." + desc);

        return new ItemBuilder(mat, (short) data)
                .setRawTitle(name)
                .addFakeEnchantment(enchanted)
                .addLores(desc)
                .setAmount(amount)
                .build(true);
    }
}
