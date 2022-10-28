package mod.gottsch.forge.treasure2bones.gui;

import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.block.TreasureBlocks;
import com.someguyssoftware.treasure2.gui.render.tileentity.WoodChestTileEntityRenderer;
import com.someguyssoftware.treasure2.tileentity.TreasureTileEntities;

import mod.gottsch.forge.treasure2bones.TreasureBones;
import mod.gottsch.forge.treasure2bones.block.ModBlocks;
import mod.gottsch.forge.treasure2bones.gui.render.tileentity.BoneChestTileEntityRenderer;
import mod.gottsch.forge.treasure2bones.tileentity.ModTileEntities;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * 
 * @author Mark Gottschling on Oct 6, 2022
 *
 */
public class ModGuis {
	@Mod.EventBusSubscriber(modid = TreasureBones.MODID, bus = EventBusSubscriber.Bus.MOD, value=Dist.CLIENT)
	public static class RegistrationHandler {
		// register the factory that is used on the client to generate Screen corresponding to our Container
		@SubscribeEvent
		public static void onClientSetupEvent(FMLClientSetupEvent event) {
			RenderTypeLookup.setRenderLayer(ModBlocks.BONE_CHEST, RenderType.cutoutMipped());

			// register the custom renderer for our tile entity
			ClientRegistry.bindTileEntityRenderer(ModTileEntities.BONE_CHEST_TILE_ENTITY_TYPE, BoneChestTileEntityRenderer::new);
		}			
	}
}
