package mod.gottsch.forge.treasure2bones.charm;

import com.someguyssoftware.treasure2.charm.ICharm;
import com.someguyssoftware.treasure2.charm.TreasureCharmRegistry;
import com.someguyssoftware.treasure2.charm.TreasureCharms;

/**
 * 
 * @author Mark Gottschling on Oct 3, 2022
 *
 */
public class ModCharms {

	static {
		for (int level = 1; level <= 35; level++) {
			TreasureCharmRegistry.register(makeBoneBuster(level));
		}
	}
	
	public static void init() {}
	
	/**
	 * 
	 * @param level
	 * @return
	 */
	public static ICharm makeBoneBuster(int level) {
		ICharm charm =  new BoneBusterCharm.Builder(level).with($ -> {
			$.mana = level * 20.0;
			$.amount = level < 10 ? 1.5 :  level < 20 ? 2.0 : 3.0;
			$.effectStackable = true;
			$.rarity = TreasureCharms.LEVEL_RARITY.get(level);
			$.recharges = TreasureCharms.getRecharges($.rarity);
		})	.build();
		return charm;
	}

	// TODO why can't i make these in a static
	public static void register() {
//		for (int level = 1; level <= 35; level++) {
//			TreasureCharmRegistry.register(makeBoneBuster(level));
//		}
	}
}
