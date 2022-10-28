package mod.gottsch.forge.treasure2bones.tileentity;

import mod.gottsch.forge.treasure2bones.TreasureBones;
import mod.gottsch.forge.treasure2bones.block.ModBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * 
 * @author Mark Gottschling on Sep 29, 2022
 *
 */
public class ModTileEntities {
	public static TileEntityType<BoneChestTileEntity> BONE_CHEST_TILE_ENTITY_TYPE;
	
	@Mod.EventBusSubscriber(modid = TreasureBones.MODID, bus = EventBusSubscriber.Bus.MOD)
	public static class RegistrationHandler {

		@SubscribeEvent
		public static void onTileEntityTypeRegistration(final RegistryEvent.Register<TileEntityType<?>> event) {
			BONE_CHEST_TILE_ENTITY_TYPE = TileEntityType.Builder
					.of(BoneChestTileEntity::new, ModBlocks.BONE_CHEST)
					.build(null);
			BONE_CHEST_TILE_ENTITY_TYPE.setRegistryName("bone_chest");
			event.getRegistry().register(BONE_CHEST_TILE_ENTITY_TYPE);
		}
	}
}
