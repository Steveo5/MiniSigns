package com.hotmail.steven.minisigns.listener;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.garbagemule.MobArena.framework.Arena;
import com.hotmail.steven.minisigns.MiniSign;
import com.hotmail.steven.minisigns.MiniSigns;
import com.hotmail.steven.minisigns.SignType;

import me.poutineqc.cuberunner.game.GameState;
import net.md_5.bungee.api.ChatColor;

public class MiniSignClickListener implements Listener {

	@EventHandler
	public void onMobarenaSignClick(PlayerInteractEvent evt)
	{
		// Check if the player is even clicking a sign
		if(evt.getAction() == Action.LEFT_CLICK_BLOCK || evt.getAction() == Action.RIGHT_CLICK_BLOCK
				&& isSign(evt.getClickedBlock()))
		{
			// Get the sign at the location
			MiniSign sign = MiniSigns.getSignManager().getSign(evt.getClickedBlock().getLocation());
			if(sign != null)
			{
				evt.setCancelled(true);
				try
				{
					joinArena(sign, evt.getPlayer());
				} catch(Exception e)
				{
					evt.getPlayer().sendMessage(e.getMessage());
				}
			}
		}
	}
	
	public void joinArena(MiniSign sign, Player player) throws Exception
	{
		switch(sign.getType())
		{
		case MOBARENA:
			Arena arena = MiniSigns.getMobArena().getArenaMaster().getArenaWithName(sign.getArenaName());
			// Check if the player can join the arena
			if(arena.canJoin(player))
			{
				arena.playerJoin(player, player.getLocation());
			} else
			{
				throw new Exception(ChatColor.RED + "You cannot join this arena");
			}
			break;
		case CUBERUN:
			me.poutineqc.cuberunner.game.Arena cubeArena = me.poutineqc.cuberunner.game.Arena.getArena(sign.getArenaName());
			if(cubeArena.getGameState() == GameState.READY)
			{
				cubeArena.addPlayer(player, true);
			} else
			{
				throw new Exception(ChatColor.RED + "You cannot join this arena");
			}
			break;
		}
	}
	
	/**
	 * Check if a block is a sign
	 * @param b
	 * @return
	 */
	public boolean isSign(Block b)
	{
		return b.getState() instanceof Sign;
	}
	
}
