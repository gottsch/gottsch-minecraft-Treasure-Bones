package mod.gottsch.forge.treasure2bones.item;

import java.util.List;

import com.someguyssoftware.treasure2.item.GemItem;
import com.someguyssoftware.treasure2.item.WealthItem;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

/**
 * 
 * @author Mark Gottschling on Oct 1, 2022
 *
 */
public class SkeletonsHeart extends GemItem {

	public SkeletonsHeart(Properties properties) {
		super(properties);
	}

	/**
	 * 
	 */
	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		// standard coin info
//		tooltip.add(new TranslationTextComponent("tooltip.label.wishable").withStyle(TextFormatting.GOLD, TextFormatting.ITALIC));
		tooltip.add(new TranslationTextComponent("tooltip.gem.skeletons_heart").withStyle(TextFormatting.DARK_RED, TextFormatting.ITALIC));
	}

}
