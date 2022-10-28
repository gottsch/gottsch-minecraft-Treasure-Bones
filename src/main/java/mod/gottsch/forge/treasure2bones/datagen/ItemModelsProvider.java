package mod.gottsch.forge.treasure2bones.datagen;

import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;

import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.adornment.TreasureAdornmentRegistry;
import com.someguyssoftware.treasure2.capability.modifier.NoLevelModifier;
import com.someguyssoftware.treasure2.enums.AdornmentType;
import com.someguyssoftware.treasure2.item.Adornment;
import com.someguyssoftware.treasure2.item.TreasureItems;
import com.someguyssoftware.treasure2.item.TreasureItems.AdornmentItemsBuilder;
import com.someguyssoftware.treasure2.material.TreasureCharmableMaterials;

import mod.gottsch.forge.treasure2bones.TreasureBones;
import mod.gottsch.forge.treasure2bones.item.ModItems;
import mod.gottsch.forge.treasure2bones.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * 
 * @author Mark Gottschling on Sep 29, 2022
 *
 */
public class ItemModelsProvider extends ItemModelProvider {
	
	List<String> names;
    public ItemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper, List<String> names) {
        super(generator, TreasureBones.MODID, existingFileHelper);
        this.names = names;
    }

    @Override
    protected void registerModels() {
        // keys
        singleTexture(ModItems.BONE_KEY.get().getRegistryName().getPath(),
        		mcLoc("item/generated"), "layer0", modLoc("item/keys/bone_key"));
        
        // gems
        singleTexture(ModItems.SKELETONS_HEART.get().getRegistryName().getPath(),
        		mcLoc("item/generated"), "layer0", modLoc("item/skeletons_heart"));
        
        // adornments
        singleTexture(ModItems.BONES_BANE.get().getRegistryName().getPath(),
        		mcLoc("item/generated"), "layer0", modLoc("item/adornments/bones_bane"));
        
        names.forEach(name -> {
	        	singleTexture(name,
	            		mcLoc("item/generated"), "layer0", modLoc("item/adornments/" + name));
        });
        
        // runes
//        singleTexture(ModItems.BONES_MANA_RUNESTONE.get().getRegistryName().getPath(),
//        		mcLoc("item/generated"), "layer0", modLoc("item/runestones/runestone3"));
     }
}
