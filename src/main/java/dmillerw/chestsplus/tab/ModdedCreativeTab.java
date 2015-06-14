package dmillerw.chestsplus.tab;

import dmillerw.chestsplus.ChestsPlus;
import dmillerw.chestsplus.util.UtilItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * @author dmillerw
 */
public class ModdedCreativeTab extends CreativeTabs {

	public ModdedCreativeTab(String lable) {
		super(lable);
	}

	@Override
	public Item getTabIconItem() {
		return UtilItem.getItem(ChestsPlus.instance.blockColoredChest);
	}

}
