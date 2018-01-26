package com.hotmail.steven.minisigns;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class MiniSignManager {

	private MiniSigns plugin;
	private List<MiniSign> signs;
	
	public MiniSignManager(MiniSigns plugin)
	{
		signs = new ArrayList<MiniSign>();
		this.plugin = plugin;
	}
	
	/**
	 * Register a minisign in the database
	 * @param sign
	 */
	public void registerSign(MiniSign sign)
	{
		signs.add(sign);
	}
	
	public List<MiniSign> getRegisteredSigns()
	{
		return signs;
	}
	
	/**
	 * Get a MiniSign for a specific SignType and arena name
	 * @param type
	 * @param arenaName
	 * @return
	 */
	public MiniSign getSign(SignType type, String arenaName)
	{
		for(MiniSign sign : signs)
		{
			if(sign.getType() == type && sign.getArenaName().equalsIgnoreCase(arenaName))
			{
				return sign;
			}
		}
		
		return null;
	}
	
	/**
	 * Get a sign at a specific location
	 * @param location
	 * @return null if not sign at the location
	 */
	public MiniSign getSign(Location location)
	{
		for(MiniSign sign : signs)
		{
			if(sign.getLocation().equals(location))
			{
				return sign;
			}
		}
		
		return null;

	}
	
}
