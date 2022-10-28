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
package mod.gottsch.forge.treasure2bones.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;

import com.someguyssoftware.gottschcore.loot.LootTableShell;
import com.someguyssoftware.treasure2.adornment.TreasureAdornmentRegistry;
import com.someguyssoftware.treasure2.capability.AdornmentCapabilityProvider;
import com.someguyssoftware.treasure2.capability.CharmableCapability;
import com.someguyssoftware.treasure2.capability.DurabilityCapability;
import com.someguyssoftware.treasure2.capability.ICharmableCapability;
import com.someguyssoftware.treasure2.capability.IDurabilityCapability;
import com.someguyssoftware.treasure2.capability.IRunestonesCapability;
import com.someguyssoftware.treasure2.capability.InventoryType;
import com.someguyssoftware.treasure2.capability.RunestonesCapability;
import com.someguyssoftware.treasure2.capability.RunestonesCapabilityProvider;
import com.someguyssoftware.treasure2.capability.modifier.GreatAdornmentLevelModifier;
import com.someguyssoftware.treasure2.capability.modifier.NoLevelModifier;
import com.someguyssoftware.treasure2.charm.Charm;
import com.someguyssoftware.treasure2.charm.DecayCurse;
import com.someguyssoftware.treasure2.charm.DrainCharm;
import com.someguyssoftware.treasure2.charm.TreasureCharmRegistry;
import com.someguyssoftware.treasure2.enums.AdornmentType;
import com.someguyssoftware.treasure2.enums.Category;
import com.someguyssoftware.treasure2.enums.Rarity;
import com.someguyssoftware.treasure2.item.Adornment;
import com.someguyssoftware.treasure2.item.KeyItem;
import com.someguyssoftware.treasure2.item.LockItem;
import com.someguyssoftware.treasure2.item.NamedAdornment;
import com.someguyssoftware.treasure2.item.RunestoneItem;
import com.someguyssoftware.treasure2.item.TreasureItems;
import com.someguyssoftware.treasure2.item.TreasureItems.AdornmentItemsBuilder;
import com.someguyssoftware.treasure2.loot.TreasureLootTableRegistry;
import com.someguyssoftware.treasure2.material.TreasureCharmableMaterials;
import com.someguyssoftware.treasure2.rune.IRuneEntity;
import com.someguyssoftware.treasure2.util.ModUtils;

import mod.gottsch.forge.treasure2bones.TreasureBones;
import mod.gottsch.forge.treasure2bones.charm.ModCharms;
import mod.gottsch.forge.treasure2bones.rune.BonesManaRune;
import mod.gottsch.forge.treasure2bones.rune.ModRunes;
import mod.gottsch.forge.treasure2bones.setup.Registration;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

/**
 * 
 * @author Mark Gottschling on Sep 29, 2022
 *
 */
