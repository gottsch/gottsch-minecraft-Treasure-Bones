package mod.gottsch.forge.treasure2bones.gui.model;
// Made with Blockbench 4.4.2
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.someguyssoftware.treasure2.gui.model.AbstractTreasureChestModel;
import com.someguyssoftware.treasure2.tileentity.AbstractTreasureChestTileEntity;
import com.someguyssoftware.treasure2.tileentity.SafeTileEntity;

import mod.gottsch.forge.treasure2bones.tileentity.BoneChestTileEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * 
 * @author Mark Gottschling on Sep 29, 2022
 *
 */
public class BoneChestModel extends AbstractTreasureChestModel {
	private final ModelRenderer chest;
	private final ModelRenderer lid;
	private final ModelRenderer bone5_r1;
	private final ModelRenderer bone4_r1;
	private final ModelRenderer bone3_r1;
	private final ModelRenderer bone2_r1;
	private final ModelRenderer bone1_r1;
	private final ModelRenderer rightBone2_r1;
	private final ModelRenderer leftBone1_r1;
	private final ModelRenderer trunk;
	private final ModelRenderer rightBone2_r2;
	private final ModelRenderer rightBone1_r1;
	private final ModelRenderer leftBone2_r1;
	private final ModelRenderer rightBone5_r1;
	private final ModelRenderer leftBone4_r1;
	private final ModelRenderer rightBone3_r1;
	private final ModelRenderer leftBone1_r2;
	private final ModelRenderer skull;
	private final ModelRenderer jaw;
	private final ModelRenderer lock;

	/**
	 * 
	 */
	public BoneChestModel() {
		super(RenderType::entityCutout);
		
		texWidth = 128;
		texHeight = 128;

		chest = new ModelRenderer(this);
		chest.setPos(0.0F, 24.0F, 0.0F);
		

		lid = new ModelRenderer(this);
		lid.setPos(-0.8F, -10.7F, 6.7F);
		chest.addChild(lid);
		lid.texOffs(0, 27).addBox(-6.2F, -4.3F, -13.7F, 14.0F, 4.0F, 14.0F, 0.0F, false);
		lid.texOffs(52, 0).addBox(6.3F, -3.3F, -1.2F, 2.0F, 3.0F, 2.0F, 0.0F, false);
		lid.texOffs(52, 0).addBox(-6.7F, -3.3F, -14.2F, 2.0F, 3.0F, 2.0F, 0.0F, false);
		lid.texOffs(0, 46).addBox(5.8F, -5.3F, -14.7F, 3.0F, 3.0F, 3.0F, -0.2F, false);
		lid.texOffs(13, 46).addBox(5.8F, -5.3F, -1.7F, 3.0F, 3.0F, 3.0F, -0.2F, false);
		lid.texOffs(13, 46).addBox(-7.2F, -5.3F, -14.7F, 3.0F, 3.0F, 3.0F, -0.2F, false);
		lid.texOffs(0, 46).addBox(-7.45F, -5.3F, -1.7F, 3.0F, 3.0F, 3.0F, -0.2F, false);

		bone5_r1 = new ModelRenderer(this);
		bone5_r1.setPos(-5.7F, -3.8F, -6.7F);
		lid.addChild(bone5_r1);
		setRotationAngle(bone5_r1, 0.0F, -0.0436F, 0.0436F);
		bone5_r1.texOffs(45, 15).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 2.0F, 12.0F, 0.0F, false);

		bone4_r1 = new ModelRenderer(this);
		bone4_r1.setPos(-2.2F, -3.8F, -6.7F);
		lid.addChild(bone4_r1);
		setRotationAngle(bone4_r1, -0.0436F, 0.0436F, 0.0F);
		bone4_r1.texOffs(42, 31).addBox(-1.0F, -1.0F, -7.5F, 2.0F, 2.0F, 15.0F, 0.0F, false);

		bone3_r1 = new ModelRenderer(this);
		bone3_r1.setPos(0.8F, -3.8F, -6.7F);
		lid.addChild(bone3_r1);
		setRotationAngle(bone3_r1, 0.0436F, 0.0F, 0.0F);
		bone3_r1.texOffs(42, 31).addBox(-1.0F, -1.0F, -7.5F, 2.0F, 2.0F, 15.0F, 0.0F, false);

		bone2_r1 = new ModelRenderer(this);
		bone2_r1.setPos(3.8F, -3.8F, -6.7F);
		lid.addChild(bone2_r1);
		setRotationAngle(bone2_r1, 0.0F, -0.0436F, -0.0873F);
		bone2_r1.texOffs(42, 31).addBox(-1.0F, -1.0F, -7.5F, 2.0F, 2.0F, 15.0F, 0.0F, false);

