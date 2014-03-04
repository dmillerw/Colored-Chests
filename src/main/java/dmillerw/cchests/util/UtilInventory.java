package dmillerw.cchests.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class UtilInventory {

	public static void dropContents(World world, int x, int y, int z) {
		Random random = new Random();

		IInventory inventory = (IInventory) world.getTileEntity(x, y, z);

		if (inventory != null) {
			for (int j1 = 0; j1 < inventory.getSizeInventory(); ++j1) {
				dropItem(world, x, y, z, inventory.getStackInSlot(j1));
			}
		}
	}

	public static void dropItem(World world, int x, int y, int z, ItemStack item) {
		Random random = new Random();
		if (item != null) {
			float f = random.nextFloat() * 0.8F + 0.1F;
			float f1 = random.nextFloat() * 0.8F + 0.1F;
			float f3 = 0.05F;

			EntityItem entityitem = new EntityItem(world);
			entityitem.setPosition(x + .5, y + .5, z + .5);
			entityitem.setEntityItemStack(item.copy());

			entityitem.motionX = (double) ((float) random.nextGaussian() * f3);
			entityitem.motionY = (double) ((float) random.nextGaussian() * f3 + 0.2F);
			entityitem.motionZ = (double) ((float) random.nextGaussian() * f3);

			world.spawnEntityInWorld(entityitem);
		}
	}

}
