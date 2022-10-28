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

import mod.gottsch.forge.treasure2bones.TreasureBones;
import mod.gottsch.forge.treasure2bones.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

/**
 * 
 * @author Mark Gottschling on Sep 29, 2022
 *
 */
public class LanguageGen extends LanguageProvider {

    public LanguageGen(DataGenerator gen, String locale) {
        super(gen, TreasureBones.MODID, locale);
    }

    @Override
    protected void addTranslations() {
//        add("itemGroup." + TreasureBones.MODID, "Treasure2: Bones");
//        
//        add(ModItems.BONE_KEY.get(), "Bone Key");    
//        add(ModItems.SKELETONS_HEART.get(), "Skeleton's Heart");
    	add(ModItems.BONES_BANE.get(), "Bone's Bane");
    }
}
