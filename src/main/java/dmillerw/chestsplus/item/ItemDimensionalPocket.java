package dmillerw.chestsplus.item;

import dmillerw.chestsplus.ChestsPlus;
import dmillerw.chestsplus.inventory.IInventoryItem;
import dmillerw.chestsplus.inventory.InventoryItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * @author dmillerw
 */
public class ItemDimensionalPocket extends Item implements IInventoryItem {

	public static final String[] GUI_FILES = new String[]{"small", "medium", "large"};

	public static final int[] INVENTORY_SIZES = new int[]{54, 108, 216};

	public static ResourceLocation getGUI(ItemStack stack) {
		return new ResourceLocation("edx:textures/gui/gui_" + GUI_FILES[stack.getItemDamage()] + ".png");
	}

	public ItemDimensionalPocket() {
		super();

		setCreativeTab(ChestsPlus.tab);
		setMaxStackSize(1);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public InventoryItem getInventory(ItemStack stack) {
		return new InventoryItem(stack, INVENTORY_SIZES[stack.getItemDamage()]);
	}

}
