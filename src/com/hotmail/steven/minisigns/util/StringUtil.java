package com.hotmail.steven.minisigns.util;

import org.bukkit.ChatColor;

public class StringUtil {

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String capitalize(String str)
	{
		return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
	}
	
	public static String colorize(String str)
	{
		return ChatColor.translateAlternateColorCodes('&', str);
	}
	
}
