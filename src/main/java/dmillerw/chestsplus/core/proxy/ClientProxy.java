package dmillerw.chestsplus.core.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.chestsplus.ChestsPlus;
import dmillerw.chestsplus.block.tile.TileColoredChest;
import dmillerw.chestsplus.block.tile.TileDynamicChest;
import dmillerw.chestsplus.client.render.RenderColoredChest;
import dmillerw.chestsplus.client.render.RenderDynamicChest;
import dmillerw.chestsplus.util.UtilItem;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		RenderColoredChest renderColor = new RenderColoredChest();
		MinecraftForgeClient.registerItemRenderer(UtilItem.getItem(ChestsPlus.instance.blockColoredChest), renderColor);
		ClientRegistry.bindTileEntitySpecialRenderer(TileColoredChest.class, renderColor);

		RenderDynamicChest renderDynamic = new RenderDynamicChest();
		MinecraftForgeClient.registerItemRenderer(UtilItem.getItem(ChestsPlus.instance.blockDynamicChest), renderDynamic);
		ClientRegistry.bindTileEntitySpecialRenderer(TileDynamicChest.class, renderDynamic);
	}

}
