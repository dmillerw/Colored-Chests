package dmillerw.chestsplus.block.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockColoredChest extends ItemBlock {

	public static final String[] dyeColorNames = new String[]{"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};

	public ItemBlockColoredChest(Block block) {
		super(block);

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
