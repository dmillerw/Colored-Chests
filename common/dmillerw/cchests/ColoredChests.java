package dmillerw.cchests;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dmillerw.cchests.block.BlockColoredChest;
import dmillerw.cchests.block.BlockDynamicChest;
import dmillerw.cchests.block.item.ItemBlockColoredChest;
import dmillerw.cchests.block.tile.TileColoredChest;
import dmillerw.cchests.block.tile.TileDynamicChest;
import dmillerw.cchests.core.handler.InteractionHandler;
import dmillerw.cchests.core.proxy.CommonProxy;
import dmillerw.cchests.lib.ModInfo;

@Mod(modid=ModInfo.ID, name=ModInfo.NAME, version=ModInfo.VERSION)
public class ColoredChests {

	public static final String[] DYE_NAMES = new String[] {"White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray", "Silver", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black"};
	public static final String[] DYE_ORE_TAGS = new String[] {"dyeWhite", "dyeOrange", "dyeMagenta", "dyeLightBlue", "dyeYellow", "dyeLime", "dyePink", "dyeGray", "dyeLightGray", "dyeCyan", "dyePurple", "dyeBlue", "dyeBrown", "dyeGreen", "dyeRed", "dyeBlack"};

	public static final int[] DYE_REVERSE_MAPPING = new int[] {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
	
	@Instance(ModInfo.ID)
	public static ColoredChests instance;
	@SidedProxy(serverSide=ModInfo.COMMON_PROXY, clientSide=ModInfo.CLIENT_PROXY)
	public static CommonProxy proxy;

	public boolean ironChestsLoaded = false;
	
	public int blockColoredChestID;
	public int blockDynamicChestID;
	
	public Block blockColoredChest;
	public Block blockDynamicChest;
	
	public Item cpw_woodToIronUpgrade;
	public Item cpw_woodToCopperUpgrade;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		blockColoredChestID = config.getBlock("blockID_coloredChest", 1000).getInt(1000);
		blockDynamicChestID = config.getBlock("blockID_dynamicChest", 1001).getInt(1001);
		config.save();
		
		blockColoredChest = new BlockColoredChest(blockColoredChestID).setUnlocalizedName("blockColoredChest");
		GameRegistry.registerBlock(blockColoredChest, ItemBlockColoredChest.class, "blockColoredChest");
		GameRegistry.registerTileEntity(TileColoredChest.class, "blockColoredChest_tile");
		
		blockDynamicChest = new BlockDynamicChest(blockDynamicChestID).setUnlocalizedName("blockDynamicChest");
		GameRegistry.registerBlock(blockDynamicChest, "blockDynamicChest");
		GameRegistry.registerTileEntity(TileDynamicChest.class, "blockDynamicChest_tile");
		LanguageRegistry.addName(blockDynamicChest, "Dynamic Chest");
		
		/* COLORED CHEST RECIPES */
		for (int i=0; i<16; i++) {
			LanguageRegistry.addName(new ItemStack(blockColoredChest, 1, i), DYE_NAMES[i] + " Chest");
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockColoredChest, 1, i), "PPP", "PDP", "PPP", 'P', "plankWood", 'D', DYE_ORE_TAGS[i]));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(blockColoredChest, 1, i), Block.chest, DYE_ORE_TAGS[i]));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(blockColoredChest, 1, i), new ItemStack(blockColoredChest, 1, OreDictionary.WILDCARD_VALUE), DYE_ORE_TAGS[i]));
		}
		GameRegistry.addShapelessRecipe(new ItemStack(Block.chest), new ItemStack(blockColoredChest, 1, OreDictionary.WILDCARD_VALUE));
		
		/* DYNAMIC CHEST RECIPES */
		GameRegistry.addRecipe(new ShapedOreRecipe(blockDynamicChest, new Object[] {"PPP", "PSP", "PPP", 'P', "plankWood", 'S', "stone"}));
		
		MinecraftForge.EVENT_BUS.register(new InteractionHandler());
		
		proxy.preInit(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if (Loader.isModLoaded("IronChest")) {
			FMLLog.info("[Colored Chests] Iron Chests detected, adding support for vanilla chest upgrade", new Object[0]);
			
			this.ironChestsLoaded = true;
			this.cpw_woodToIronUpgrade = GameRegistry.findItem("IronChest", "woodIronUpgrade");
			this.cpw_woodToCopperUpgrade = GameRegistry.findItem("IronChest", "woodCopperUpgrade");
		}
		
		proxy.postInit(event);
	}
	
}
