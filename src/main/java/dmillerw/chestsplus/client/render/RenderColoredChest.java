package dmillerw.chestsplus.client.render;

import dmillerw.chestsplus.block.tile.TileColoredChest;
import dmillerw.chestsplus.lib.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RenderColoredChest extends TileEntitySpecialRenderer implements IItemRenderer {

	private static final ResourceLocation RES_NORMAL_SINGLE = new ResourceLocation(ModInfo.RESOURCE_PREFIX + "textures/normal.png");

	private ModelChest chestModel = new ModelChest();

	public void renderColoredChestAt(TileColoredChest tile, double x, double y, double z, float partial) {
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

		Minecraft.getMinecraft().renderEngine.bindTexture(RES_NORMAL_SINGLE);

		this.chestModel.chestLid.rotateAngleX = -(f1 * (float) Math.PI / 2.0F);

		GL11.glColor3f(EntitySheep.fleeceColorTable[meta][0], EntitySheep.fleeceColorTable[meta][1], EntitySheep.fleeceColorTable[meta][2]);

		this.chestModel.chestKnob.rotateAngleX = this.chestModel.chestLid.rotateAngleX;
		this.chestModel.chestLid.render(0.0625F);
		this.chestModel.chestBelow.render(0.0625F);

		GL11.glColor4f(1, 1, 1, 1);

		this.chestModel.chestKnob.render(0.0625F);

		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		renderColoredChestAt((TileColoredChest) tileentity, d0, d1, d2, f);
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

		TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileColoredChest(item.getItemDamage()), 0, 0, 0, 0);

		GL11.glPopMatrix();
	}

}
