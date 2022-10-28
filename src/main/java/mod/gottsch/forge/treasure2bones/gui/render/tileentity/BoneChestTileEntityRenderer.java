package mod.gottsch.forge.treasure2bones.gui.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.someguyssoftware.treasure2.gui.model.SafeModel;
import com.someguyssoftware.treasure2.gui.render.tileentity.AbstractChestTileEntityRenderer;
import com.someguyssoftware.treasure2.tileentity.AbstractTreasureChestTileEntity;
import com.someguyssoftware.treasure2.tileentity.SafeTileEntity;

import mod.gottsch.forge.treasure2bones.TreasureBones;
import mod.gottsch.forge.treasure2bones.gui.model.BoneChestModel;
import mod.gottsch.forge.treasure2bones.tileentity.BoneChestTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;

/**
 * 
 * @author Mark Gottschling on Oct 7, 2022
 *
 */
public class BoneChestTileEntityRenderer extends AbstractChestTileEntityRenderer {

	/**
	 * 
	 * @param tileEntityRendererDispatcher
	 */
	public BoneChestTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
		super(tileEntityRendererDispatcher);

		setTexture(new ResourceLocation(TreasureBones.MODID + ":textures/entity/bone_chest.png"));
		setModel(new BoneChestModel());
	}

	@Override
	public void updateModelLidRotation(AbstractTreasureChestTileEntity tileEntity, float partialTicks) {
		BoneChestTileEntity te = (BoneChestTileEntity) tileEntity;
		final float MAX_DIST = 1.0F;
		// don't recalculate skull position if lid is open ie. the values shouldn't change so don't waste ticks
		if (te.isLidClosed) {
			float amount = te.prevSkullYPosition + (te.skullYPosition - te.prevSkullYPosition) * partialTicks;
			((BoneChestModel)getModel()).getJaw().y = -(amount * MAX_DIST);
			((BoneChestModel)getModel()).getSkull().y = (amount * MAX_DIST);
		}
		
		// TODO lock property is not being synced and thereofr in the TE the angles aren't being calculated on the client
		float lockRotation = te.prevLockAngle + (te.lockAngle - te.prevLockAngle) * partialTicks;
		lockRotation = 1.0F - lockRotation;
		lockRotation = 1.0F - lockRotation * lockRotation * lockRotation;
		((BoneChestModel)getModel()).getLock().zRot = -(lockRotation * (float)Math.PI / getAngleModifier());
		
		float lidRotation = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;
		lidRotation = 1.0F - lidRotation;
		lidRotation = 1.0F - lidRotation * lidRotation * lidRotation;
		getModel().getLid().xRot = -(lidRotation * (float)Math.PI / getAngleModifier());
	}

	@Override
	public void renderLocks(AbstractTreasureChestTileEntity tileEntity, MatrixStack matrixStack, IRenderTypeBuffer renderBuffer, int combinedLight, int combinedOverlay) {
		// do nothing ie do not render locks.
	}

}
