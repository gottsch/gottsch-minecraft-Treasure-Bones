package mod.gottsch.forge.treasure2bones.item;

import com.someguyssoftware.treasure2.adornment.AdornmentSize;
import com.someguyssoftware.treasure2.capability.*;
import com.someguyssoftware.treasure2.capability.modifier.GreatAdornmentLevelModifier;
import com.someguyssoftware.treasure2.charm.Charm;
import com.someguyssoftware.treasure2.charm.DrainCharm;
import com.someguyssoftware.treasure2.charm.ICharmEntity;
import com.someguyssoftware.treasure2.charm.TreasureCharmRegistry;
import com.someguyssoftware.treasure2.enums.AdornmentType;
import com.someguyssoftware.treasure2.item.NamedAdornment;
import com.someguyssoftware.treasure2.material.TreasureCharmableMaterials;
import com.someguyssoftware.treasure2.rune.IRuneEntity;
import com.someguyssoftware.treasure2.util.ModUtils;

import mod.gottsch.forge.treasure2bones.charm.BoneBusterCharm;
import mod.gottsch.forge.treasure2bones.charm.ModCharms;
import mod.gottsch.forge.treasure2bones.rune.BonesManaRune;
import mod.gottsch.forge.treasure2bones.rune.ModRunes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class BonesBane extends NamedAdornment {

	public BonesBane(AdornmentType type, AdornmentSize size, Properties properties) {
		super(type, size, properties);
	}

	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {

		/*
		 *  add enchantment. kinda hacky
		 */
		if (!EnchantmentHelper.hasVanishingCurse(stack)) {
			stack.enchant(Enchantments.VANISHING_CURSE, 1);
		}

		ICharmableCapability cap = new CharmableCapability.Builder(3, 1, 0).with($ -> {
			$.innate = true;
			$.imbuable = true;
			$.socketable = true;
			$.source = false;
			$.executing = true;
			$.namedByCharm = false;
			$.namedByMaterial = false;
			$.baseMaterial = TreasureCharmableMaterials.BONE.getName();
			$.sourceItem = ModItems.SKELETONS_HEART.get().getRegistryName();
			$.levelModifier = new GreatAdornmentLevelModifier();
		}).build();
		
		ICharmEntity e1 = ModCharms.makeBoneBuster(30).createEntity();
		ICharmEntity e2 = TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(BoneBusterCharm.TYPE, 30))).get().createEntity();
//		cap.getCharmEntities().get(InventoryType.INNATE).add(ModCharms.makeBoneBuster(30).createEntity());
		cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(BoneBusterCharm.TYPE, 30))).get().createEntity());
		cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(DrainCharm.DRAIN_TYPE, 16))).get().createEntity());
//		cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(DecayCurse.DECAY_TYPE, 10))).get().createEntity());
		
		IDurabilityCapability durabilityCap = new DurabilityCapability(500, 500, TreasureCharmableMaterials.BONE);
		durabilityCap.setInfinite(true);
		
		IRunestonesCapability runestonesCap = new RunestonesCapability.Builder(1, 0, 1).with($ -> {
			$.socketable = true;
		}).build();
		
		IRuneEntity runeEntity = ModRunes.RUNE_OF_BONES_AS_MANA.createEntity();	
		runestonesCap.add(InventoryType.INNATE, runeEntity);
		
		((BonesManaRune)runeEntity.getRunestone()).initCapabilityApply(cap, runeEntity);
		
		return new AdornmentCapabilityProvider(cap, runestonesCap, durabilityCap);
	}
}
