
package mod.gottsch.forge.treasure2bones.charm.cost;

import java.util.Random;

import com.someguyssoftware.gottschcore.spatial.ICoords;
import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.charm.ICharmEntity;
import com.someguyssoftware.treasure2.charm.cost.CostEvaluator;
import com.someguyssoftware.treasure2.charm.cost.ICostEvaluator;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.Event;

/**
 * 
 * @author Mark Gottschling on Oct 3, 2022
 *
 */
public class BonesCostEvaluator extends CostEvaluator {

	private ICostEvaluator evaluator;
	
	public BonesCostEvaluator() {
		evaluator = new CostEvaluator();
	}
	
	public BonesCostEvaluator(ICostEvaluator evaluator) {
//		Treasure.LOGGER.debug("receiving child evaluator of -> {}", evaluator.getClass().getSimpleName());
		this.evaluator = evaluator;
	}
	
	@Override
	public double apply(World world, Random random, ICoords coords, PlayerEntity player, Event event,
			ICharmEntity entity, double amount) {
		Treasure.LOGGER.debug("executing...");
		double newAmount = amount;
		boolean isBoneConsumed = false;
		
		ItemStack selectedStack = ItemStack.EMPTY;
		// find first bone item
		for (ItemStack stack : player.inventory.items) {
			if (stack.getItem() == Items.BONE) {
				selectedStack = stack;
				break;
			}
		}
		
		if (selectedStack != null && selectedStack != ItemStack.EMPTY) {	
			// consume one bone regardless of mana cost. therefor better to use this on a high cost charm(s) (which don't really exist yet)
			selectedStack.shrink(1);
			newAmount = 0;
			isBoneConsumed = true;
		}

		// if not damaged, process against default evaluator
		if (!isBoneConsumed) {
			Treasure.LOGGER.debug("no bone consumed, use mana using cost eval ->{}", evaluator.getClass().getSimpleName());
			// execute the orignal evaluator
			newAmount = entity.getCharm().getCostEvaluator().apply(world, random, coords, player, event, entity, amount);
		}		
		return newAmount;
	}
	
	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		try {
			super.save(nbt); // save my className to nbt
			
			CompoundNBT tag = new CompoundNBT();		
			evaluator.save(tag);
			nbt.put("evaluator", tag);
		}
		catch(Exception e) {
			Treasure.LOGGER.error("error saving BonesCostEvaluator -> ", e);
		}
		return nbt;
	}
	
	@Override
	public void load(CompoundNBT nbt) {
		try {
		super.load(nbt);
//		Treasure.logger.debug("loading equipment cost eval...");
		if (nbt.contains("evaluator") && nbt.getCompound("evaluator").contains("costClass")) {
			try {
				CompoundNBT tag = nbt.getCompound("evaluator");
					String costEvalClass = nbt.getString("costClass");
//					Treasure.logger.debug("child cost class -> {}", costEvalClass);
					Object o = Class.forName(costEvalClass).newInstance();
					((ICostEvaluator)o).load(tag);
					this.evaluator = (ICostEvaluator)o;

			}
			catch(Exception e) {
				Treasure.LOGGER.warn("unable to create cost evaluator from class string:");
				Treasure.LOGGER.error(e);
				this.evaluator = new CostEvaluator();
			}
		}
		else {
			this.evaluator = new CostEvaluator();
		}
		}
		catch(Exception e) {
			Treasure.LOGGER.error("error loading BonesCostEvaluator -> ", e);
		}
	}

	public ICostEvaluator getEvaluator() {
		return evaluator;
	}
}
