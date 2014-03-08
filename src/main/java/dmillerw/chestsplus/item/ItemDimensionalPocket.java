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

	public static final int[][] GUI_SIZES = new int[][]{
			{184, 202},
			{238, 256}
	};

	public static final int[] INVENTORY_SIZES = new int[]{54, 108, 216};

	public static ResourceLocation getGUI(byte tier) {
		return new ResourceLocation("edx:textures/gui/gui_" + GUI_FILES[tier] + ".png");
	}

	public static int getSize(byte tier) {
		return tier == -1 ? 27 : INVENTORY_SIZES[tier];
	}

	public static int getRows(byte tier) {
		return getColumns(tier) / getSize(tier);
	}

	public static int getColumns(byte tier) {
		switch (tier) {
			case 0:
			case 1:
				return 9;
			case 2:
				return 12;
			default:
				return 9;
		}
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
