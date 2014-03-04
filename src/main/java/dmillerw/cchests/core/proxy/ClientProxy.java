package dmillerw.cchests.core.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.cchests.ColoredChests;
import dmillerw.cchests.block.tile.TileColoredChest;
import dmillerw.cchests.block.tile.TileDynamicChest;
import dmillerw.cchests.client.render.RenderColoredChest;
import dmillerw.cchests.client.render.RenderDynamicChest;
import dmillerw.cchests.util.UtilItem;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		RenderColoredChest renderColor = new RenderColoredChest();
		MinecraftForgeClient.registerItemRenderer(UtilItem.getItem(ColoredChests.instance.blockColoredChest), renderColor);
		ClientRegistry.bindTileEntitySpecialRenderer(TileColoredChest.class, renderColor);

		RenderDynamicChest renderDynamic = new RenderDynamicChest();
		MinecraftForgeClient.registerItemRenderer(UtilItem.getItem(ColoredChests.instance.blockDynamicChest), renderDynamic);
		ClientRegistry.bindTileEntitySpecialRenderer(TileDynamicChest.class, renderDynamic);
	}

}
