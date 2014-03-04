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

public class TileDynamicChest extends TileChest {

	public ItemStack mimickStack;
	
	@Override
	public Block getBlockType() {
		return ColoredChests.instance.blockDynamicChest;
	}

	@Override
	public int getBlockMetadata() {
		return 0;
	}

	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		if (mimickStack != null) {
			NBTTagCompound nbt = new NBTTagCompound();
			this.mimickStack.writeToNBT(nbt);
			tag.setCompoundTag("mimick", nbt);
		}
	}

	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		if (tag.hasKey("mimick")) {
			this.mimickStack = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("mimick"));
		}
	}
	
}
