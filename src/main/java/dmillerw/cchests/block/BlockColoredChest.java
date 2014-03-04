package dmillerw.cchests.block;

import dmillerw.cchests.block.tile.TileColoredChest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class BlockColoredChest extends BlockChest {

	public BlockColoredChest() {
		super();
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 16; i++) {
			list.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return Blocks.planks.getIcon(side, meta);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileColoredChest();
	}


}
