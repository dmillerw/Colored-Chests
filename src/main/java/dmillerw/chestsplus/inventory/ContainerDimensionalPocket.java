package dmillerw.chestsplus.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

/**
 * @author dmillerw
 */
public class ContainerDimensionalPocket extends Container {

	private final ItemStack item;

	public ContainerDimensionalPocket(ItemStack item) {
		this.item = item;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

}
