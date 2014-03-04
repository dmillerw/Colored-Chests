package dmillerw.cchests.core.handler;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.oredict.OreDictionary;
import dmillerw.cchests.ColoredChests;
import dmillerw.cchests.block.BlockColoredChest;
import dmillerw.cchests.block.tile.TileDynamicChest;
import dmillerw.cchests.util.UtilInventory;

public class InteractionHandler {

	@ForgeSubscribe
	public void onBlockInteract(PlayerInteractEvent event) {
		if (!event.entityPlayer.worldObj.isRemote) {
			if (ColoredChests.instance.ironChestsLoaded) {
				EntityPlayer player = event.entityPlayer;
				int id = player.worldObj.getBlockId(event.x, event.y, event.z);
				
				if (player.getCurrentEquippedItem() != null && (id == ColoredChests.instance.blockColoredChestID || id == ColoredChests.instance.blockDynamicChestID)) {
	        		ItemStack held = player.getCurrentEquippedItem();
	        		
	        		// Basically, if it's being hit with an upgrade, transform into a vanilla chest. Less work for me! :D
	        		if (held.getItem() == ColoredChests.instance.cpw_woodToIronUpgrade || held.getItem() == ColoredChests.instance.cpw_woodToCopperUpgrade) {
	        			ChestHandler.vanilla(player.worldObj, event.x, event.y, event.z);
	        		}
	        	}
			}
			
			if (event.action == Action.RIGHT_CLICK_BLOCK) {
				EntityPlayer player = event.entityPlayer;
				int id = player.worldObj.getBlockId(event.x, event.y, event.z);
				
				if (id == ColoredChests.instance.blockColoredChestID) {
					if (player.isSneaking() && player.getCurrentEquippedItem() != null) {
						if (player.getCurrentEquippedItem().getItem() == Item.dyePowder) {
							ChestHandler.dye(player.worldObj, event.x, event.y, event.z, ColoredChests.DYE_REVERSE_MAPPING[player.getCurrentEquippedItem().getItemDamage()]);
							event.setCanceled(true);
						} else if (player.getCurrentEquippedItem().getItem() == Item.bucketWater) {
							ChestHandler.vanilla(player.worldObj, event.x, event.y, event.z);
							event.setCanceled(true);
						}
					}
				} else if (id == ColoredChests.instance.blockDynamicChestID) {
					if (player.isSneaking() && player.getCurrentEquippedItem() != null) {
						if (player.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
							ChestHandler.mimick(player.worldObj, event.x, event.y, event.z, player.getCurrentEquippedItem().copy());
							event.setCanceled(true);
						}
					} else if (player.isSneaking() && player.getCurrentEquippedItem() == null){
						ChestHandler.vanilla(player.worldObj, event.x, event.y, event.z);
						event.setCanceled(true);
					}
				} else if (id == Block.chest.blockID) {
					if (player.isSneaking() && player.getCurrentEquippedItem() != null) {
						if (player.getCurrentEquippedItem().getItem() == Item.dyePowder) {
							ChestHandler.dye(player.worldObj, event.x, event.y, event.z, ColoredChests.DYE_REVERSE_MAPPING[player.getCurrentEquippedItem().getItemDamage()]);
							event.setCanceled(true);
						} else if (player.isSneaking() && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
							ChestHandler.mimick(player.worldObj, event.x, event.y, event.z, player.getCurrentEquippedItem().copy());
							event.setCanceled(true);
						}
					}
				}
			}
		}
	}
	
}
