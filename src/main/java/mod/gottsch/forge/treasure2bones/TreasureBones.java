/*
 * This file is part of  Treasure2: Bones.
 * Copyright (c) 2022 Mark Gottschling (gottsch)
 *
 * Treasure2: Bones is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Treasure2: Bones is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Treasure2: Bones.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package mod.gottsch.forge.treasure2bones;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.gottsch.forge.treasure2bones.charm.ModCharms;
import mod.gottsch.forge.treasure2bones.eventhandler.WorldEventHandler;
import mod.gottsch.forge.treasure2bones.item.ModItems;
import mod.gottsch.forge.treasure2bones.setup.CommonSetup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = TreasureBones.MODID)
public class TreasureBones {
	// constants
	public static final String MODID = "treasure2bones";
	protected static final String NAME = "Treasure2 Bones";

	// logger
	public static Logger LOGGER = LogManager.getLogger(TreasureBones.NAME);

	public static TreasureBones instance;

	/**
	 * 
	 */
	public TreasureBones() {
		TreasureBones.instance = this;
		ModCharms.init();
		// register the deferred registries
		//		ModBlocks.register();
		ModItems.register();

		// Register the setup method for modloading
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		eventBus.addListener(CommonSetup::init);
		
		MinecraftForge.EVENT_BUS.register(new WorldEventHandler());
	}
}
