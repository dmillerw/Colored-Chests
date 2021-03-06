package dmillerw.cchests.block.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockColoredChest extends ItemBlock {

	public static final String[] dyeColorNames = new String[] {"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
	
	public ItemBlockColoredChest(int id) {
		super(id);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + dyeColorNames[stack.getItemDamage()];
	}
	
}
