package com.hotmail.steven.minisigns;

import org.bukkit.Location;

import com.hotmail.steven.minisigns.data.MiniSignData;

public class SignCreator {

	private SignType type;
	private int maxPlayers = -1, currentPlayers;
	private String name;
	private boolean started;
	
	public SignCreator(SignType signType, String name)
	{
		this.type = signType;
		this.name = name;
	}
	
	/**
	 * Set the max players that can join the arena
	 * @param maxPlayers
	 * @return
	 */
	public SignCreator maxPlayers(int maxPlayers)
	{
		this.maxPlayers = maxPlayers;
		return this;
	}
	
	/**
	 * Get the max players that can join an arena
	 * @return
	 */
	public int maxPlayers()
	{
		return maxPlayers;
	}
	
	/**
	 * Sets the current players in the arena
	 * @param currentPlayers
	 * @return
	 */
	public SignCreator currentPlayers(int currentPlayers)
	{
		this.currentPlayers = currentPlayers;
		return this;
	}
	
	/**
	 * Gets the current player count
	 * @return
	 */
	public int currentPlayers()
	{
		return currentPlayers;
	}
	
	/**
	 * Gets the sign type
	 * @return
	 */
	public SignType type()
	{
		return type;
	}
	
	/**
	 * Gets the arena name for this sign
	 * @return
	 */
	public String name()
	{
		return name;
	}
	
	/**
	 * Sets whether the game is started
	 * @param started
	 * @return
	 */
	public SignCreator started(boolean started)
	{
		this.started = started;
		return this;
	}
	
	/**
	 * Gets whether this arena is started
	 * @return
	 */
	public boolean started()
	{
		return started;
	}
	
	/**
	 * Creates the sign at the specified location
	 * @param location
	 * @return
	 */
	public MiniSign create(Location location)
	{
		MiniSign newSign = new MiniSign(type(), name(), location);
		newSign.setPlayerCount(currentPlayers());
		newSign.setMaxPlayers(maxPlayers());
		if(started()) newSign.start();
		// Register this sign to start being updated
		MiniSigns.getSignManager().registerSign(newSign);
		MiniSignData.saveSign(newSign);
		return newSign;
	}
}
