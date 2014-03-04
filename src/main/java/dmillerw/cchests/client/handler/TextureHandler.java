package dmillerw.cchests.client.handler;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import dmillerw.cchests.lib.ModInfo;

public class TextureHandler {

	public static DynamicTexture[][] CACHED_TEXTURES = new DynamicTexture[4096][16];
	
	private static DrawInfo[] DRAW_ORDERS = new DrawInfo[] {
		/* LID TOP/BOTTOM */
		new DrawInfo(15, 1, 12, 12, 2, 2, 12, 12),
		new DrawInfo(29, 1, 12, 12, 2, 2, 12, 12),
		/* LID SIDES */
		new DrawInfo(1,  15, 12, 3, 2, 3, 12, 3),
		new DrawInfo(15, 15, 12, 3, 2, 3, 12, 3),
		new DrawInfo(29, 15, 12, 3, 2, 3, 12, 3),
		new DrawInfo(43, 15, 12, 3, 2, 3, 12, 3),
		/* BODY TOP/BOTTOM */
		new DrawInfo(15, 20, 12, 12, 2, 2, 12, 12),
		new DrawInfo(29, 20, 12, 12, 2, 2, 12, 12),
		/* BODY SIDES */
		new DrawInfo(1,  34, 12, 8, 2, 7, 12, 8),
		new DrawInfo(15, 34, 12, 8, 2, 7, 12, 8),
		new DrawInfo(29, 34, 12, 8, 2, 7, 12, 8),
		new DrawInfo(43, 34, 12, 8, 2, 7, 12, 8),
	};
	
	public static AbstractTexture getChestTextureForBlock(ItemStack stack) {
		if (stack.getItem().itemID > 4095) {
			throw new RuntimeException("Tried to get block texture for item!");
		}
		
		if (CACHED_TEXTURES[stack.getItem().itemID][stack.getItemDamage()] == null) {
			CACHED_TEXTURES[stack.getItem().itemID][stack.getItemDamage()] = generateChestTexture_safe(stack);
		}
		return CACHED_TEXTURES[stack.getItem().itemID][stack.getItemDamage()];
	}
	
	public static DynamicTexture generateChestTexture_safe(ItemStack stack) {
		try {
			return generateChestTexture(stack);
		} catch(IOException ex) {
			return null;
		}
	}
	
	public static DynamicTexture generateChestTexture(ItemStack stack) throws IOException, NullPointerException {
		BufferedImage chestCopy = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(ModInfo.RESOURCE_PREFIX + "textures/normalTrans.png")).getInputStream());
		BufferedImage blockIcon = ImageIO.read(IconHandler.getResourceForIcon(Block.blocksList[stack.getItem().itemID].getIcon(0, stack.getItemDamage()), true).getInputStream());
		
		if (chestCopy != null && blockIcon != null) {
			Graphics2D graphics = chestCopy.createGraphics();
			
			for (DrawInfo info : DRAW_ORDERS) {
				Image sub = blockIcon.getSubimage(info.iconX, info.iconY, info.iconW, info.iconH);
				graphics.drawImage(sub, info.drawX, info.drawY, info.drawW, info.drawH, null);
			}
		}
		
		return new DynamicTexture(chestCopy);
	}
	
	private static class DrawInfo {
		public final int drawX;
		public final int drawY;
		public final int drawW;
		public final int drawH;
		
		public final int iconX;
		public final int iconY;
		public final int iconW;
		public final int iconH;
		
		public DrawInfo(int dx, int dy, int dw, int dh, int ix, int iy, int iw, int ih) {
			this.drawX = dx;
			this.drawY = dy;
			this.drawW = dw;
			this.drawH = dh;
			this.iconX = ix;
			this.iconY = iy;
			this.iconW = iw;
			this.iconH = ih;
		}
	}

}
