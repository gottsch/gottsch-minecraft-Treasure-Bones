
package mod.gottsch.forge.treasure2bones.setup;

import mod.gottsch.forge.treasure2bones.TreasureBones;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 
 * @author Mark Gottschling on Sep 29, 2022
 *
 */
public class Registration {
	
	// deferred registries
	public final static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TreasureBones.MODID);
	public final static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TreasureBones.MODID);

	/**
	 * 
	 */
	public static void registerBlocks() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		BLOCKS.register(eventBus);
	}
	
	/**
	 * 
	 */
	public static void registerItems() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ITEMS.register(eventBus);
	}
}
