package com.hotmail.steven.minisigns.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

import com.garbagemule.MobArena.framework.Arena;
import com.hotmail.steven.minisigns.MiniSigns;
import com.hotmail.steven.minisigns.SignCreator;
import com.hotmail.steven.minisigns.SignType;
import com.hotmail.steven.minisigns.util.StringUtil;

import me.poutineqc.cuberunner.game.GameState;
import net.md_5.bungee.api.ChatColor;

public class MiniSignCreateListener implements Listener {

	private MiniSigns plugin;
	
	public MiniSignCreateListener(MiniSigns plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onSignCreate(SignChangeEvent evt)
	{
		// Get the actual text of the sign
		String firstLine = ChatColor.stripColor(evt.getLine(0)).trim();
		if(firstLine.length() < 5) return;
		String secondLine = evt.getLine(1);
		System.out.println(firstLine.charAt(firstLine.length() - 1));
		if(firstLine.charAt(0) == '[' && firstLine.charAt(firstLine.length() - 1) == ']')
		{
			// Remove the boxed characters
			firstLine = firstLine.replaceAll("\\[", "").replaceAll("\\]", "");
			try
			{
				// Get the arena type the user is making a sign for
				SignType type = SignType.valueOf(firstLine.toUpperCase());
				// Check if the signs library is loaded
				if(!MiniSigns.isSignEnabled(type) || secondLine.isEmpty())
				{
					evt.setLine(0, StringUtil.colorize("&c&l[Error]"));
					System.out.println("error1");
					return;
				}
				
				// Initialize and create our sign
				SignCreator creator = new SignCreator(type, secondLine);
				// Load the settings into the creator
				if(isArena(type, secondLine))
				{
					evt.getPlayer().sendMessage(StringUtil.colorize("&aArena sign created successfully!"));
					creator.create(evt.getBlock().getLocation()).update();
				} else
				{
					evt.setLine(0, StringUtil.colorize("&c&l[Error]"));
					System.out.println("error2");
				}

			} catch(Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * Initialize a creator with its settings
	 * @param creator
	 */
	public boolean isArena(SignType type, String arenaName)
	{
		switch(type)
		{
		case MOBARENA:
			// Check if the actual arena exists in mob arena
			Arena arena = MiniSigns.getMobArena().getArenaMaster().getArenaWithName(arenaName);
			if(arena == null)
			{
				//evt.setLine(0, StringUtil.colorize("&c&l[Error]"));
				return false;
			}
			break;
		case CUBERUN:
			// Get the cube arena
			me.poutineqc.cuberunner.game.Arena cubeArena = me.poutineqc.cuberunner.game.Arena.getArena(arenaName);
			if(cubeArena == null)
			{
				return false;
			}
			break;
		}
		
		return true;
	}
	
}
