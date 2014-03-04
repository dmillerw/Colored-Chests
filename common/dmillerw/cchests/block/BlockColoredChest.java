package dmillerw.cchests.block;

import java.util.List;

import dmillerw.cchests.block.tile.TileColoredChest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockColoredChest extends BlockChest {

	public BlockColoredChest(int id) {
		super(id);
	}

	@Override
	public void getSubBlocks(int id, CreativeTabs tab, List list) {
		for (int i=0; i<16; i++) {
			list.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileColoredChest();
	}
    
	
}
