package mod.gottsch.forge.treasure2bones.rune;

import com.someguyssoftware.treasure2.enums.Rarity;
import com.someguyssoftware.treasure2.rune.IRune;
import com.someguyssoftware.treasure2.rune.TreasureRunes;

import mod.gottsch.forge.treasure2bones.TreasureBones;
import net.minecraft.util.ResourceLocation;

/**
 * 
 * @author Mark Gottschling on Oct 3, 2022
 *
 */
public class ModRunes {
	public static IRune RUNE_OF_BONES_AS_MANA;
//	public static IRune RUNE_OF_BONE_BUSTER;

	static {
		RUNE_OF_BONES_AS_MANA = new BonesManaRune.Builder(new ResourceLocation(TreasureBones.MODID, "bones_mana_rune"))
				.with($ -> {
					$.lore = "tooltip.runestone.lore.bones_mana_rune";
					$.rarity = Rarity.EPIC;
				}).build();
		
//		RUNE_OF_BONE_BUSTER = new BoneBusterRune.Builder(new ResourceLocation(TreasureBones.MODID, "bone_buster_rune"))
//				.with($ -> {
//					$.lore = "tooltip.runestone.lore.bone_buster_rune";
//					$rarity = Rarity.EPIC;
//				}).build();
	}
	
	/**
	 * 
	 */
	public static void register() {
		TreasureRunes.register(RUNE_OF_BONES_AS_MANA);
//		TreasureRunes.register(RUNE_OF_BONE_BUSTER);
	}
}
