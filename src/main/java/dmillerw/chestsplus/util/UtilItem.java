package dmillerw.chestsplus.util;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * @author dmillerw
 */
public class UtilItem {

	/**
	 * Returns the internal ID used for Item/Block reference
	 */
	public static int getID(ItemStack stack) {
		return getID(stack.getItem());
	}

	public static int getID(Block block) {
		return GameData.blockRegistry.getId(block);
	}

	public static int getID(Item item) {
		return GameData.itemRegistry.getId(item);
	}

	public static Block getBlock(ItemStack stack) {
		return getBlock(stack.getItem());
	}

	public static Block getBlock(Item item) {
		if (!(item instanceof ItemBlock)) {
			return null;
		}

		return getBlock(getID(item));
	}

	public static Block getBlock(int id) {
		return GameData.blockRegistry.getObjectById(id);
	}

	public static Item getItem(int id) {
		return GameData.itemRegistry.getObjectById(id);
	}

	public static Item getItem(Block block) {
		return getItem(getID(block));
	}

	public static boolean isBlock(ItemStack stack, Block block) {
		return getID(stack) == getID(block);
	}

	public static ItemStack convertToItemStack(Object object) {
		if (object instanceof ItemStack) {
			ItemStack itemStack = (ItemStack) object;
			if (itemStack.getItemDamage() < 0) {
				itemStack.setItemDamage(0);
			}
			return itemStack;
		}

		if (object instanceof Block) {
			return new ItemStack(Block.getBlockById(UtilItem.getID((Block) object)), 1, -1);
		}

		if (object instanceof Item) {
			return new ItemStack(Item.getItemById(UtilItem.getID((Item) object)), 1, -1);
		}
		return null;
	}

}
