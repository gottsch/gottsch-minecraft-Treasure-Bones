package mod.gottsch.forge.treasure2bones.eventhandler.loot;

import java.util.List;

import com.google.gson.JsonObject;
import com.someguyssoftware.gottschcore.random.RandomHelper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 
 * @author Mark Gottschling on Oct 5, 2022
 *
 */
public class SkeletonsHeartFromSkeletonModifier extends LootModifier {
	private final Item addition;
	
	protected SkeletonsHeartFromSkeletonModifier(ILootCondition[] conditionsIn, Item addition) {
		super(conditionsIn);
		this.addition = addition;
	}

	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		if (RandomHelper.checkProbability(context.getRandom(), (0.025D * context.getLootingModifier()))) { // TODO config option
			generatedLoot.add(new ItemStack(addition, 1));
		}
		return generatedLoot;
	}
	
	public static class Serializer extends GlobalLootModifierSerializer<SkeletonsHeartFromSkeletonModifier> {

		@Override
		public SkeletonsHeartFromSkeletonModifier read(ResourceLocation location, JsonObject object,	ILootCondition[] conditions) {

			Item addition = ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getAsString(object, "addition")));
			return new SkeletonsHeartFromSkeletonModifier(conditions, addition);
		}

		@Override
		public JsonObject write(SkeletonsHeartFromSkeletonModifier instance) {
			JsonObject json = makeConditions(instance.conditions);
			json.addProperty("addition", ForgeRegistries.ITEMS.getKey(instance.addition).toString());
			return json;
		}		
	}
}
