package mod.gottsch.forge.treasure2bones.block;

import com.someguyssoftware.treasure2.block.StandardChestBlock;
import com.someguyssoftware.treasure2.chest.TreasureChestType;
import com.someguyssoftware.treasure2.enums.Rarity;
import com.someguyssoftware.treasure2.tileentity.AbstractTreasureChestTileEntity;

import mod.gottsch.forge.treasure2bones.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;


/**
 * 
 * @author Mark Gottschling on Oct 8, 2022
 *
 */
public class BoneChestBlock extends StandardChestBlock {

	public BoneChestBlock(String modID, String name, Class<? extends AbstractTreasureChestTileEntity> tileEntity,
			TreasureChestType type, Rarity rarity, Properties properties) {
		super(modID, name, tileEntity, type, rarity, properties);
	}

	@Override
	 public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		AbstractTreasureChestTileEntity chestTileEntity = (AbstractTreasureChestTileEntity) super.createTileEntity(state, world);
		chestTileEntity.getLockStates().get(0).setLock(ModItems.BONE_LOCK.get());
		return chestTileEntity;
	}
}
