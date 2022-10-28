package mod.gottsch.forge.treasure2bones.tileentity;

import java.util.Random;

import javax.annotation.Nullable;

import com.someguyssoftware.gottschcore.spatial.Coords;
import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.inventory.StandardChestContainer;
import com.someguyssoftware.treasure2.lock.LockState;
import com.someguyssoftware.treasure2.tileentity.AbstractTreasureChestTileEntity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * 
 * @author Mark Gottschling on Sep 29, 2022
 *
 */
public class BoneChestTileEntity extends AbstractTreasureChestTileEntity {
	private static final double PROXIMITY_SQUARED = 36;

	// persistent state property
	private boolean locked;

	// display state properties
	public float skullYPosition;
	public float prevSkullYPosition;
	
	public float lockAngle;
	public float prevLockAngle;

	public boolean isSkullOpen = false;
	public boolean isSkullClosed = true;
	public boolean isLockOpen = true;
	public boolean isLockClosed = false;
	public boolean isLidOpen = false;
	public boolean isLidClosed = false;

	/**
	 * 
	 * @param texture
	 */
	public BoneChestTileEntity() {
		super(ModTileEntities.BONE_CHEST_TILE_ENTITY_TYPE);
		setCustomName(new TranslationTextComponent("display.bone_chest.name"));
		setLocked(false);
	}

	/**
	 * 
	 * @param windowID
	 * @param inventory
	 * @param player
	 * @return
	 */
	public Container createServerContainer(int windowID, PlayerInventory inventory, PlayerEntity player) {
		return new StandardChestContainer(windowID, inventory, this);
	}
	
	@Override
	public void updateEntityState() {
		// save the previous positions and angles of safe components
		this.prevLidAngle = this.lidAngle;
		this.prevSkullYPosition = this.skullYPosition;
		this.prevLockAngle = this.lockAngle;		

		// TODO going to need more properties for the skull opening and closing and isLocked and isUnlocked
		// as the entity will ALWAYS have a LockState/BoneLock present

		// check if player is within range
		boolean isProximityMet = false;
		for (PlayerEntity player : getLevel().players()) {
			// get the distance
			double distanceSq = player.distanceToSqr(this.getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ());
			if (distanceSq < PROXIMITY_SQUARED) {
				isProximityMet = true;
				break;
			}
		}

		if (isProximityMet) {
			// test the skull
			if (this.skullYPosition > -1.0F) { // TODO rename to skullYAmount and make a positive. it represents a percent of movement, not the actual position
				isSkullOpen = false;
				this.skullYPosition -= 0.1F;
				isSkullClosed = false;
				if (this.skullYPosition <= -1.0F) {
					this.skullYPosition = -1.0F;
					isSkullOpen = true;
				}
			} else {
				isSkullOpen = true;
			}
		} else {
			if (this.skullYPosition < 0.0F) {
				isSkullClosed = false;
				this.skullYPosition += 0.1F;
				isSkullOpen = false;
				if (this.skullYPosition >= 0.0F) {
					this.skullYPosition = 0.0F;
					isSkullClosed = true;
				}
			} else {
				isSkullClosed = true;
			}
		}

		// process lock rotation
		if (!isLocked() && !isLockOpen) {
			if (this.lockAngle > 0F) {
				this.lockAngle -= 0.1F;
				isLockClosed = false;
				if (this.lockAngle <= 0F) {
					this.lockAngle = 0F;
					isLockOpen = true;
				}
			}
			else {
				isLockOpen = true;
			}
		}
		else if (isLocked() && !isLockClosed) {
			if (this.lockAngle < 1.0F) {
				this.lockAngle += 0.1F;
				isLockOpen = false;
				if (this.lockAngle > 1.0F) {
					this.lockAngle = 1F;
					isLockClosed = true;
				}
			}
			else {
				isLockClosed = true;
			}
		}
		
		// opening ie. players
		if (this.openCount > 0) {
			// play the opening chest sound the at the beginning of opening
			if (this.lidAngle == 0.0F) {
				this.playSound(SoundEvents.CHEST_OPEN);
			}

			// test the lid
			if (this.lidAngle < 1.0F) {
				isLidOpen = false;
				this.lidAngle += 0.1F;
				isLidClosed = false;
				if (this.lidAngle >= 1.0F) {
					this.lidAngle = 1.0F;
					isLidOpen = true;
				}
			} else {
				isLidOpen = true;
			}
		}
		// closing ie no players
		else {
			float f2 = this.lidAngle;

			if (this.lidAngle > 0.0F) {
				isLidClosed = false;
				this.lidAngle -= 0.1F;
				isLidOpen = false;
				if (this.lidAngle <= 0.0F) {
					this.lidAngle = 0.0F;
					isLidClosed = true;
				}
			} else {
				isLidClosed = true;
			}

			// play the closing sound
			if (this.lidAngle < 0.06F && f2 >= 0.06F) {
				this.playSound(SoundEvents.CHEST_CLOSE);
			}
		}		

	}

	@Override
	public CompoundNBT save(CompoundNBT parentNBT) {
		super.save(parentNBT);
		try {
			parentNBT.putBoolean("locked", isLocked());
//			Treasure.LOGGER.debug("saving Bone chest locked -> {}", isLocked());
		} catch (Exception e) {
			Treasure.LOGGER.error("Error writing Properties to NBT:", e);
		}
		return parentNBT;
	}

	/**
	 * 
	 */
	@Override
	public void load(BlockState state, CompoundNBT parentNBT) {
		super.load(state, parentNBT);

		try {
			if (parentNBT.contains("locked")) {
				setLocked(parentNBT.getBoolean("locked"));
//				Treasure.LOGGER.debug("loading Bone chest locked -> {}", isLocked());
//				if (isLocked()) {
//					isLockOpen = false;
//					isLockClosed = true;
//				}
			}
		} catch (Exception e) {
			Treasure.LOGGER.error("Error reading to NBT:", e);
		}
	}

	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.worldPosition, 1, this.getUpdateTag());
	}

	@Override 
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		// TODO shouldn't call load() and save() as this sync's ALL the data every time which is unneeded
		load(getLevel().getBlockState(pkt.getPos()), pkt.getTag());
	}
	
	@Override
	public CompoundNBT getUpdateTag() {
		return this.save(new CompoundNBT());
	}


	
	@Override
	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public float getSkullYPosition() {
		return skullYPosition;
	}

	public void setSkullYPosition(float skullYPosition) {
		this.skullYPosition = skullYPosition;
	}

	public float getPrevSkullYPosition() {
		return prevSkullYPosition;
	}

	public void setPrevSkullYPosition(float prevSkullYPosition) {
		this.prevSkullYPosition = prevSkullYPosition;
	}

	public float getLockAngle() {
		return lockAngle;
	}

	public void setLockAngle(float lockAngle) {
		this.lockAngle = lockAngle;
	}

	public float getPrevLockAngle() {
		return prevLockAngle;
	}

	public void setPrevLockAngle(float prevLockAngle) {
		this.prevLockAngle = prevLockAngle;
	}
}
