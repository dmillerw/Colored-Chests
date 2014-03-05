package dmillerw.chestsplus.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dmillerw.chestsplus.ChestsPlus;
import dmillerw.chestsplus.block.tile.TileChest;
import dmillerw.chestsplus.raytrace.IRaytracable;
import dmillerw.chestsplus.raytrace.IndexedAABB;
import dmillerw.chestsplus.raytrace.RayTracer;
import dmillerw.chestsplus.util.UtilEntity;
import dmillerw.chestsplus.util.UtilInventory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BlockChest extends BlockContainer implements IRaytracable {

	private final Random random = new Random();

	public BlockChest() {
		super(Material.wood);
		this.setCreativeTab(ChestsPlus.tab);

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
			TileChest tile = (TileChest) world.getTileEntity(x, y, z);
			if (tile != null) {
				RayTracer.RaytraceResult result = RayTracer.doRaytrace(world, x, y, z, player);

				if (result.hitID == 0) {
					if (!tile.isLocked(player)) {
						if (!player.isSneaking()) {
							if (world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN)) {
								return true;
							} else if (UtilEntity.isOcelotSittingOn(world, x, y, z)) {
								return true;
							}

							player.displayGUIChest((IInventory) tile);
							return true;
						}
					} else {
						player.addChatComponentMessage(new ChatComponentText("You find this chest to be locked, and cannot be opened."));
						return true;
					}
				} else if (result.hitID == 1) {
					if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ChestsPlus.instance.itemKey) {
						if (tile.isOwner(player)) {
							tile.locked = !tile.locked;
							//TODO sound
							return true;
						}
					}
				}
			}
		}

		return !player.isSneaking();
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

	@Override
	public IIcon getIcon(int side, int meta) {
		return Blocks.planks.getIcon(0, 0);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {

	}

	@Override
	public abstract TileEntity createNewTileEntity(World world, int meta);

	/* IRAYTRACABLE */
	@Override
	public List<IndexedAABB> getTargets(World world, int x, int y, int z) {
		List<IndexedAABB> targets = new ArrayList<IndexedAABB>();

		targets.add(new IndexedAABB(0, 0.0625F, 0F, 0.0625F, 0.9375F, 0.875F, 0.9375F));

		TileChest tile = (TileChest) world.getTileEntity(x, y, z);

		if (tile != null && tile.lidAngle == 0 && tile.orientation != null) {
			float pixel = 0.0625F;
			float ymin = pixel * 7;
			float ymax = pixel * 11;
			float lockWidthMin = 0.5F - pixel;
			float lockWidthMax = 0.5F + pixel;

			switch (tile.orientation) {
				case NORTH: {
					targets.add(new IndexedAABB(1, 1, lockWidthMin, ymin, 0, lockWidthMax, ymax, pixel));
					break;
				}

				case SOUTH: {
					targets.add(new IndexedAABB(1, 1, lockWidthMin, ymin, 1, lockWidthMax, ymax, 1 - pixel));
					break;
				}

				case EAST: {
					targets.add(new IndexedAABB(1, 1, 1, ymin, lockWidthMin, 1 - pixel, ymax, lockWidthMax));
					break;
				}

				case WEST: {
					targets.add(new IndexedAABB(1, 1, 0, ymin, lockWidthMin, pixel, ymax, lockWidthMax));
					break;
				}
			}
		}

		return targets;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
		// For this example, just sets the bounds to full. This can change
		setBlockBounds(0.0625F, 0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * Used to return the selection bounding box
	 */
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		RayTracer.RaytraceResult result = RayTracer.doRaytrace(world, x, y, z, Minecraft.getMinecraft().thePlayer);

		if (result != null && result.aabb != null) {
			// Returns the resulting bounding box, offset to match the block coordinates
			return result.aabb.offset(x, y, z);
		} else {
			return super.getSelectedBoundingBoxFromPool(world, x, y, z);
		}
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 origin, Vec3 direction) {
		RayTracer.RaytraceResult result = RayTracer.doRaytrace(world, x, y, z, origin, direction);

		if (result == null) {
			return null;
		} else {
			return result.mob;
		}
	}

	@Override
	public MovingObjectPosition raytrace(World world, int x, int y, int z, Vec3 origin, Vec3 direction) {
		return super.collisionRayTrace(world, x, y, z, origin, direction);
	}

}
