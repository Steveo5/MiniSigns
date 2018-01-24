package com.hotmail.steven.minisigns;

import java.util.logging.Level;

import org.bukkit.block.Sign;
import org.bukkit.plugin.java.JavaPlugin;

import com.garbagemule.MobArena.MobArena;

public class MiniSigns extends JavaPlugin {

	private static MobArena maPlugin;
	private static MiniSignManager signManager;
	
	@Override
	public void onEnable()
	{
		signManager = new MiniSignManager(this);
		maPlugin = (MobArena)this.getServer().getPluginManager().getPlugin("MobArena");
		if(maPlugin != null)
		{
			log(Level.INFO, "Found MobArena, loading signs");
		}
		
		this.getServer().getPluginManager().registerEvents(new MiniSignCreateListener(this), this);
	}
	
	public static void log(Level level, String message)
	{
		System.out.println("[MiniSigns] " + message);
	}
	
	/**
	 * Check if a given signs plugin is enabled
	 * @param type
	 * @return
	 */
	public static boolean isSignEnabled(SignType type)
	{
		switch(type)
		{
		case MOBARENA:
			if(maPlugin != null) return true;
			break;
		}
		
		return false;
	}
	
	public static MobArena getMobArena()
	{
		return maPlugin;
	}
	
	public static MiniSignManager getSignManager()
	{
		return signManager;
	}
	
}
