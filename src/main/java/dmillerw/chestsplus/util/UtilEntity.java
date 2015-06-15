package dmillerw.chestsplus.util;

import java.util.Iterator;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class UtilEntity {

	public static ForgeDirection get2DFacingRotation(EntityLivingBase entity) {
		byte face = 0;
		int clampedAngle = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (clampedAngle == 0) {
			face = 2;
		} else if (clampedAngle == 1) {
			face = 5;
		} else if (clampedAngle == 2) {
			face = 3;
		} else if (clampedAngle == 3) {
			face = 4;
		}

		return ForgeDirection.getOrientation(face);
	}

	public static boolean isOcelotSittingOn(World world, int x, int y, int z) {
		Iterator iterator = world.getEntitiesWithinAABB(EntityOcelot.class, AxisAlignedBB.getBoundingBox((double) x, (double) (y + 1), (double) z, (double) (x + 1), (double) (y + 2), (double) (z + 1))).iterator();

		if (!iterator.hasNext()) {
			return false;
		} else {
			while (iterator.hasNext()) {
				if (((EntityOcelot) iterator.next()).isSitting()) {
					return true;
				}
			}
		}

		return false;
	}

}
