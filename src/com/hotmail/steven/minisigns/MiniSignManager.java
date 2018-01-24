package com.hotmail.steven.minisigns;

import java.util.ArrayList;
import java.util.List;

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
	
}
