package dmillerw.cchests.block;

import static net.minecraftforge.common.ForgeDirection.DOWN;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dmillerw.cchests.ColoredChests;
import dmillerw.cchests.block.tile.TileChest;
import dmillerw.cchests.block.tile.TileColoredChest;
import dmillerw.cchests.util.UtilEntity;
import dmillerw.cchests.util.UtilInventory;

public abstract class BlockChest extends BlockContainer {
    
	private final Random random = new Random();

    public BlockChest(int id) {
        super(id, Material.wood);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        this.setHardness(2F);
        this.setResistance(2F);
    }

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		
		TileChest tile = (TileChest) world.getBlockTileEntity(x, y, z);
		
		if (tile != null) {
			tile.orientation = UtilEntity.get2DFacingRotation(entity);
		}
	}

	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		UtilInventory.dropContents(world, x, y, z);
		super.breakBlock(world, x, y, z, id, meta);
	}

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fx, float fy, float fz) {
    	if (!world.isRemote) {
    		TileEntity tile = world.getBlockTileEntity(x, y, z);
            if (tile != null){
            	if (world.isBlockSolidOnSide(x, y + 1, z, DOWN)) {
    				return true;
    			} else if (UtilEntity.isOcelotSittingOn(world, x, y, z)) {
    				return true;
    			}
            	
                player.displayGUIChest((IInventory) tile);
            }
    	}
    	
    	return true;
    }

    @Override
    public abstract void getSubBlocks(int id, CreativeTabs tab, List list);
    
	public abstract TileEntity createNewTileEntity(World world);

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int meta) {
		return Container.calcRedstoneFromInventory((IInventory) world.getBlockTileEntity(x, y, z));
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public int damageDropped(int damage) {
		return damage;
	}
	
    @SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon("planks_oak");
	}
}
