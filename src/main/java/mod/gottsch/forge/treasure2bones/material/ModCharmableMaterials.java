package mod.gottsch.forge.treasure2bones.material;

import java.util.Optional;

import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.capability.ICharmableCapability;
import com.someguyssoftware.treasure2.capability.TreasureCapabilities;
import com.someguyssoftware.treasure2.material.CharmableMaterial;
import com.someguyssoftware.treasure2.material.TreasureCharmableMaterials;

import mod.gottsch.forge.treasure2bones.item.ModItems;

/**
 * 
 * @author Mark Gottschling on Oct 1, 2022
 *
 */
public class ModCharmableMaterials {
	public static CharmableMaterial SKELETONS_HEART_MATERIAL;
	
	/**
	 * 
	 */
	public static void register() {
		SKELETONS_HEART_MATERIAL = new CharmableMaterial(3000, ModItems.SKELETONS_HEART.get().getRegistryName() , 14, 12);
		SKELETONS_HEART_MATERIAL.setCanAffix(stack -> {
			// test if the item stack has bone material
			Optional<ICharmableCapability> cap = stack.getCapability(TreasureCapabilities.CHARMABLE).resolve();
			if (cap.isPresent()) {
				if (cap.get().getBaseMaterial().equals(TreasureCharmableMaterials.BONE.getName())) {
					return true;
				}
			}
			return false;
		});
		TreasureCharmableMaterials.registerSourceItem(SKELETONS_HEART_MATERIAL.getName(), SKELETONS_HEART_MATERIAL);
	}
}
