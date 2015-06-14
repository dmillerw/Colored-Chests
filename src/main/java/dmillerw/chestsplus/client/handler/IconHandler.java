package dmillerw.chestsplus.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class IconHandler {

	public static String getIconPath(IIcon icon, boolean block) {
		if (icon == null) {
			return "null";
		}
		String[] split = icon.getIconName().split(":");
		boolean minecraftIcon = split.length != 2;
		return (minecraftIcon ? "minecraft" : split[0]) + ":textures/" + (block ? "blocks/" : "items/") + (minecraftIcon ? icon.getIconName() : split[1]) + ".png";
	}

	public static IResource getResourceForIcon(IIcon icon, boolean block) {
		try {
			return Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(getIconPath(icon, block)));
		} catch (Exception e) {
			return null;
		}
	}

}
