package mod.gottsch.forge.treasure2bones.block;

import com.google.common.base.Preconditions;
import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.block.StandardChestBlock;
import com.someguyssoftware.treasure2.chest.TreasureChestTypes;
import com.someguyssoftware.treasure2.data.TreasureData;
import com.someguyssoftware.treasure2.enums.Rarity;
import com.someguyssoftware.treasure2.item.TreasureItemGroups;

import mod.gottsch.forge.treasure2bones.TreasureBones;
import mod.gottsch.forge.treasure2bones.setup.Registration;
import mod.gottsch.forge.treasure2bones.tileentity.BoneChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * 
 * @author Mark Gottschling on Sep 29, 2022
 *
 */
public class ModBlocks {
	public static Block BONE_CHEST;

	/**
	 *
	 */
	@Mod.EventBusSubscriber(modid = TreasureBones.MODID, bus = EventBusSubscriber.Bus.MOD)
	public static class RegistrationHandler {

		/**
		 * 
		 * @param event
		 */
		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			/*
			 *  block shapes
			 */
			VoxelShape vanillaChestShape = Block.box(1, 0, 1, 15, 15, 15);
			VoxelShape[] standardChestBounds = new VoxelShape[] {vanillaChestShape, vanillaChestShape, vanillaChestShape, vanillaChestShape};

			BONE_CHEST = new BoneChestBlock(TreasureBones.MODID, "bone_chest", BoneChestTileEntity.class,
					TreasureChestTypes.SINGLE_STANDARD, Rarity.SCARCE, Block.Properties.of(Material.STONE, MaterialColor.STONE).strength(3F))
					.setBounds(standardChestBounds);

			// register block with forge
			final IForgeRegistry<Block> registry = event.getRegistry();
			registry.register(BONE_CHEST);

			// register chest with Treasure2
			TreasureData.CHESTS_BY_RARITY.put(((StandardChestBlock) BONE_CHEST).getRarity(), BONE_CHEST);
			TreasureData.CHESTS_BY_NAME.put(BONE_CHEST.getRegistryName().getPath(), BONE_CHEST);
		}

		/**
		 * Register this mod's {@link ItemBlock}s.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
			final IForgeRegistry<Item> registry = event.getRegistry();

			@Deprecated
			BlockItem itemBlock = new BlockItem(BONE_CHEST, new Item.Properties().tab(TreasureItemGroups.TREASURE_ITEM_GROUP));
			final ResourceLocation registryName = Preconditions.checkNotNull(BONE_CHEST.getRegistryName(),
					"Block %s has null registry name", BONE_CHEST);

			registry.register(itemBlock.setRegistryName(registryName));
		}

		// TODO for future use
		// conveniance method: take a RegistryObject<Block> and make a corresponding RegistryObject<Item> from it
		public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block, Item.Properties itemProperties) {
			return Registration.ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), itemProperties));
		}
	}
}
