package dmillerw.chestsplus.inventory;

import net.minecraft.item.ItemStack;

/**
 * @author dmillerw
 */
public interface IInventoryItem {

	public InventoryItem getInventory(ItemStack stack);

}
