/*
 * This file is part of  Treasure2: Bones.
 * Copyright (c) 2022 Mark Gottschling (gottsch)
 *
 * Treasure2: Bones is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Treasure2: Bones is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Treasure2: Bones.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package mod.gottsch.forge.treasure2bones.item;

import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.item.KeyItem;
import com.someguyssoftware.treasure2.item.LockItem;
import com.someguyssoftware.treasure2.lock.LockState;
import com.someguyssoftware.treasure2.tileentity.AbstractTreasureChestTileEntity;

import mod.gottsch.forge.treasure2bones.tileentity.BoneChestTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 
 * @author Mark Gottschling on Sep 25, 2022
 *
 */
public class BoneLock extends LockItem {

	public BoneLock(Properties properties) {
		super(properties);
	}
	
	public BoneLock(Properties properties, KeyItem[] keys) {
		super(properties);
	}

	/**
	 * NOTE this is moot as a BoneLock item doesn't exist as a registered Item
	 * @param tileEntity
	 * @param player
	 * @param heldItem
	 * @return
	 */
	@Override
	public boolean handleHeldLock(AbstractTreasureChestTileEntity tileEntity, PlayerEntity player, ItemStack heldItem) {
		return false;
	}
	
	public void doUnlock(World level, PlayerEntity player, BlockPos chestPos,
			AbstractTreasureChestTileEntity chestTileEntity, LockState lockState) {
		
		Treasure.LOGGER.debug("in bone lock doUnlock()");
		doUnlockedEffects(level, player, chestPos, chestTileEntity, lockState);
		 
		// NOTE does NOT remove the lock from the lock state.
		
		// update TE's locked property OR this can be executed from the BoneKey
		((BoneChestTileEntity)chestTileEntity).setLocked(false);
	}
	
	@Override
	public void dropLock(World level, BlockPos pos) {
		// do nothing ie. don't drop a lock
	}
}
