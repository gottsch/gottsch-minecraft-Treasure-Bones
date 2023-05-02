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

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.someguyssoftware.treasure2.adornment.TreasureAdornmentRegistry;
import com.someguyssoftware.treasure2.capability.modifier.GreatAdornmentLevelModifier;
import com.someguyssoftware.treasure2.capability.modifier.NoLevelModifier;
import com.someguyssoftware.treasure2.datagen.TreasureBlockTagsProvider;
import com.someguyssoftware.treasure2.datagen.TreasureItemTagsProvider;
import com.someguyssoftware.treasure2.enums.AdornmentType;
import com.someguyssoftware.treasure2.item.Adornment;
import com.someguyssoftware.treasure2.item.TreasureItems.AdornmentItemsBuilder;
import com.someguyssoftware.treasure2.material.TreasureCharmableMaterials;

import mod.gottsch.forge.treasure2bones.TreasureBones;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

/**
 * 
 * @author Mark Gottschling on Sep 29, 2022
 *
 */
@Mod.EventBusSubscriber(modid = TreasureBones.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

	static List<Pair<String, Supplier<Adornment>>> adornments;
	static List<String> names;

	static {
		AdornmentItemsBuilder builder = new AdornmentItemsBuilder(TreasureBones.MODID);
		adornments =  builder
				.types(AdornmentType.BRACELET, AdornmentType.NECKLACE, AdornmentType.RING)
				.sizes(TreasureAdornmentRegistry.STANDARD, TreasureAdornmentRegistry.GREAT)
				.materials(TreasureCharmableMaterials.BONE)
				.mapInnate(TreasureCharmableMaterials.BONE, 1)
				.mapLevelModifier(TreasureAdornmentRegistry.STANDARD, new NoLevelModifier())
				.mapLevelModifier(TreasureAdornmentRegistry.GREAT, new GreatAdornmentLevelModifier())
				.deferredBuild();
		adornments.addAll(builder.useSourceDefaults().sources(new ResourceLocation(TreasureBones.MODID, "skeletons_heart")).deferredBuild());
		names = adornments.stream().map(p -> p.getLeft()).collect(Collectors.toList());
	}

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		if (event.includeServer()) {
			//            generator.addProvider(new Recipes(generator));
			//            generator.addProvider(new LootTables(generator));
        	TreasureBonesBlockTagsProvider blockTags = new TreasureBonesBlockTagsProvider(generator, event.getExistingFileHelper());
            generator.addProvider(blockTags);
            generator.addProvider(new TreasureBonesItemTagsProvider(generator, blockTags, event.getExistingFileHelper()));
		}
		if (event.includeClient()) {
			//            generator.addProvider(new BlockStates(generator, event.getExistingFileHelper()));
			generator.addProvider(new ItemModelsProvider(generator, event.getExistingFileHelper(), names));
//			generator.addProvider(new LanguageGen(generator, "en_us"));
		}
	}
}