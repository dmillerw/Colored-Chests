package dmillerw.chestsplus.block;

import dmillerw.chestsplus.block.tile.TileDynamicChest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
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
	public IIcon getIcon(int side, int meta) {
		return Blocks.planks.getIcon(side, meta);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileDynamicChest();
	}

}
