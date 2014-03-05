package dmillerw.chestsplus.client.render;

import dmillerw.chestsplus.block.tile.TileDynamicChest;
import dmillerw.chestsplus.lib.ModInfo;
import dmillerw.chestsplus.util.UtilItem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RenderDynamicChest extends TileEntitySpecialRenderer implements IItemRenderer {

	public static void setGLColor(int color) {
		float r = (float) (color >> 16 & 255) / 255.0F;
		float g = (float) (color >> 8 & 255) / 255.0F;
		float b = (float) (color & 255) / 255.0F;
		GL11.glColor4f(r, g, b, 1.0F);
	}

	private static final ResourceLocation RES_NORMAL_SINGLE = new ResourceLocation(ModInfo.RESOURCE_PREFIX + "textures/normal_trans.png");

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
		GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
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

		GL11.glRotatef((float) short1, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		float f1 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * partial;
		float f2;
		f1 = 1.0F - f1;
		f1 = 1.0F - f1 * f1 * f1;


		Tessellator t = Tessellator.instance;

		Block mimick = tile.mimickStack != null ? UtilItem.getBlock(tile.mimickStack) : Blocks.stone;
		int mimickMeta = tile.mimickStack != null ? tile.mimickStack.getItemDamage() : 0;

		this.chestModel.chestLid.rotateAngleX = -(f1 * (float) Math.PI / 2.0F);
		this.chestModel.chestKnob.rotateAngleX = this.chestModel.chestLid.rotateAngleX;

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

		if (tile.hasWorldObj()) {
			int renderColor = mimick.getRenderColor(mimickMeta);

			if (mimick.getRenderBlockPass() != 1) {
				setGLColor(renderColor);
			}

			// Transparency and handling
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		}

		renderLidOverlay(tile, t, mimick, mimickMeta);
		renderBodyOverlay(tile, t, mimick, mimickMeta);

		if (tile.hasWorldObj()) {
			GL11.glDisable(GL11.GL_BLEND);

			GL11.glColor4f(1, 1, 1, 1);
		}

		Minecraft.getMinecraft().renderEngine.bindTexture(RES_NORMAL_SINGLE);

		this.chestModel.chestLid.render(0.0625F);
		this.chestModel.chestBelow.render(0.0625F);
		this.chestModel.chestKnob.render(0.0625F);

		GL11.glPopMatrix();
	}

	private float interpolatedCoords(int pos) {
		return (float) (0.0625 * pos);
	}

	private void renderLidOverlay(TileDynamicChest tile, Tessellator t, Block block, int meta) {
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

		 /* LID TOP */
		t.startDrawingQuads();

		t.setNormal(0, -1, 0);

		IIcon icon = block.getIcon(1, meta);

		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(2), interpolatedCoords(2), icon.getInterpolatedU(2), icon.getInterpolatedV(2));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(2), interpolatedCoords(2), icon.getInterpolatedU(14), icon.getInterpolatedV(2));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(2), interpolatedCoords(14), icon.getInterpolatedU(14), icon.getInterpolatedV(14));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(2), interpolatedCoords(14), icon.getInterpolatedU(2), icon.getInterpolatedV(14));
		t.draw();

		// Fix for grass rendering color oddly
		if (block == Blocks.grass) {
			GL11.glColor4f(1, 1, 1, 1);
		}

		/* LID FRONT */
		t.startDrawingQuads();

		t.setNormal(0, 0, -1);

		icon = block.getIcon(2, meta);

		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(6), interpolatedCoords(1), icon.getInterpolatedU(2), icon.getInterpolatedV(6));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(6), interpolatedCoords(1), icon.getInterpolatedU(14), icon.getInterpolatedV(6));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(3), interpolatedCoords(1), icon.getInterpolatedU(14), icon.getInterpolatedV(3));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(3), interpolatedCoords(1), icon.getInterpolatedU(2), icon.getInterpolatedV(3));
		t.draw();

        /* LID RIGHT */
		t.startDrawingQuads();

		t.setNormal(1, 0, 0);

		icon = block.getIcon(5, meta);

		t.addVertexWithUV(interpolatedCoords(15), interpolatedCoords(6), interpolatedCoords(2), icon.getInterpolatedU(2), icon.getInterpolatedV(6));
		t.addVertexWithUV(interpolatedCoords(15), interpolatedCoords(6), interpolatedCoords(14), icon.getInterpolatedU(14), icon.getInterpolatedV(6));
		t.addVertexWithUV(interpolatedCoords(15), interpolatedCoords(3), interpolatedCoords(14), icon.getInterpolatedU(14), icon.getInterpolatedV(3));
		t.addVertexWithUV(interpolatedCoords(15), interpolatedCoords(3), interpolatedCoords(2), icon.getInterpolatedU(2), icon.getInterpolatedV(3));
		t.draw();
        
        /* LID LEFT */
		t.startDrawingQuads();

		t.setNormal(-1, 0, 0);

		icon = block.getIcon(4, meta);

		t.addVertexWithUV(interpolatedCoords(1), interpolatedCoords(6), interpolatedCoords(14), icon.getInterpolatedU(2), icon.getInterpolatedV(6));
		t.addVertexWithUV(interpolatedCoords(1), interpolatedCoords(6), interpolatedCoords(2), icon.getInterpolatedU(14), icon.getInterpolatedV(6));
		t.addVertexWithUV(interpolatedCoords(1), interpolatedCoords(3), interpolatedCoords(2), icon.getInterpolatedU(14), icon.getInterpolatedV(3));
		t.addVertexWithUV(interpolatedCoords(1), interpolatedCoords(3), interpolatedCoords(14), icon.getInterpolatedU(2), icon.getInterpolatedV(3));
		t.draw();
        
        /* LID BACK */
		t.startDrawingQuads();

		t.setNormal(0, 0, 1);

		icon = block.getIcon(3, meta);

		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(6), interpolatedCoords(15), icon.getInterpolatedU(2), icon.getInterpolatedV(6));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(6), interpolatedCoords(15), icon.getInterpolatedU(14), icon.getInterpolatedV(6));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(3), interpolatedCoords(15), icon.getInterpolatedU(14), icon.getInterpolatedV(3));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(3), interpolatedCoords(15), icon.getInterpolatedU(2), icon.getInterpolatedV(3));
		t.draw();
        
        /* LID BOTTOM */
		t.startDrawingQuads();

		t.setNormal(0, 1, 0);

		icon = block.getIcon(0, meta);

		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(7), interpolatedCoords(2), icon.getInterpolatedU(2), icon.getInterpolatedV(2));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(7), interpolatedCoords(2), icon.getInterpolatedU(14), icon.getInterpolatedV(2));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(7), interpolatedCoords(14), icon.getInterpolatedU(14), icon.getInterpolatedV(14));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(7), interpolatedCoords(14), icon.getInterpolatedU(2), icon.getInterpolatedV(14));
		t.draw();

		GL11.glPopMatrix();
	}

	private void renderBodyOverlay(TileDynamicChest tile, Tessellator t, Block block, int meta) {
		/* BODY FRONT */
		t.startDrawingQuads();

		t.setNormal(0, 0, -1);

		IIcon icon = block.getIcon(2, meta);

		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(15), interpolatedCoords(1), icon.getInterpolatedU(2), icon.getInterpolatedV(15));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(15), interpolatedCoords(1), icon.getInterpolatedU(14), icon.getInterpolatedV(15));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(7), interpolatedCoords(1), icon.getInterpolatedU(14), icon.getInterpolatedV(7));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(7), interpolatedCoords(1), icon.getInterpolatedU(2), icon.getInterpolatedV(7));
		t.draw();
        
        /* BODY RIGHT */
		t.startDrawingQuads();

		t.setNormal(1, 0, 0);

		icon = block.getIcon(5, meta);

		t.addVertexWithUV(interpolatedCoords(15), interpolatedCoords(15), interpolatedCoords(2), icon.getInterpolatedU(2), icon.getInterpolatedV(15));
		t.addVertexWithUV(interpolatedCoords(15), interpolatedCoords(15), interpolatedCoords(14), icon.getInterpolatedU(14), icon.getInterpolatedV(15));
		t.addVertexWithUV(interpolatedCoords(15), interpolatedCoords(7), interpolatedCoords(14), icon.getInterpolatedU(14), icon.getInterpolatedV(7));
		t.addVertexWithUV(interpolatedCoords(15), interpolatedCoords(7), interpolatedCoords(2), icon.getInterpolatedU(2), icon.getInterpolatedV(7));
		t.draw();
        
        /* BODY LEFT */
		t.startDrawingQuads();

		t.setNormal(-1, 0, 0);

		icon = block.getIcon(4, meta);

		t.addVertexWithUV(interpolatedCoords(1), interpolatedCoords(15), interpolatedCoords(14), icon.getInterpolatedU(2), icon.getInterpolatedV(15));
		t.addVertexWithUV(interpolatedCoords(1), interpolatedCoords(15), interpolatedCoords(2), icon.getInterpolatedU(14), icon.getInterpolatedV(15));
		t.addVertexWithUV(interpolatedCoords(1), interpolatedCoords(7), interpolatedCoords(2), icon.getInterpolatedU(14), icon.getInterpolatedV(7));
		t.addVertexWithUV(interpolatedCoords(1), interpolatedCoords(7), interpolatedCoords(14), icon.getInterpolatedU(2), icon.getInterpolatedV(7));
		t.draw();
        
        /* BODY BACK */
		t.startDrawingQuads();

		t.setNormal(0, 0, 1);

		icon = block.getIcon(3, meta);

		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(15), interpolatedCoords(15), icon.getInterpolatedU(2), icon.getInterpolatedV(15));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(15), interpolatedCoords(15), icon.getInterpolatedU(14), icon.getInterpolatedV(15));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(7), interpolatedCoords(15), icon.getInterpolatedU(14), icon.getInterpolatedV(7));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(7), interpolatedCoords(15), icon.getInterpolatedU(2), icon.getInterpolatedV(7));
		t.draw();
        
        /* BODY TOP */
		t.startDrawingQuads();

		t.setNormal(0, -1, 0);

		icon = block.getIcon(0, meta);

		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(6), interpolatedCoords(2), icon.getInterpolatedU(2), icon.getInterpolatedV(2));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(6), interpolatedCoords(2), icon.getInterpolatedU(14), icon.getInterpolatedV(2));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(6), interpolatedCoords(14), icon.getInterpolatedU(14), icon.getInterpolatedV(14));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(6), interpolatedCoords(14), icon.getInterpolatedU(2), icon.getInterpolatedV(14));
		t.draw();

        /* BODY BOTTOM */
		t.startDrawingQuads();

		t.setNormal(0, 1, 0);

		icon = block.getIcon(0, meta);

		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(16), interpolatedCoords(2), icon.getInterpolatedU(2), icon.getInterpolatedV(2));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(16), interpolatedCoords(2), icon.getInterpolatedU(14), icon.getInterpolatedV(2));
		t.addVertexWithUV(interpolatedCoords(2), interpolatedCoords(16), interpolatedCoords(14), icon.getInterpolatedU(14), icon.getInterpolatedV(14));
		t.addVertexWithUV(interpolatedCoords(14), interpolatedCoords(16), interpolatedCoords(14), icon.getInterpolatedU(2), icon.getInterpolatedV(14));
		t.draw();
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
