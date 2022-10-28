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

import static com.someguyssoftware.treasure2.capability.TreasureCapabilities.DURABILITY;

import java.util.List;

import com.someguyssoftware.gottschcore.world.WorldInfo;
import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.block.AbstractChestBlock;
import com.someguyssoftware.treasure2.block.ITreasureChestProxy;
import com.someguyssoftware.treasure2.capability.IDurabilityCapability;
import com.someguyssoftware.treasure2.config.TreasureConfig;
import com.someguyssoftware.treasure2.item.KeyItem;
import com.someguyssoftware.treasure2.item.LockItem;
import com.someguyssoftware.treasure2.lock.LockState;
import com.someguyssoftware.treasure2.tileentity.AbstractTreasureChestTileEntity;

import mod.gottsch.forge.treasure2bones.tileentity.BoneChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

/**
 * 
 * @author Mark Gottschling on Sep 28, 2022
 *
 */
public class BoneKey extends KeyItem {
	
	public BoneKey(Properties properties) {
		super(properties);
	}
	
	/**
	 * Format: (Additions)
	 * 
	 * Specials: [text] [color=gold]
	 */
	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		
		tooltip.add(
				new TranslationTextComponent("tooltip.label.specials", 
				TextFormatting.GOLD + new TranslationTextComponent("tooltip.bone_key.specials").getString())
			);	
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		BlockPos chestPos = context.getClickedPos();
		// determine if block at pos is a treasure chest
		Block block = context.getLevel().getBlockState(chestPos).getBlock();
		if (block instanceof ITreasureChestProxy) {
			chestPos = ((ITreasureChestProxy)block).getChestPos(chestPos);
			block = context.getLevel().getBlockState(chestPos).getBlock();
		}

		if (block instanceof AbstractChestBlock) {
			// get the tile entity
			TileEntity tileEntity = context.getLevel().getBlockEntity(chestPos);
			if (tileEntity == null || !(tileEntity instanceof AbstractTreasureChestTileEntity)) {
				Treasure.LOGGER.warn("Null or incorrect TileEntity");
				return ActionResultType.FAIL;
			}
			AbstractTreasureChestTileEntity chestTileEntity = (AbstractTreasureChestTileEntity)tileEntity;

			// exit if on the client
			if (WorldInfo.isClientSide(context.getLevel())) {			
				return ActionResultType.FAIL;
			}

			// determine if chest has locks
			if (!chestTileEntity.hasLocks()) {
				return ActionResultType.SUCCESS;
			}

			try {
				ItemStack heldItemStack = context.getPlayer().getItemInHand(context.getHand());	
				boolean breakKey = true;
				boolean fitsLock = false;
				LockState lockState = null;
				boolean isKeyBroken = false;
				// check if this key is one that opens a lock (only first lock that key fits is unlocked).
				lockState = fitsFirstLock(chestTileEntity.getLockStates());
				if (lockState != null) {
					fitsLock = true;
				}

				if (fitsLock) {
					// ------ bone key specific code -----
					BoneChestTileEntity te = (BoneChestTileEntity)tileEntity;
					
					if (te.isLocked() && unlock(lockState.getLock())) {
						// unlock the lock
						doUnlock(context, chestTileEntity, lockState);
						
						// update the client
						chestTileEntity.sendUpdates();
						
						// don't break the key
						breakKey = false;
					} else if (!te.isLocked())  {
						// lock the chest
						// TODO add method doLock() that calls the effects etc.
						te.setLocked(true);
						chestTileEntity.sendUpdates();
						// don't break the key
						breakKey = false;
					}
					// ----- end of bone key specific code -----
				}

				IDurabilityCapability cap = heldItemStack.getCapability(DURABILITY).orElseThrow(IllegalStateException::new);
				
				// check key's breakability
				if (breakKey) {
					if ((isBreakable() || anyLockBreaksKey(chestTileEntity.getLockStates(), this)) && TreasureConfig.KEYS_LOCKS.enableKeyBreaks.get()) {
						
						// TODO make method breakAndShrink() using caps
						int damage = heldItemStack.getDamageValue() + (getMaxDamage() - (heldItemStack.getDamageValue() % getMaxDamage()));
                        heldItemStack.setDamageValue(damage);
                        if (heldItemStack.getDamageValue() >= cap.getDurability()) {
							// break key;
							heldItemStack.shrink(1);
                        }
                        
                        // do effects
                        doKeyBreakEffects(context.getLevel(), context.getPlayer(), chestPos, chestTileEntity);
                        
						// flag the key as broken
						isKeyBroken = true;
					}
					else if (!fitsLock) {
						doKeyNotFitEffects(context.getLevel(), context.getPlayer(), chestPos, chestTileEntity);
					}
					else {
						doKeyUnableToUnlockEffects(context.getLevel(), context.getPlayer(), chestPos, chestTileEntity);
					}						
				}

				// user attempted to use key - increment the damage
				if (isDamageable() && !isKeyBroken) {
					 heldItemStack.setDamageValue(heldItemStack.getDamageValue() + 1);
                    if (heldItemStack.getDamageValue() >= cap.getDurability()) {
                        heldItemStack.shrink(1);
                    }
				}
			}
			catch (Exception e) {
				Treasure.LOGGER.error("error: ", e);
			}			
		}		

		return ActionResultType.PASS;
	}
	
	/**
	 * 
	 * @param context
	 * @param chestTileEntity
	 * @param lockState
	 */
	public void doUnlock(ItemUseContext context, AbstractTreasureChestTileEntity chestTileEntity,	LockState lockState) {
		LockItem lock = lockState.getLock();		
		 lock.doUnlock(context.getLevel(), context.getPlayer(), context.getClickedPos(), chestTileEntity, lockState);
	}
}
