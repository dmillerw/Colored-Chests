package dmillerw.cchests.core.proxy;

import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.cchests.ColoredChests;
import dmillerw.cchests.block.tile.TileColoredChest;
import dmillerw.cchests.block.tile.TileDynamicChest;
import dmillerw.cchests.client.handler.TextureHandler;
import dmillerw.cchests.client.render.RenderColoredChest;
import dmillerw.cchests.client.render.RenderDynamicChest;

public class ClientProxy extends CommonProxy {

	public boolean cacheOnLoad = false;
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		Property col = config.get("general", "cacheOnLoad", false);
		col.comment = "Whether to cache all dynamic chest textures on load. Setting this to false means they will be generated upon request, which may impact performace. Setting this to true will add an additional few seconds to load time.";
		this.cacheOnLoad = col.getBoolean(false);
		if (config.hasChanged()) {
			config.save();
		}
		
		RenderColoredChest renderColor = new RenderColoredChest();
		MinecraftForgeClient.registerItemRenderer(ColoredChests.instance.blockColoredChestID, renderColor);
		ClientRegistry.bindTileEntitySpecialRenderer(TileColoredChest.class, renderColor);
		RenderDynamicChest renderDynamic = new RenderDynamicChest();
		MinecraftForgeClient.registerItemRenderer(ColoredChests.instance.blockDynamicChestID, renderDynamic);
		ClientRegistry.bindTileEntitySpecialRenderer(TileDynamicChest.class, renderDynamic);
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		if (cacheOnLoad) {
			FMLLog.info("[Colored Chests] Caching all dynamic chest textures. This may take a few seconds.");
			long startTime = System.currentTimeMillis();
			for (int i=0; i<4096; i++) {
				for (int j=0; j<16; j++) {
					if (Block.blocksList[i] != null) {
						try {
							TextureHandler.CACHED_TEXTURES[i][j] = TextureHandler.generateChestTexture(new ItemStack(i, 1, j));
						} catch(Exception ex) {
							
						}
					}
				}
			}
			FMLLog.info("[Colored Chests] Generation of all images took " + (System.currentTimeMillis() - startTime) + "ms");
		}
	}
	
}
