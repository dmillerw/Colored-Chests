package dmillerw.cchests.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dmillerw.cchests.block.tile.TileChest;
import dmillerw.cchests.util.UtilEntity;
import dmillerw.cchests.util.UtilInventory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

public abstract class BlockChest extends BlockContainer {

	private final Random random = new Random();

	public BlockChest() {
		super(Material.wood);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		this.setHardness(2F);
		this.setResistance(2F);
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);

		TileChest tile = (TileChest) world.getTileEntity(x, y, z);

		if (tile != null) {
			tile.orientation = UtilEntity.get2DFacingRotation(entity);
		}
	}

	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		UtilInventory.dropContents(world, x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fx, float fy, float fz) {
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile != null) {
				if (world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN)) {
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
	public abstract void getSubBlocks(Item item, CreativeTabs tab, List list);

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int meta) {
		return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(x, y, z));
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
	public void registerIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon("planks_oak");
	}

	@Override
	public abstract TileEntity createNewTileEntity(World world, int meta);
}
