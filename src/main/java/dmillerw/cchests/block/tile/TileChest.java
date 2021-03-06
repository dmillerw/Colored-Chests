package dmillerw.cchests.block.tile;

import dmillerw.cchests.ColoredChests;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public abstract class TileChest extends TileEntity implements IInventory {

	private ItemStack[] chestContents = new ItemStack[36];

	public String customName;
	
	public ForgeDirection orientation;
	
    public float lidAngle;
    public float prevLidAngle;

    public int numUsingPlayers;
    private int ticksSinceSync;
    
    @Override
    public void updateEntity() {
    	super.updateEntity();
        float f;

        this.prevLidAngle = this.lidAngle;
        f = 0.1F;
        double d0;

		if (this.numUsingPlayers > 0 && this.lidAngle == 0.0F) {
			double d1 = (double) this.xCoord + 0.5D;
			d0 = (double) this.zCoord + 0.5D;
			this.worldObj.playSoundEffect(d1, (double) this.yCoord + 0.5D, d0, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}

		if (this.numUsingPlayers == 0 && this.lidAngle > 0.0F || this.numUsingPlayers > 0 && this.lidAngle < 1.0F) {
			float f1 = this.lidAngle;

			if (this.numUsingPlayers > 0) {
				this.lidAngle += f;
			} else {
				this.lidAngle -= f;
			}

			if (this.lidAngle > 1.0F) {
				this.lidAngle = 1.0F;
			}

			float f2 = 0.5F;

			if (this.lidAngle < f2 && f1 >= f2) {
				d0 = (double) this.xCoord + 0.5D;
				double d2 = (double) this.zCoord + 0.5D;
				this.worldObj.playSoundEffect(d0, (double) this.yCoord + 0.5D, d2, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}

			if (this.lidAngle < 0.0F) {
				this.lidAngle = 0.0F;
			}
		}
    }

    @Override
    public Packet getDescriptionPacket() {
    	NBTTagCompound nbt = new NBTTagCompound();
    	this.writeToNBT(nbt);
    	return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, nbt);
    }
    
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
    	this.readFromNBT(pkt.data);
    }
    
	public boolean receiveClientEvent(int par1, int par2) {
		if (par1 == 1) {
			this.numUsingPlayers = par2;
			return true;
		} else {
			return super.receiveClientEvent(par1, par2);
		}
	}

	public void openChest() {
		if (this.numUsingPlayers < 0) {
			this.numUsingPlayers = 0;
		}

        ++this.numUsingPlayers;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 1, this.numUsingPlayers);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType().blockID);
    }

    public void closeChest() {
		if (this.numUsingPlayers < 0) {
			this.numUsingPlayers = 0;
		}
    	
    	--this.numUsingPlayers;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 1, this.numUsingPlayers);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType().blockID);
    }
    
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		
		this.orientation = ForgeDirection.getOrientation(par1NBTTagCompound.getByte("orientation"));
		
		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
		this.chestContents = new ItemStack[this.getSizeInventory()];

		if (par1NBTTagCompound.hasKey("CustomName")) {
			this.customName = par1NBTTagCompound.getString("CustomName");
		}

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist
					.tagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;

			if (j >= 0 && j < this.chestContents.length) {
				this.chestContents[j] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
			}
		}
		
		readCustomNBT(par1NBTTagCompound);
	}

	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		
		par1NBTTagCompound.setByte("orientation", (byte) ((this.orientation != null) ? this.orientation.ordinal() : 0));
		
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.chestContents.length; ++i) {
			if (this.chestContents[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.chestContents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		par1NBTTagCompound.setTag("Items", nbttaglist);

		if (this.isInvNameLocalized()) {
			par1NBTTagCompound.setString("CustomName", this.customName);
		}
		
		writeCustomNBT(par1NBTTagCompound);
	}
    
	@Override
	public int getSizeInventory() {
		return 27;
	}

	public ItemStack getStackInSlot(int par1)
    {
        return this.chestContents[par1];
    }

	public ItemStack decrStackSize(int par1, int par2) {
		if (this.chestContents[par1] != null) {
			ItemStack itemstack;

			if (this.chestContents[par1].stackSize <= par2) {
				itemstack = this.chestContents[par1];
				this.chestContents[par1] = null;
				this.onInventoryChanged();
				return itemstack;
			} else {
				itemstack = this.chestContents[par1].splitStack(par2);

				if (this.chestContents[par1].stackSize == 0) {
					this.chestContents[par1] = null;
				}

				this.onInventoryChanged();
				return itemstack;
			}
		} else {
			return null;
		}
	}

	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.chestContents[par1] != null) {
			ItemStack itemstack = this.chestContents[par1];
			this.chestContents[par1] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.chestContents[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	public String getInvName() {
		return this.isInvNameLocalized() ? this.customName : "container.chest";
	}

	public boolean isInvNameLocalized() {
		return this.customName != null && this.customName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : entityplayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	@Override // I should not have to do this... :|
	public abstract Block getBlockType();
	
	@Override // Or this...
	public abstract int getBlockMetadata();
	
	public abstract void writeCustomNBT(NBTTagCompound tag);
	
	public abstract void readCustomNBT(NBTTagCompound tag);
	
}
