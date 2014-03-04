package dmillerw.cchests.client.render;

import dmillerw.cchests.ColoredChests;
import dmillerw.cchests.block.tile.TileDynamicChest;
import dmillerw.cchests.lib.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RenderDynamicChest extends TileEntitySpecialRenderer implements IItemRenderer {

	private static final ResourceLocation RES_NORMAL_SINGLE = new ResourceLocation(ModInfo.RESOURCE_PREFIX + "textures/normalTrans.png");
	
	private ModelChest chestModel = new ModelChest();

	public void renderDynamicChestAt(TileDynamicChest tile, double x, double y, double z, float partial) {
		int meta = tile.getBlockMetadata();
		int i;

		if (!tile.hasWorldObj()) {
			i = 5;
		} else {
            if (tile.orientation != null) {
            	i = tile.orientation.ordinal();
            } else {
            	i = 0;
            }
		}

        GL11.glPushMatrix();
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float)x, (float)y + 1.0F, (float)z + 1.0F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        short short1 = 0;

		if (i == 2) {
			short1 = 180;
		}
		if (i == 3) {
			short1 = 0;
		}
		if (i == 4) {
			short1 = 90;
		}
		if (i == 5) {
			short1 = -90;
		}

        GL11.glRotatef((float)short1, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        float f1 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * partial;
        float f2;
        f1 = 1.0F - f1;
        f1 = 1.0F - f1 * f1 * f1;

		Minecraft.getMinecraft().renderEngine.bindTexture(RES_NORMAL_SINGLE);

		this.chestModel.chestLid.rotateAngleX = -(f1 * (float)Math.PI / 2.0F);
        
        this.chestModel.chestKnob.rotateAngleX = this.chestModel.chestLid.rotateAngleX;
        this.chestModel.chestLid.render(0.0625F);
        this.chestModel.chestBelow.render(0.0625F);
        
        this.chestModel.chestKnob.render(0.0625F);
        
        GL11.glPopMatrix();
	}
	
	private float interpolatedCoords(int pos) {
		return (float) (0.0625 * pos);
	}

	private void renderLidOverlay(TileDynamicChest tile, Tessellator t, IIcon IIcon) {
		GL11.glPushMatrix();
		
		GL11.glTranslatef(this.chestModel.chestLid.rotationPointX * 0.0625F, this.chestModel.chestLid.rotationPointY * 0.0625F, this.chestModel.chestLid.rotationPointZ * 0.0625F);
		if (this.chestModel.chestLid.rotateAngleZ != 0.0F) {
			GL11.glRotatef(this.chestModel.chestLid.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
		}
		if (this.chestModel.chestLid.rotateAngleY != 0.0F) {
			GL11.glRotatef(this.chestModel.chestLid.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
		}
		if (this.chestModel.chestLid.rotateAngleX != 0.0F) {
			GL11.glRotatef(this.chestModel.chestLid.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
		}
		GL11.glTranslatef(-this.chestModel.chestLid.rotationPointX * 0.0625F, -this.chestModel.chestLid.rotationPointY * 0.0625F, -this.chestModel.chestLid.rotationPointZ * 0.0625F);
        
		/* LID FRONT */
        t.startDrawingQuads();
        if (tile.hasWorldObj()) {
			setBrightness(tile.getWorldObj(), tile.xCoord - 1, tile.yCoord, tile.zCoord, ColoredChests.instance.blockColoredChest);
		}
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(6), interpolatedCoords(1), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(6));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(6), interpolatedCoords(1), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(6));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(3), interpolatedCoords(1), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(3));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(3), interpolatedCoords(1), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(3));
		t.draw();
        
        /* LID RIGHT */
        t.startDrawingQuads();
        if (tile.hasWorldObj()) {
			setBrightness(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, ColoredChests.instance.blockColoredChest);
		}
		t.addVertexWithUV(interpolatedCoords(15), interpolatedCoords(6), interpolatedCoords(2), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(6));
		t.addVertexWithUV(interpolatedCoords(15), interpolatedCoords(6), interpolatedCoords(14), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(6));
		t.addVertexWithUV(interpolatedCoords(15), interpolatedCoords(3), interpolatedCoords(14), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(3));
		t.addVertexWithUV(interpolatedCoords(15), interpolatedCoords(3), interpolatedCoords(2), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(3));
		t.draw();
        
        /* LID LEFT */
        t.startDrawingQuads();
        if (tile.hasWorldObj()) {
			setBrightness(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, ColoredChests.instance.blockColoredChest);
		}
		t.addVertexWithUV(interpolatedCoords(1), interpolatedCoords(6), interpolatedCoords(14), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(6));
		t.addVertexWithUV(interpolatedCoords(1), interpolatedCoords(6), interpolatedCoords(2), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(6));
		t.addVertexWithUV(interpolatedCoords(1), interpolatedCoords(3), interpolatedCoords(2), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(3));
		t.addVertexWithUV(interpolatedCoords(1), interpolatedCoords(3), interpolatedCoords(14), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(3));
		t.draw();
        
        /* LID BACK */
        t.startDrawingQuads();
        if (tile.hasWorldObj()) {
			setBrightness(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, ColoredChests.instance.blockColoredChest);
		}
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(6), interpolatedCoords(15), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(6));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(6), interpolatedCoords(15), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(6));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(3), interpolatedCoords(15), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(3));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(3), interpolatedCoords(15), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(3));
		t.draw();
        
        /* LID TOP */
        t.startDrawingQuads();
        if (tile.hasWorldObj()) {
			setBrightness(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, ColoredChests.instance.blockColoredChest);
		}
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(2), interpolatedCoords(2), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(2));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(2), interpolatedCoords(2), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(2));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(2), interpolatedCoords(14), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(14));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(2), interpolatedCoords(14), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(14));
		t.draw();
        
        /* LID BOTTOM */
        t.startDrawingQuads();
        if (tile.hasWorldObj()) {
			setBrightness(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, ColoredChests.instance.blockColoredChest);
		}
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(7), interpolatedCoords(2), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(2));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(7), interpolatedCoords(2), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(2));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(7), interpolatedCoords(14), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(14));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(7), interpolatedCoords(14), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(14));
		t.draw();
        
        GL11.glPopMatrix();
	}

	private void renderBodyOverlay(TileDynamicChest tile, Tessellator t, IIcon IIcon) {
		/* BODY FRONT */
        t.startDrawingQuads();
        if (tile.hasWorldObj()) {
			setBrightness(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, ColoredChests.instance.blockColoredChest);
		}
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(15), interpolatedCoords(1), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(15));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(15), interpolatedCoords(1), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(15));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(7), interpolatedCoords(1), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(7));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(7), interpolatedCoords(1), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(7));
		t.draw();
        
        /* BODY RIGHT */
        t.startDrawingQuads();
        if (tile.hasWorldObj()) {
			setBrightness(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, ColoredChests.instance.blockColoredChest);
		}
		t.addVertexWithUV(interpolatedCoords(15), interpolatedCoords(15), interpolatedCoords(2), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(15));
		t.addVertexWithUV(interpolatedCoords(15), interpolatedCoords(15), interpolatedCoords(14), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(15));
		t.addVertexWithUV(interpolatedCoords(15), interpolatedCoords(7), interpolatedCoords(14), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(7));
		t.addVertexWithUV(interpolatedCoords(15), interpolatedCoords(7), interpolatedCoords(2), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(7));
		t.draw();
        
        /* BODY LEFT */
        t.startDrawingQuads();
        if (tile.hasWorldObj()) {
			setBrightness(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, ColoredChests.instance.blockColoredChest);
		}
		t.addVertexWithUV(interpolatedCoords(1), interpolatedCoords(15), interpolatedCoords(14), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(15));
		t.addVertexWithUV(interpolatedCoords(1), interpolatedCoords(15), interpolatedCoords(2), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(15));
		t.addVertexWithUV(interpolatedCoords(1), interpolatedCoords(7), interpolatedCoords(2), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(7));
		t.addVertexWithUV(interpolatedCoords(1), interpolatedCoords(7), interpolatedCoords(14), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(7));
		t.draw();
        
        /* BODY BACK */
        t.startDrawingQuads();
        if (tile.hasWorldObj()) {
			setBrightness(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, ColoredChests.instance.blockColoredChest);
		}
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(15), interpolatedCoords(15), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(15));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(15), interpolatedCoords(15), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(15));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(7), interpolatedCoords(15), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(7));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(7), interpolatedCoords(15), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(7));
		t.draw();
        
        /* BODY TOP */
        t.startDrawingQuads();
        if (tile.hasWorldObj()) {
			setBrightness(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, ColoredChests.instance.blockColoredChest);
		}
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(6), interpolatedCoords(2), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(2));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(6), interpolatedCoords(2), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(2));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(6), interpolatedCoords(14), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(14));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(6), interpolatedCoords(14), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(14));
		t.draw();
        
        /* BODY BOTTOM */
        t.startDrawingQuads();
        if (tile.hasWorldObj()) {
			setBrightness(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, ColoredChests.instance.blockColoredChest);
		}
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(16), interpolatedCoords(2), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(2));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(16), interpolatedCoords(2), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(2));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(16), interpolatedCoords(14), IIcon.getInterpolatedU(14), IIcon.getInterpolatedV(14));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(16), interpolatedCoords(14), IIcon.getInterpolatedU(2), IIcon.getInterpolatedV(14));
		t.draw();
	}
	
	private static void setBrightness(IBlockAccess blockAccess, int i, int j, int k, Block block) {
		Tessellator tessellator = Tessellator.instance;
		int mb = block.getMixedBrightnessForBlock(blockAccess, i, j, k);
		tessellator.setBrightness(mb);

		float f = 1.0F;

		int l = block.colorMultiplier(blockAccess, i, j, k);
		float f1 = (l >> 16 & 0xFF) / 255.0F;
		float f2 = (l >> 8 & 0xFF) / 255.0F;
		float f3 = (l & 0xFF) / 255.0F;
		if (EntityRenderer.anaglyphEnable) {
			float f6 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
			float f4 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
			float f7 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
			f1 = f6;
			f2 = f4;
			f3 = f7;
		}
		tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		renderDynamicChestAt((TileDynamicChest) tileentity, d0, d1, d2, f);
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		
		if (type == ItemRenderType.ENTITY) {
			GL11.glTranslated(-.5, -.5, -.5);
		}

		TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileDynamicChest(), 0, 0, 0, 0);

		GL11.glPopMatrix();
	}

}
