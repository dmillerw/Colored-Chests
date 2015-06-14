package dmillerw.chestsplus.client.gui;

import dmillerw.chestsplus.inventory.ContainerChest;
import dmillerw.chestsplus.item.ItemDimensionalPocket;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import org.lwjgl.opengl.GL11;

/**
 * @author dmillerw
 */
public class GuiChest extends GuiContainer {

	private byte tier;

	private int xSize;
	private int ySize;

	public GuiChest(EntityPlayer player, IInventory chest, byte tier) {
		super(new ContainerChest(player, chest, tier, ItemDimensionalPocket.GUI_SIZES[tier][0], ItemDimensionalPocket.GUI_SIZES[tier][1]));

		this.tier = tier;
		this.xSize = ItemDimensionalPocket.GUI_SIZES[tier][0];
		this.ySize = ItemDimensionalPocket.GUI_SIZES[tier][1];
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1, 1, 1, 1);

		this.mc.getTextureManager().bindTexture(ItemDimensionalPocket.getGUI(tier));

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}
