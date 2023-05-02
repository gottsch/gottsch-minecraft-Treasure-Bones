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
package mod.gottsch.forge.treasure2bones.datagen;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.someguyssoftware.treasure2.adornment.TreasureAdornmentRegistry;
import com.someguyssoftware.treasure2.enums.AdornmentType;
import com.someguyssoftware.treasure2.item.Adornment;
import com.someguyssoftware.treasure2.item.CharmItem;
import com.someguyssoftware.treasure2.item.TreasureItems;

import mod.gottsch.forge.treasure2bones.TreasureBones;
import mod.gottsch.forge.treasure2bones.item.ModItems;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * 
 * @author Mark Gottschling on May 2, 2023
 *
 */
public class TreasureBonesItemTagsProvider extends ItemTagsProvider {
	/**
	 * 
	 * @param dataGenerator
	 * @param blockTagProvider
	 * @param existingFileHelper
	 */
	public TreasureBonesItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, TreasureBones.MODID, existingFileHelper);
    }
	
    @Override
    protected void addTags() {           	
//        List<Adornment> adornments = TreasureAdornmentRegistry.getByType(AdornmentType.RING).stream()
//        		.filter(a -> a.getRegistryName().getNamespace().equals(TreasureBones.MODID))
//        		.collect(Collectors.toList());
    	List<Adornment> adornments = ModItems.ADORNMENT_ITEMS.values().stream()
    			.filter(a -> a.get().getType() == AdornmentType.RING).map(a -> a.get()).collect(Collectors.toList());
        adornments.forEach(ring -> {
        	tag(TreasureBonesTags.Items.RING).add(ring);
        });
        
//        adornments = TreasureAdornmentRegistry.getByType(AdornmentType.NECKLACE).stream()
//        		.filter(a -> a.getRegistryName().getNamespace().equals(TreasureBones.MODID))
//        		.collect(Collectors.toList());
        adornments = ModItems.ADORNMENT_ITEMS.values().stream()
    			.filter(a -> a.get().getType() == AdornmentType.NECKLACE).map(a -> a.get()).collect(Collectors.toList());
        adornments.forEach(necklace -> {
        	tag(TreasureBonesTags.Items.NECKLACE).add(necklace);
        });
        
//        adornments = TreasureAdornmentRegistry.getByType(AdornmentType.BRACELET).stream()
//        		.filter(a -> a.getRegistryName().getNamespace().equals(TreasureBones.MODID))
//        		.collect(Collectors.toList());
        adornments = ModItems.ADORNMENT_ITEMS.values().stream()
    			.filter(a -> a.get().getType() == AdornmentType.BRACELET).map(a -> a.get()).collect(Collectors.toList());
        adornments.forEach(bracelet -> {
        	tag(TreasureBonesTags.Items.BRACELET).add(bracelet);
        });
        
        // special adornments
        tag(TreasureBonesTags.Items.RING).add(ModItems.BONES_BANE.get());
    }
}
