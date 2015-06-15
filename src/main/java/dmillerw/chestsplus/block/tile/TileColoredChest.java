package dmillerw.chestsplus.block.tile;

import dmillerw.chestsplus.ChestsPlus;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;

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
		return ChestsPlus.instance.blockColoredChest;
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
	public void writeCustomNBT(NBTTagCompound tag) {
	}

	@Override
	public void readCustomNBT(NBTTagCompound tag) {
	}

}
