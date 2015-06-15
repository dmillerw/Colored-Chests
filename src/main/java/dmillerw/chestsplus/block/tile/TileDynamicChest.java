package dmillerw.chestsplus.block.tile;

import dmillerw.chestsplus.ChestsPlus;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileDynamicChest extends TileChest {

	public ItemStack mimickStack;

	@Override
	public Block getBlockType() {
		return ChestsPlus.instance.blockDynamicChest;
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
			tag.setTag("mimick", nbt);
		}
	}

	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		if (tag.hasKey("mimick")) {
			this.mimickStack = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("mimick"));
		}
	}

}
