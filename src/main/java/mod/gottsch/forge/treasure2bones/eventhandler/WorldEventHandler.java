/*
 * This file is part of  Treasure2.
 * Copyright (c) 2021, Mark Gottschling (gottsch)
 * 
 * All rights reserved.
 *
 * Treasure2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Treasure2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Treasure2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package mod.gottsch.forge.treasure2bones.eventhandler;

import com.someguyssoftware.gottschcore.world.WorldInfo;
import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.adornment.TreasureAdornmentRegistry;
import com.someguyssoftware.treasure2.capability.TreasureCapabilities;
import com.someguyssoftware.treasure2.config.TreasureConfig;
import com.someguyssoftware.treasure2.data.TreasureData;
import com.someguyssoftware.treasure2.loot.TreasureLootTableRegistry;
import com.someguyssoftware.treasure2.persistence.TreasureGenerationSavedData;
import com.someguyssoftware.treasure2.registry.TreasureMetaRegistry;
import com.someguyssoftware.treasure2.registry.TreasureTemplateRegistry;
import com.someguyssoftware.treasure2.rune.TreasureRunes;

import mod.gottsch.forge.treasure2bones.item.ModItems;
import mod.gottsch.forge.treasure2bones.rune.ModRunes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

/**
 * 
 * @author Mark Gottschling on Oct 2, 2022
 *
 */
@Mod.EventBusSubscriber(modid = Treasure.MODID, bus = EventBusSubscriber.Bus.FORGE)
public class WorldEventHandler {

	private boolean isLoaded = false;

	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onWorldLoad(WorldEvent.Load event) {
		Treasure.LOGGER.info("bones: In world load event");
		/*
		 * On load of dimension 0 (overworld), initialize the loot table's context and other static loot tables
		 */
		if (WorldInfo.isServerSide((World)event.getWorld())) {
			ServerWorld world = (ServerWorld) event.getWorld();
			ResourceLocation dimension = WorldInfo.getDimension(world);

			if (!isLoaded && TreasureConfig.GENERAL.dimensionsWhiteList.get().contains(dimension.toString())) {

				/*
				 *  register adornments into the TreasureAdornmentRegistry
				 *  NOTE
				 *  i don't like loading the data/registry here but for some reason, adornment items
				 *  did not have capabilities initiated in the FMLCommonSetupEvent (CommonSetup.init())
				 */
				ModItems.ADORNMENT_ITEMS.forEach((r, a) -> {
					ItemStack itemStack = new ItemStack(a.get());
					itemStack.getCapability(TreasureCapabilities.CHARMABLE).ifPresent(cap -> {
						TreasureAdornmentRegistry.register(cap.getBaseMaterial(), cap.getSourceItem(), a.get());
					});
				});
				
				TreasureRunes.register(ModRunes.RUNE_OF_BONES_AS_MANA, ModItems.BONES_MANA_RUNESTONE.get());

				isLoaded = true;
			}	
		}
	}
}