@Mod.EventBusSubscriber(modid = TreasureBones.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {
	public static final Map<ResourceLocation, RegistryObject<Adornment>> ADORNMENT_ITEMS = new HashMap<>();

	public static final Supplier<Item.Properties> ITEM_PROPERTIES = () -> new Item.Properties();

	// keys
	public static RegistryObject<KeyItem> BONE_KEY = 
			Registration.ITEMS.register("bone_key", () -> new BoneKey(ITEM_PROPERTIES.get().durability(10))
					.setCategory(Category.ELEMENTAL)
					.setRarity(Rarity.SCARCE)
					.setCraftable(false));

	// locks - TEMP?
	public static RegistryObject<LockItem> BONE_LOCK = 
			Registration.ITEMS.register("bone_lock", () -> new BoneLock(new Item.Properties())
			.setCategory(Category.ELEMENTAL)
			.setRarity(Rarity.SCARCE));
	
	// gems
	public static RegistryObject<Item> SKELETONS_HEART = 
			Registration.ITEMS.register("skeletons_heart", () -> new SkeletonsHeart(ITEM_PROPERTIES.get()) {
				@Override
				public List<LootTableShell> getLootTables() {
					return TreasureLootTableRegistry.getLootTableMaster().getLootTableByRarity(Rarity.LEGENDARY);
				}
				@Override
				public ItemStack getDefaultLootKey (Random random) {
					return new ItemStack(TreasureItems.SAPPHIRE.get());
				}			
			});

	// runes
	public static RegistryObject<RunestoneItem> BONES_MANA_RUNESTONE = 
			Registration.ITEMS.register("bones_mana_runestone", () -> new RunestoneItem(ITEM_PROPERTIES.get()) {
				public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
					IRunestonesCapability cap = new RunestonesCapability.Builder(1, 0, 0).with($ -> {
						$.bindable = true;
					}).build();
					cap.add(InventoryType.INNATE, ModRunes.RUNE_OF_BONES_AS_MANA.createEntity());	
					return new RunestonesCapabilityProvider(cap);
				}
			});

	// adornments
	public static RegistryObject<Adornment>BONES_BANE = Registration.ITEMS.register("bones_bane", 
			() -> new NamedAdornment(AdornmentType.RING, TreasureAdornmentRegistry.GREAT, ITEM_PROPERTIES.get()) {
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
				$.sourceItem = SKELETONS_HEART.get().getRegistryName();
				$.levelModifier = new GreatAdornmentLevelModifier();
			}).build();
			cap.getCharmEntities().get(InventoryType.INNATE).add(ModCharms.makeBoneBuster(30).createEntity());
			cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(DrainCharm.DRAIN_TYPE, 16))).get().createEntity());
			cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(DecayCurse.DECAY_TYPE, 10))).get().createEntity());
			
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
	});
	
	//	public static RegistryObject<Adornment> TEST = Registration.ITEMS.register("onyx_bone_ring", () -> new Adornment(AdornmentType.RING, TreasureAdornmentRegistry.STANDARD, new Item.Properties()) {
	//		public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
	//			ICharmableCapability cap = new CharmableCapability.Builder(0, 0, 0).with($ -> {
	//				$.innate = false;
	//				$.imbuable = false;
	//				$.socketable = true;
	//				$.source = false;
	//				$.executing = true;
	//				$.baseMaterial = TreasureCharmableMaterials.GOLD.getName();
	//				$.sourceItem = TreasureItems.ONYX.get().getRegistryName();
	//				$.levelModifier = new GreatAdornmentLevelModifier();
	//			}).build();
	//			IDurabilityCapability durabilityCap = new DurabilityCapability(1000, 1000, TreasureCharmableMaterials.GOLD);
	//			IRunestonesCapability runestonesCap = new RunestonesCapability.Builder(0, 0, 2).with($ -> {
	//				$.socketable = true;
	//			}).build();
	//			return new AdornmentCapabilityProvider(cap, runestonesCap, durabilityCap);
	//		}
	//	});

	static {
		AdornmentItemsBuilder builder = new AdornmentItemsBuilder(TreasureBones.MODID);
		List<Pair<String, Supplier<Adornment>>> adornments =  builder
				.types(AdornmentType.BRACELET, AdornmentType.NECKLACE, AdornmentType.RING)
				.sizes(TreasureAdornmentRegistry.STANDARD, TreasureAdornmentRegistry.GREAT)
				.materials(TreasureCharmableMaterials.BONE)
				.mapInnate(TreasureCharmableMaterials.BONE, 1)
				.mapLevelModifier(TreasureAdornmentRegistry.STANDARD, new NoLevelModifier())
				.mapLevelModifier(TreasureAdornmentRegistry.GREAT, new GreatAdornmentLevelModifier())
				.deferredBuild();
		adornments.addAll(builder.useSourceDefaults().sources(new ResourceLocation(TreasureBones.MODID, "skeletons_heart")).deferredBuild()); 
		adornments.forEach(a -> {
			// register each with the deferred registry
			RegistryObject<Adornment> adornment = Registration.ITEMS.register(a.getLeft(), () -> a.getRight().get());
			// add to local cache
			ADORNMENT_ITEMS.put(new ResourceLocation(TreasureBones.MODID, a.getLeft()), adornment);
		});
	}

	// locks
	// NOTE BoneLock is not registered as a game item. It is used internally in a Bone Chest and therefor only needs to be a back-end class
	// NOTE since BoneLock is NOT a RegistryObject in a DeferredRegistry, it needs to finishes its initialized in the RegistryEvent
//	public static LockItem BONE_LOCK = new BoneItem(new Item.Properties())
//			.setCategory(Category.ELEMENTAL)
//			.setRarity(Rarity.SCARCE);
	
	/**
	 * 
	 */
	public static void register() {		
		// cycle through all block and create items
		Registration.registerItems();
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {

	}
}
