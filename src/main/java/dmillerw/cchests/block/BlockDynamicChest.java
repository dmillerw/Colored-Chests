package dmillerw.cchests.block;

import dmillerw.cchests.block.tile.TileDynamicChest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class BlockDynamicChest extends BlockChest {

	public BlockDynamicChest() {
		super();
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(this, 1, 0));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileDynamicChest();
	}

}
