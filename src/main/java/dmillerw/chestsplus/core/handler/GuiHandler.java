package dmillerw.chestsplus.core.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import dmillerw.chestsplus.client.gui.GuiChest;
import dmillerw.chestsplus.inventory.ContainerChest;
import dmillerw.chestsplus.item.ItemDimensionalPocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerChest(player, (IInventory) world.getTileEntity(x, y, z), (byte) ID, ItemDimensionalPocket.GUI_SIZES[ID][0], ItemDimensionalPocket.GUI_SIZES[ID][1]);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GuiChest(player, (IInventory) world.getTileEntity(x, y, z), (byte) ID);
	}

}
