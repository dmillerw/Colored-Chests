package dmillerw.chestsplus.core.handler;

import dmillerw.chestsplus.ChestsPlus;
import dmillerw.chestsplus.block.tile.TileChest;
import dmillerw.chestsplus.block.tile.TileColoredChest;
import dmillerw.chestsplus.block.tile.TileDynamicChest;
import dmillerw.chestsplus.item.ItemDimensionalPocket;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ChestHandler {

	public static int getSize(byte tier) {
		return tier == -1 ? 27 : ItemDimensionalPocket.INVENTORY_SIZES[tier];
	}

	public static int getRows(byte tier) {
		return getColumns(tier) / getSize(tier);
	}

	public static int getColumns(byte tier) {
		switch (tier) {
			case 0:
			case 1:
				return 9;
			case 2:
				return 12;
			default:
				return 9;
		}
	}

	public static final ForgeDirection[] SIDE_DIRS = new ForgeDirection[]{
			ForgeDirection.EAST,
			ForgeDirection.NORTH,
			ForgeDirection.WEST,
			ForgeDirection.SOUTH
	};

	public static boolean vanilla(World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);

		int chestCount = 0;
		for (ForgeDirection dir : SIDE_DIRS) {
			if (world.getBlock(x + dir.offsetX, y, z + dir.offsetZ) == Blocks.chest) {
				chestCount++;

				TileEntityChest tileChest = (TileEntityChest) world.getTileEntity(x + dir.offsetX, y, z + dir.offsetZ);

				if (tileChest.adjacentChestXNeg != null || tileChest.adjacentChestXPos != null || tileChest.adjacentChestZNeg != null || tileChest.adjacentChestZPos != null) {
					chestCount++;
				}
			}
		}
		if (chestCount > 1) {
			return false;
		}

		if (tile != null && tile instanceof TileChest) {
			tile.invalidate();
			int meta = ((TileChest) tile).orientation.ordinal();
			world.setBlockToAir(x, y, z);
			world.setBlock(x, y, z, Blocks.chest, meta, 3);
			TileEntityChest newTile = new TileEntityChest();
			NBTTagCompound tag = new NBTTagCompound();
			tile.writeToNBT(tag);
			newTile.readFromNBT(tag);
			world.setTileEntity(x, y, z, newTile);

			return true;
		}

		return false;
	}

	public static void dye(World world, int x, int y, int z, int dye) {
		TileEntity tile = world.getTileEntity(x, y, z);

		if (tile != null && tile instanceof TileEntityChest) {
			tile.invalidate();
			int meta = world.getBlockMetadata(x, y, z);
			world.setBlockToAir(x, y, z);
			world.setBlock(x, y, z, ChestsPlus.instance.blockColoredChest, dye, 3);
			TileColoredChest newTile = new TileColoredChest();
			NBTTagCompound tag = new NBTTagCompound();
			tile.writeToNBT(tag);
			newTile.readFromNBT(tag);
			newTile.orientation = ForgeDirection.getOrientation(meta);
			world.setTileEntity(x, y, z, newTile);
		} else if (tile != null && tile instanceof TileColoredChest) {
			world.setBlockMetadataWithNotify(x, y, z, dye, 3);
		}
	}

	public static void mimick(World world, int x, int y, int z, ItemStack stack) {
		TileEntity tile = world.getTileEntity(x, y, z);

		if (tile != null && tile instanceof TileEntityChest) {
			tile.invalidate();
			int meta = world.getBlockMetadata(x, y, z);
			world.setBlockToAir(x, y, z);
			world.setBlock(x, y, z, ChestsPlus.instance.blockDynamicChest, 0, 3);
			TileDynamicChest newTile = new TileDynamicChest();
			NBTTagCompound tag = new NBTTagCompound();
			tile.writeToNBT(tag);
			newTile.readFromNBT(tag);
			newTile.orientation = ForgeDirection.getOrientation(meta);
			newTile.mimickStack = stack;
			world.setTileEntity(x, y, z, newTile);
			world.markBlockForUpdate(x, y, z);
		} else if (tile != null && tile instanceof TileDynamicChest) {
			((TileDynamicChest) tile).mimickStack = stack;
			world.markBlockForUpdate(x, y, z);
		}
	}

	public static void update(World world, int x, int y, int z, byte tier) {
		TileEntity tile = world.getTileEntity(x, y, z);

		if (tile instanceof TileChest) {
			((TileChest) tile).tier = tier;
		}
	}

	public static ItemStack getDynamicChest(ItemStack mimick) {
		ItemStack chest = new ItemStack(ChestsPlus.instance.blockDynamicChest);
		NBTTagCompound mimTag = new NBTTagCompound();
		mimick.writeToNBT(mimTag);
		if (!chest.hasTagCompound()) {
			chest.setTagCompound(new NBTTagCompound());
		}
		chest.getTagCompound().setTag("mimick", mimTag);
		return chest;
	}

}
