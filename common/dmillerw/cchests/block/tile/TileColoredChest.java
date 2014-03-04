package dmillerw.cchests.block.tile;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import dmillerw.cchests.ColoredChests;

public class TileColoredChest extends TileChest {

	public int metaOverride;
	
	public TileColoredChest() {
		this.metaOverride = -1;
	}
	
	public TileColoredChest(int meta) {
		this.metaOverride = meta;
	}
	
	@Override
	public Block getBlockType() {
		return ColoredChests.instance.blockColoredChest;
	}

	@Override
	public int getBlockMetadata() {
		if (this.hasWorldObj()) {
			return this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		} else {
			return this.metaOverride;
		}
	}

	@Override
	public void writeCustomNBT(NBTTagCompound tag) {}

	@Override
	public void readCustomNBT(NBTTagCompound tag) {}

}
