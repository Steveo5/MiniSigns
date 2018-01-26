package com.hotmail.steven.minisigns;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.plugin.java.JavaPlugin;

import com.garbagemule.MobArena.MobArena;
import com.hotmail.steven.minisigns.data.MiniSignData;
import com.hotmail.steven.minisigns.listener.MiniSignClickListener;
import com.hotmail.steven.minisigns.listener.MiniSignCreateListener;

import me.poutineqc.cuberunner.CubeRunner;
import me.poutineqc.cuberunner.game.Arena;

public class MiniSigns extends JavaPlugin {

	private static MobArena maPlugin;
	private static MiniSignManager signManager;
	private static MiniSigns instance;
	private static CubeRunner cubeRunner;
	
	@Override
	public void onEnable()
	{
		instance = this;
		signManager = new MiniSignManager(this);
		maPlugin = (MobArena)this.getServer().getPluginManager().getPlugin("MobArena");
		if(maPlugin != null)
		{
			log(Level.INFO, "Found MobArena, loading signs");
		}
		
		cubeRunner = (CubeRunner)this.getServer().getPluginManager().getPlugin("CubeRunner");
		if(cubeRunner != null)
		{
			log(Level.INFO, "Found CubeRunner, loading signs");
		}
		
		this.saveDefaultConfig();
		
		this.getServer().getPluginManager().registerEvents(new MiniSignCreateListener(this), this);
		this.getServer().getPluginManager().registerEvents(new MiniSignClickListener(), this);
		
		/**
		 * Initialize and load our config data
		 */
		MiniSignData.initialize(this);
		/**
		 *  Start updating signs (other than normal updates received through plugin apis)
		 *  This method will keep signs updated for plugins that don't have api events for
		 *  all of the required updates.
		 *  
		 *  Only signs that have actually changes will recieve updates to reduce lag
		 */
		new SignUpdater().runTaskTimer(this, 20L * 5L, 20L * 1L);
	}
	
	public static MiniSigns instance()
	{
		return instance;
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
		case CUBERUN:
			if(cubeRunner != null) return true;
			break;
		}
		
		return false;
	}
	
	public static MobArena getMobArena()
	{
		return maPlugin;
	}
	
	public static CubeRunner getCubeRunner()
	{
		return cubeRunner;
	}
	
	public static MiniSignManager getSignManager()
	{
		return signManager;
	}
	
}
