package dmillerw.cchests.block;

import java.util.List;

import dmillerw.cchests.block.tile.TileDynamicChest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockDynamicChest extends BlockChest {

	public BlockDynamicChest(int id) {
		super(id);
	}

	@Override
	public void getSubBlocks(int id, CreativeTabs tab, List list) {
		list.add(new ItemStack(this, 1, 0));
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileDynamicChest();
	}

}
