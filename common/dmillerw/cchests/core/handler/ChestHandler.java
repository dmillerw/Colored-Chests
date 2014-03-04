package dmillerw.cchests.core.handler;

import dmillerw.cchests.ColoredChests;
import dmillerw.cchests.block.tile.TileChest;
import dmillerw.cchests.block.tile.TileColoredChest;
import dmillerw.cchests.block.tile.TileDynamicChest;
import dmillerw.cchests.util.UtilInventory;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ChestHandler {

	public static final ForgeDirection[] SIDE_DIRS = new ForgeDirection[] {
		ForgeDirection.EAST,
		ForgeDirection.NORTH,
		ForgeDirection.WEST,
		ForgeDirection.SOUTH
	};
	
	public static boolean vanilla(World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		int chestCount = 0;
		for (ForgeDirection dir : SIDE_DIRS) {
			if (world.getBlockId(x + dir.offsetX, y, z + dir.offsetZ) == Block.chest.blockID) {
				chestCount++;
				
				TileEntityChest tileChest = (TileEntityChest) world.getBlockTileEntity(x + dir.offsetX, y, z + dir.offsetZ);
				
				if (tileChest.adjacentChestXNeg != null || tileChest.adjacentChestXPos != null || tileChest.adjacentChestZNeg != null || tileChest.adjacentChestZPosition != null) {
					chestCount++;
				}
			}
		}
		if (chestCount > 1) {
			return false;
		}
		
		if (tile != null && tile instanceof TileChest) {
			tile.invalidate();
			int meta = ((TileChest)tile).orientation.ordinal();
			world.setBlockToAir(x, y, z);
			world.setBlock(x, y, z, Block.chest.blockID, meta, 3);
			TileEntityChest newTile = new TileEntityChest();
			NBTTagCompound tag = new NBTTagCompound();
			tile.writeToNBT(tag);
			newTile.readFromNBT(tag);
			world.setBlockTileEntity(x, y, z, newTile);
			
			return true;
		}
		
		return false;
	}
	
	public static void dye(World world, int x, int y, int z, int dye) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if (tile != null && tile instanceof TileEntityChest) {
			tile.invalidate();
			int meta = world.getBlockMetadata(x, y, z);
			world.setBlockToAir(x, y, z);
			world.setBlock(x, y, z, ColoredChests.instance.blockColoredChestID, dye, 3);
			TileColoredChest newTile = new TileColoredChest();
			NBTTagCompound tag = new NBTTagCompound();
			tile.writeToNBT(tag);
			newTile.readFromNBT(tag);
			newTile.orientation = ForgeDirection.getOrientation(meta);
			world.setBlockTileEntity(x, y, z, newTile);
		} else if (tile != null && tile instanceof TileColoredChest) {
			world.setBlockMetadataWithNotify(x, y, z, dye, 3);
		}
	}
	
	public static void mimick(World world, int x, int y, int z, ItemStack stack) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if (tile != null && tile instanceof TileEntityChest) {
			tile.invalidate();
			int meta = world.getBlockMetadata(x, y, z);
			world.setBlockToAir(x, y, z);
			world.setBlock(x, y, z, ColoredChests.instance.blockDynamicChestID, 0, 3);
			TileDynamicChest newTile = new TileDynamicChest();
			NBTTagCompound tag = new NBTTagCompound();
			tile.writeToNBT(tag);
			newTile.readFromNBT(tag);
			newTile.orientation = ForgeDirection.getOrientation(meta);
			newTile.mimickStack = stack;
			world.setBlockTileEntity(x, y, z, newTile);
			world.markBlockForUpdate(x, y, z);
		} else if (tile != null && tile instanceof TileDynamicChest) {
			((TileDynamicChest)tile).mimickStack = stack;
			world.markBlockForUpdate(x, y, z);
		}
	}
	
	public static ItemStack getDynamicChest(ItemStack mimick) {
		ItemStack chest = new ItemStack(ColoredChests.instance.blockDynamicChest);
		NBTTagCompound mimTag = new NBTTagCompound();
		mimick.writeToNBT(mimTag);
		if (!chest.hasTagCompound()) {
			chest.setTagCompound(new NBTTagCompound());
		}
		chest.getTagCompound().setCompoundTag("mimick", mimTag);
		return chest;
	}
	
}
