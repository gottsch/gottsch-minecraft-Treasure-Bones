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
package mod.gottsch.forge.treasure2bones.setup;

import java.util.Arrays;

import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.adornment.TreasureAdornmentRegistry;
import com.someguyssoftware.treasure2.api.TreasureApi;
import com.someguyssoftware.treasure2.capability.TreasureCapabilities;
import com.someguyssoftware.treasure2.item.KeyItem;
import com.someguyssoftware.treasure2.item.TreasureItems;

import mod.gottsch.forge.treasure2bones.TreasureBones;
import mod.gottsch.forge.treasure2bones.charm.ModCharms;
import mod.gottsch.forge.treasure2bones.item.ModItems;
import mod.gottsch.forge.treasure2bones.material.ModCharmableMaterials;
import mod.gottsch.forge.treasure2bones.rune.ModRunes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * 
 * @author Mark Gottschling on Aug 17, 2022
 *
 */
@Mod.EventBusSubscriber(modid = TreasureBones.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetup {

	/**
	 * 
	 * @param event
	 */
    public static void init(FMLCommonSetupEvent event) {
		Treasure.LOGGER.debug("in CommonSetup.init()");
    	
    	// add to the keys by rarity map
		TreasureItems.keys.put(ModItems.BONE_KEY.get().getRarity(), ModItems.BONE_KEY.get());
		// set the accepted keys
		ModItems.BONE_LOCK.get().setKeys(Arrays.asList(new KeyItem[] { ModItems.BONE_KEY.get()}));
    	
		// register adornments into the TreasureAdornmentRegistry
//		Treasure.LOGGER.debug("adornment items size -> {}", ModItems.ADORNMENT_ITEMS.entrySet().size());
//		ModItems.ADORNMENT_ITEMS.forEach((r, a) -> {
//			Treasure.LOGGER.debug("attempting to register bones adornment -> {}", a.get().getRegistryName());
//			ItemStack itemStack = new ItemStack(a.get());
//			Treasure.LOGGER.debug("created item stack of -> {}, class -> {}", itemStack.getItem().getRegistryName(), itemStack.getItem().getClass().getSimpleName());
//			itemStack.getCapability(TreasureCapabilities.CHARMABLE).ifPresent(cap -> {
//				Treasure.LOGGER.debug("registering bones adornment -> {}", a.get().getRegistryName());
//				TreasureAdornmentRegistry.register(cap.getBaseMaterial(), cap.getSourceItem(), a.get());
//			});
//			if (!itemStack.getCapability(TreasureCapabilities.CHARMABLE).isPresent()) {
//				Treasure.LOGGER.debug("adornment doesn't have cap?!! -> {}", itemStack.getDisplayName().getString());
//			}
//		});
		
		ModCharmableMaterials.register();
		ModRunes.register();
		ModCharms.register();
		TreasureApi.registerLootTables(TreasureBones.MODID);
    }
}
