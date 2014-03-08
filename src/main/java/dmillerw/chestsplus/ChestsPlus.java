package dmillerw.chestsplus;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.chestsplus.block.BlockColoredChest;
import dmillerw.chestsplus.block.BlockDynamicChest;
import dmillerw.chestsplus.block.item.ItemBlockColoredChest;
import dmillerw.chestsplus.block.tile.TileColoredChest;
import dmillerw.chestsplus.block.tile.TileDynamicChest;
import dmillerw.chestsplus.core.handler.GuiHandler;
import dmillerw.chestsplus.core.handler.InteractionHandler;
import dmillerw.chestsplus.core.proxy.CommonProxy;
import dmillerw.chestsplus.item.ItemKey;
import dmillerw.chestsplus.lib.ModInfo;
import dmillerw.chestsplus.tab.ModdedCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class ChestsPlus {

	public static final String[] DYE_ORE_TAGS = new String[]{"dyeWhite", "dyeOrange", "dyeMagenta", "dyeLightBlue", "dyeYellow", "dyeLime", "dyePink", "dyeGray", "dyeLightGray", "dyeCyan", "dyePurple", "dyeBlue", "dyeBrown", "dyeGreen", "dyeRed", "dyeBlack"};

	public static final int[] DYE_REVERSE_MAPPING = new int[]{15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};

	@Instance(ModInfo.ID)
	public static ChestsPlus instance;
	@SidedProxy(serverSide = ModInfo.COMMON_PROXY, clientSide = ModInfo.CLIENT_PROXY)
	public static CommonProxy proxy;

	public static CreativeTabs tab = new ModdedCreativeTab(ModInfo.ID);

	public Block blockColoredChest;
	public Block blockDynamicChest;

	public Item itemKey;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		blockColoredChest = new BlockColoredChest().setBlockName("coloredChest");
		GameRegistry.registerBlock(blockColoredChest, ItemBlockColoredChest.class, "coloredChest");
		GameRegistry.registerTileEntity(TileColoredChest.class, "coloredChest");

		blockDynamicChest = new BlockDynamicChest().setBlockName("dynamicChest");
		GameRegistry.registerBlock(blockDynamicChest, "dynamicChest");
		GameRegistry.registerTileEntity(TileDynamicChest.class, "dynamicChest");

		itemKey = new ItemKey().setUnlocalizedName("key");
		GameRegistry.registerItem(itemKey, "key");

		/* COLORED CHEST RECIPES */
		for (int i = 0; i < 16; i++) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockColoredChest, 1, i), "PPP", "PDP", "PPP", 'P', "plankWood", 'D', DYE_ORE_TAGS[i]));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(blockColoredChest, 1, i), Blocks.chest, DYE_ORE_TAGS[i]));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(blockColoredChest, 1, i), new ItemStack(blockColoredChest, 1, OreDictionary.WILDCARD_VALUE), DYE_ORE_TAGS[i]));
		}
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.chest), new ItemStack(blockColoredChest, 1, OreDictionary.WILDCARD_VALUE));
		
		/* DYNAMIC CHEST RECIPES */
		GameRegistry.addRecipe(new ShapedOreRecipe(blockDynamicChest, new Object[]{"PPP", "PSP", "PPP", 'P', "plankWood", 'S', "stone"}));

		/* KEY RECIPE */
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemKey), "  I", "III", "I I", 'I', Items.iron_ingot));

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		MinecraftForge.EVENT_BUS.register(new InteractionHandler());

		proxy.preInit(event);
	}

}
