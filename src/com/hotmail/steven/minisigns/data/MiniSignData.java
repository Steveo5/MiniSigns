package com.hotmail.steven.minisigns.data;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.hotmail.steven.minisigns.MiniSign;
import com.hotmail.steven.minisigns.MiniSigns;
import com.hotmail.steven.minisigns.SignType;

public class MiniSignData {

	private static FileConfiguration cfg;
	private static File signFile;
	
	/**
	 * Initialize and load MiniSigns into memory
	 */
	public static void initialize(MiniSigns plugin)
	{
		// Get the sign file
		signFile = new File(plugin.getDataFolder() + File.separator + "signs.yml");
		// Create the file if needed
		if(!signFile.exists())
		{
			try {
				signFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		cfg = YamlConfiguration.loadConfiguration(signFile);
		// Create signs section
		if(!cfg.isConfigurationSection("signs"))
		{
			cfg.createSection("signs");
		}
		// Now load the signs into memory
		loadSigns();
	}
	
	/**
	 * Load the signs into memory. Must call
	 * initialize before this function is used
	 */
	public static void loadSigns()
	{
		int loaded = 0;
		int total = 0;
		// Loop over sign types
		for(String strSignType : cfg.getConfigurationSection("signs").getKeys(false))
		{
			SignType type = SignType.valueOf(strSignType.toUpperCase());
			for(String arenaName : cfg.getConfigurationSection("signs." + strSignType).getKeys(false))
			{
				// Get the serialized location
				String[] locData = cfg.getString("signs." + strSignType + "." + arenaName).split(",");
				// Get the world
				World w = Bukkit.getWorld(UUID.fromString(locData[0]));
				// Check if the world actually exists
				if(w != null)
				{
					try
					{
						int x = Integer.parseInt(locData[1]);
						int y = Integer.parseInt(locData[2]);
						int z = Integer.parseInt(locData[3]);
						
						MiniSign sign = new MiniSign(type, arenaName, new Location(w, x, y, z));
						// Register the sign so it starts recieving updates
						MiniSigns.getSignManager().registerSign(sign);
						loaded++;
					} catch(NumberFormatException e)
					{
						Bukkit.getLogger().log(Level.WARNING, "Failed to load sign for world " + w.getName() + " " + locData[1] + " " + locData[2] + " " + locData[3]);
					}
				}
				total++;
			}
		}
		
		MiniSigns.log(Level.INFO, loaded + " / " + total + " signs were loaded from config");
	}
	
	/**
	 * Save a sign to database
	 * @param sign
	 */
	public static void saveSign(MiniSign sign)
	{
		// Create the SignType section
		if(!cfg.isConfigurationSection("signs." + sign.getType().name().toLowerCase()))
		{
			cfg.createSection(sign.getType().name().toLowerCase());
		}
		Location l = sign.getLocation();
		cfg.set("signs." + sign.getType().name().toLowerCase() + "." + sign.getArenaName(), l.getWorld().getUID().toString() + "," + l.getBlockX()  + "," + l.getBlockY() + "," + l.getBlockZ());
		
		// Save file
		try {
			cfg.save(signFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
