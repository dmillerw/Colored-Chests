package dmillerw.chestsplus.item;

import dmillerw.chestsplus.ChestsPlus;
import dmillerw.chestsplus.lib.ModInfo;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

/**
 * @author dmillerw
 */
public class ItemKey extends Item {

	private IIcon icon;

	public ItemKey() {
		super();

		setCreativeTab(ChestsPlus.tab);
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		return icon;
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		icon = iconRegister.registerIcon(ModInfo.RESOURCE_PREFIX + "key");
	}

}
