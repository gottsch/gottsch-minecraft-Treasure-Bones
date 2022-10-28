package mod.gottsch.forge.treasure2bones.eventhandler;

import javax.annotation.Nonnull;

import mod.gottsch.forge.treasure2bones.TreasureBones;
import mod.gottsch.forge.treasure2bones.eventhandler.loot.SkeletonsHeartFromSkeletonModifier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 
 * @author Mark Gottschling on Oct 5, 2022
 *
 */
@Mod.EventBusSubscriber(modid = TreasureBones.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventHandler {
	
	@SubscribeEvent
	public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
		event.getRegistry().registerAll(
				new SkeletonsHeartFromSkeletonModifier.Serializer().setRegistryName(
						new ResourceLocation(TreasureBones.MODID, "skeletons_heart_from_skeleton"))
				);
	}
}