		bone1_r1 = new ModelRenderer(this);
		bone1_r1.setPos(7.3F, -3.8F, -6.7F);
		lid.addChild(bone1_r1);
		setRotationAngle(bone1_r1, 0.0F, 0.0F, -0.0873F);
		bone1_r1.texOffs(45, 15).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 2.0F, 12.0F, 0.0F, false);

		rightBone2_r1 = new ModelRenderer(this);
		rightBone2_r1.setPos(0.8F, 10.7F, -6.7F);
		lid.addChild(rightBone2_r1);
		setRotationAngle(rightBone2_r1, 0.0F, -0.0436F, 0.0F);
		rightBone2_r1.texOffs(52, 0).addBox(-7.5F, -14.0F, 5.5F, 2.0F, 3.0F, 2.0F, 0.0F, false);

		leftBone1_r1 = new ModelRenderer(this);
		leftBone1_r1.setPos(7.3F, -1.8F, -13.2F);
		lid.addChild(leftBone1_r1);
		setRotationAngle(leftBone1_r1, 0.0F, 0.0873F, 0.0F);
		leftBone1_r1.texOffs(52, 0).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);

		trunk = new ModelRenderer(this);
		trunk.setPos(0.0F, -5.0F, 0.0F);
		chest.addChild(trunk);
		trunk.texOffs(0, 0).addBox(-7.0F, -7.0F, -7.0F, 14.0F, 12.0F, 14.0F, 0.0F, false);
		trunk.texOffs(43, 30).addBox(5.5F, -4.0F, -4.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);
		trunk.texOffs(48, 49).addBox(5.5F, -2.0F, 2.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

		rightBone2_r2 = new ModelRenderer(this);
		rightBone2_r2.setPos(-6.5F, -0.5F, 6.5F);
		trunk.addChild(rightBone2_r2);
		setRotationAngle(rightBone2_r2, -0.0436F, 0.0F, -0.0436F);
		rightBone2_r2.texOffs(0, 0).addBox(-1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, 0.0F, false);

		rightBone1_r1 = new ModelRenderer(this);
		rightBone1_r1.setPos(-6.5F, -0.5F, -6.5F);
		trunk.addChild(rightBone1_r1);
		setRotationAngle(rightBone1_r1, 0.0436F, 0.0F, -0.0436F);
		rightBone1_r1.texOffs(0, 0).addBox(-1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, 0.0F, false);

		leftBone2_r1 = new ModelRenderer(this);
		leftBone2_r1.setPos(6.5F, -0.5F, 6.5F);
		trunk.addChild(leftBone2_r1);
		setRotationAngle(leftBone2_r1, -0.0436F, 0.0F, 0.0436F);
		leftBone2_r1.texOffs(0, 0).addBox(-1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, 0.0F, false);

		rightBone5_r1 = new ModelRenderer(this);
		rightBone5_r1.setPos(0.0F, 5.0F, 0.0F);
		trunk.addChild(rightBone5_r1);
		setRotationAngle(rightBone5_r1, 0.0F, 0.0F, -0.0436F);
		rightBone5_r1.texOffs(39, 49).addBox(-7.5F, -8.0F, 2.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

		leftBone4_r1 = new ModelRenderer(this);
		leftBone4_r1.setPos(6.5F, -1.0F, 0.0F);
		trunk.addChild(leftBone4_r1);
		setRotationAngle(leftBone4_r1, -0.0873F, 0.0F, -0.0436F);
		leftBone4_r1.texOffs(43, 0).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);

		rightBone3_r1 = new ModelRenderer(this);
		rightBone3_r1.setPos(0.0F, 5.0F, 0.0F);
		trunk.addChild(rightBone3_r1);
		setRotationAngle(rightBone3_r1, -0.2182F, 0.0F, 0.0F);
		rightBone3_r1.texOffs(0, 27).addBox(-7.5F, -10.0F, -4.0F, 2.0F, 10.0F, 2.0F, 0.0F, false);

		leftBone1_r2 = new ModelRenderer(this);
		leftBone1_r2.setPos(6.5F, -0.5F, -6.5F);
		trunk.addChild(leftBone1_r2);
		setRotationAngle(leftBone1_r2, 0.0436F, 0.0873F, 0.0436F);
		leftBone1_r2.texOffs(0, 0).addBox(-1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 2.0F, 0.0F, false);

		skull = new ModelRenderer(this);
		skull.setPos(0.0F, 0.0F, 0.0F);
		trunk.addChild(skull);
		skull.texOffs(26, 46).addBox(-2.5F, -4.0F, -7.5F, 5.0F, 4.0F, 1.0F, 0.0F, false);

		jaw = new ModelRenderer(this);
		jaw.setPos(0.0F, 0.0F, 0.0F);
		trunk.addChild(jaw);
		jaw.texOffs(25, 52).addBox(-1.5F, 0.0F, -7.5F, 3.0F, 2.0F, 1.0F, 0.0F, false);

		lock = new ModelRenderer(this);
		lock.setPos(0.0F, 0.0F, -6.6F);
		trunk.addChild(lock);
		lock.texOffs(52, 6).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void renderAll(MatrixStack matrixStack, IVertexBuilder renderBuffer, int combinedLight, int combinedOverlay, AbstractTreasureChestTileEntity te) {
		BoneChestTileEntity bte = (BoneChestTileEntity) te;

		// renders everthing
		chest.render(matrixStack, renderBuffer, combinedLight, combinedOverlay);		
	}
	
	/**
	 * 
	 * @param modelRenderer
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public ModelRenderer getLid() {
		return lid;
	}

	public ModelRenderer getSkull() {
		return skull;
	}

	public ModelRenderer getJaw() {
		return jaw;
	}

	public ModelRenderer getLock() {
		return this.lock;
	}
}