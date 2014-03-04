package dmillerw.cchests.client.handler;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Resource;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

public class IconHandler {

	public static String getIconPath(Icon icon, boolean block) {
		if (icon == null) {
			return "null";
		}
		String[] split = icon.getIconName().split(":");
		boolean minecraftIcon = split.length != 2;
		return (minecraftIcon ? "minecraft" : split[0]) + ":textures/" + (block ? "blocks/" : "items/") + (minecraftIcon ? icon.getIconName() : split[1]) + ".png";
	}
	
	public static Resource getResourceForIcon(Icon icon, boolean block) {
		try {
			return Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(getIconPath(icon, block)));
		} catch (IOException e) {
			return null;
		}
	}
	
}
