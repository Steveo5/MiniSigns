package com.hotmail.steven.minisigns;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

import com.hotmail.steven.minisigns.util.StringUtil;

public class MiniSign {

	private SignType type;
	private Location location;
	private int playerCount, maxPlayerCount;
	private boolean arenaStarted = false;
	private String arenaName;
	
	/**
	 * 
	 * @param type the SignType
	 * @param arenaName applicable arena
	 * @param location of this sign
	 * @param maxPlayers allowed in the arena or -1 for no limit
	 */
	public MiniSign(SignType type, String arenaName, Location location)
	{
		this.type = type;
		this.location = location;
		this.arenaName = arenaName;
	}
	
	/**
	 * Gets the sign type
	 * @return
	 */
	public SignType getType()
	{
		return type;
	}
	
	/**
	 * Get the arena name for this sign
	 * @return
	 */
	public String getArenaName()
	{
		return arenaName;
	}
	
	/**
	 * Gets the sign location
	 * @return
	 */
	public Location getLocation()
	{
		return location;
	}
	
	/**
	 * Return the player count inside the arena
	 * @return
	 */
	public int getPlayerCount()
	{
		return playerCount;
	}
	
	/**
	 * Set the player count inside the arena
	 * @param count
	 */
	public void setPlayerCount(int count)
	{
		if(count != playerCount)
		{
			playerCount = count;
			update();
		}
	}
	
	/**
	 * Gets the maximum amount of players allowed through
	 * @return
	 */
	public int getMaxPlayers()
	{
		return maxPlayerCount;
	}
	
	/**
	 * Set the max player count
	 * @param max
	 */
	public void setMaxPlayers(int max)
	{
		if(maxPlayerCount != max)
		{
			maxPlayerCount = max;
			update();
		}
	}
	
	/**
	 * Gets whether the arena is started
	 * @return
	 */
	public boolean isStarted()
	{
		return arenaStarted;
	}
	
	/**
	 * Sets the arena as started
	 */
	public void start()
	{
		if(!arenaStarted)
		{
			arenaStarted = true;
			update();
		}
	}
	
	/**
	 * Sets the arena as stopped
	 */
	public void stop()
	{
		if(arenaStarted)
		{
			arenaStarted = false;
			update();
		}
	}
	
	/**
	 * Updates the physical sign in the world
	 */
	public void update()
	{
		if(location.getChunk().isLoaded())
		{
			System.out.println("check");
			// Get the block at the sign location
			BlockState block = location.getWorld().getBlockAt(location).getState();
			if(block instanceof Sign)
			{
				System.out.println("check2");
				Sign sign = (Sign)block;
				
				sign.setLine(0, StringUtil.colorize("&a&l[" + StringUtil.capitalize(type.name()) + "]"));
				sign.setLine(1, getArenaName());
				// Set the player counts
				if(getMaxPlayers() == -1 || getMaxPlayers() == 0)
				{
					sign.setLine(2, getPlayerCount() + " players");
				} else
				{
					sign.setLine(2, getPlayerCount() + " / " + getMaxPlayers() + " players");
				
				}
				// Set the started status
				if(isStarted())
				{
					sign.setLine(3, StringUtil.colorize("&5Game started"));
				} else
				{
					// Set player status
					if(getMaxPlayers() == -1 || getMaxPlayers() == 0 || getPlayerCount() < getMaxPlayers())
					{
						sign.setLine(3, StringUtil.colorize("&5In lobby"));
					} else
					{
						sign.setLine(3, StringUtil.colorize("&5Game full"));
					}
				}
				Bukkit.getScheduler().runTask(MiniSigns.instance(), sign::update);
			}
		}
	}
	
}
