
package mod.gottsch.forge.treasure2bones.charm;

import java.util.Random;

import com.someguyssoftware.gottschcore.spatial.ICoords;
import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.charm.Charm;
import com.someguyssoftware.treasure2.charm.CharmEntity;
import com.someguyssoftware.treasure2.charm.ICharm;
import com.someguyssoftware.treasure2.charm.ICharmEntity;
import com.someguyssoftware.treasure2.charm.cost.ICostEvaluator;
import com.someguyssoftware.treasure2.util.ModUtils;

import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;

/**
 * 
 * @author Mark Gottschling on Oct 3, 2022
 *
 */
public class BoneBusterCharm extends Charm {
	public static String TYPE = "bone_buster";
	private static final Class<?> REGISTERED_EVENT = LivingHurtEvent.class;
	
	/**
	 * 
	 * @param builder
	 */
	BoneBusterCharm(Builder builder) {
		super(builder);
	}

	@Override
	public Class<?> getRegisteredEvent() {
		return REGISTERED_EVENT;
	}
	
	/**
	 * NOTE: it is assumed that only the allowable events are calling this action.
	 */
	@Override
	public boolean update(World world, Random random, ICoords coords, PlayerEntity player, Event event, final ICharmEntity entity) {
		boolean result = false;
		if (entity.getMana() > 0 && player.isAlive()) {
			DamageSource source = ((LivingHurtEvent) event).getSource();
			if (source.getDirectEntity() instanceof PlayerEntity && ((LivingHurtEvent)event).getEntityLiving() instanceof SkeletonEntity) {

					// get the source and amount
					double amount = ((LivingHurtEvent)event).getAmount();
					double newAmount = amount * getAmount();
					// increase damage amount
					((LivingHurtEvent)event).setAmount((float) newAmount);

					applyCost(world, random, coords, player, event, entity, newAmount);
					result = true;
					Treasure.LOGGER.debug("bone buster damage {} onto mob -> {} ", newAmount, ((LivingHurtEvent)event).getEntityLiving());
			}
		}
		return result;
	}
	
	@Override
	public ITextComponent getCharmDesc(ICharmEntity entity) {
		return new TranslationTextComponent("tooltip.charm.rate.bone_buster", Math.toIntExact((long) (entity.getAmount()*100)));
	}
	
	public static class Builder extends Charm.Builder {
		
		public Builder(Integer level) {
			super(ModUtils.asLocation(makeName(TYPE, level)), TYPE, level);
		}
		
		@Override
		public ICharm build() {
			return  new BoneBusterCharm(this);
		}
	}
}
