
package mod.gottsch.forge.treasure2bones.rune;

import java.util.Map.Entry;

import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.capability.ICharmableCapability;
import com.someguyssoftware.treasure2.capability.IDurabilityCapability;
import com.someguyssoftware.treasure2.capability.InventoryType;
import com.someguyssoftware.treasure2.capability.TreasureCapabilities;
import com.someguyssoftware.treasure2.charm.ICharmEntity;
import com.someguyssoftware.treasure2.charm.cost.ICostEvaluator;
import com.someguyssoftware.treasure2.rune.IRune;
import com.someguyssoftware.treasure2.rune.IRuneEntity;
import com.someguyssoftware.treasure2.rune.Rune;

import mod.gottsch.forge.treasure2bones.charm.cost.BonesCostEvaluator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * 
 * @author Mark Gottschling on Oct 3, 2022
 *
 */
public class BonesManaRune extends Rune {

	protected BonesManaRune(Builder builder) {
		super(builder);
	}

	@Override
	public boolean isValid(ItemStack itemStack) {		
		// has charmable
		return itemStack.getCapability(TreasureCapabilities.CHARMABLE).isPresent();
	}

	/**
	 * Applies the Rune's ability/modification to a Capability.
	 * Used only during Item.initCapability().
	 */
	public void initCapabilityApply(ICharmableCapability charmCap, IRuneEntity entity) {
		process(charmCap, entity);
	}
	
	@Override
	public void apply(ItemStack itemStack, IRuneEntity runestoneEntity) {
		if (!isValid(itemStack)) {
			return;
		}
		
		ICharmableCapability charmableCap =  itemStack.getCapability(TreasureCapabilities.CHARMABLE).map(cap -> cap).orElse(null);
		process(charmableCap, runestoneEntity);
		
//		itemStack.getCapability(TreasureCapabilities.CHARMABLE).ifPresent(charmableCap -> {
//			charmableCap.getCharmEntities().forEach((type, charmEntity) -> {
//				charmEntity.setCostEvaluator(new BonesCostEvaluator(charmEntity.getCostEvaluator()));
//				Treasure.LOGGER.debug("setting entity -> {} to use cost eval -> {} with child eval -> {}", charmEntity.getCharm().getName().toString(), charmEntity.getCostEvaluator().getClass().getSimpleName(),
//						((BonesCostEvaluator)charmEntity.getCostEvaluator()).getEvaluator().getClass().getSimpleName());
//			});
//			runestoneEntity.setApplied(true);			
//		});
	}

	protected void process(ICharmableCapability charmableCap, IRuneEntity runestoneEntity) {
		if (charmableCap != null) {
			for (Entry<InventoryType, ICharmEntity> s : charmableCap.getCharmEntities().entries()) {
				InventoryType type = s.getKey();
				ICharmEntity ce = s.getValue();
				ICostEvaluator costor = ce.getCostEvaluator();
			}
			charmableCap.getCharmEntities().forEach((type, charmEntity) -> {
				ICostEvaluator costor = charmEntity.getCostEvaluator();
				charmEntity.setCostEvaluator(new BonesCostEvaluator());
				Treasure.LOGGER.debug("setting entity -> {} to use cost eval -> {} with child eval -> {}", charmEntity.getCharm().getName().toString(), charmEntity.getCostEvaluator().getClass().getSimpleName(),
						((BonesCostEvaluator)charmEntity.getCostEvaluator()).getEvaluator().getClass().getSimpleName());
			});
			runestoneEntity.setApplied(true);	
		}
	}
	
	@Override
	public void undo(ItemStack itemStack, IRuneEntity runestoneEntity) {
		itemStack.getCapability(TreasureCapabilities.CHARMABLE).ifPresent(charmableCap -> {
			charmableCap.getCharmEntities().forEach((type, charmEntity) -> {
				if (charmEntity.getCostEvaluator() instanceof BonesCostEvaluator) {
					charmEntity.setCostEvaluator(((BonesCostEvaluator)charmEntity.getCostEvaluator()).getEvaluator());
				}
			});
			runestoneEntity.setApplied(false);
		});
	}

	// TODO extend to take a ICostEvaluator as a required param
	public static class Builder extends Rune.Builder {
		public Builder(ResourceLocation name) {
			super(name);
		}
		@Override
		public IRune build() {
			return new BonesManaRune(this);
		}
	}
}
