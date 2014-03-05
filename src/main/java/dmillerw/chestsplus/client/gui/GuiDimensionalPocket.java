package dmillerw.chestsplus.client.gui;

import dmillerw.chestsplus.inventory.ContainerDimensionalPocket;
import dmillerw.chestsplus.item.ItemDimensionalPocket;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

/**
 * @author dmillerw
 */
public class GuiDimensionalPocket extends GuiContainer {

	private final String label;

	private final ItemStack item;

	public GuiDimensionalPocket(String label, ItemStack item) {
		super(new ContainerDimensionalPocket(item));

		this.label = label;
		this.item = item;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partial, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(ItemDimensionalPocket.getGUI(item));
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

}
