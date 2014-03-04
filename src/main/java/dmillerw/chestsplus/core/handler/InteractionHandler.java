package dmillerw.chestsplus.core.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.chestsplus.ChestsPlus;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class InteractionHandler {

	@SubscribeEvent
	public void onBlockInteract(PlayerInteractEvent event) {
		if (!event.entityPlayer.worldObj.isRemote) {
			if (event.action == Action.RIGHT_CLICK_BLOCK) {
				EntityPlayer player = event.entityPlayer;
				Block block = player.worldObj.getBlock(event.x, event.y, event.z);

				if (block == ChestsPlus.instance.blockColoredChest) {
					if (player.isSneaking() && player.getCurrentEquippedItem() != null) {
						if (player.getCurrentEquippedItem().getItem() == Items.dye) {
							ChestHandler.dye(player.worldObj, event.x, event.y, event.z, ChestsPlus.DYE_REVERSE_MAPPING[player.getCurrentEquippedItem().getItemDamage()]);
							event.setCanceled(true);
						} else if (player.getCurrentEquippedItem().getItem() == Items.water_bucket) {
							ChestHandler.vanilla(player.worldObj, event.x, event.y, event.z);
							event.setCanceled(true);
						}
					}
				} else if (block == ChestsPlus.instance.blockDynamicChest) {
					if (player.isSneaking() && player.getCurrentEquippedItem() != null) {
						if (player.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
							ChestHandler.mimick(player.worldObj, event.x, event.y, event.z, player.getCurrentEquippedItem().copy());
							event.setCanceled(true);
						}
					} else if (player.isSneaking() && player.getCurrentEquippedItem() == null) {
						ChestHandler.vanilla(player.worldObj, event.x, event.y, event.z);
						event.setCanceled(true);
					}
				} else if (block == Blocks.chest) {
					if (player.isSneaking() && player.getCurrentEquippedItem() != null) {
						if (player.getCurrentEquippedItem().getItem() == Items.dye) {
							ChestHandler.dye(player.worldObj, event.x, event.y, event.z, ChestsPlus.DYE_REVERSE_MAPPING[player.getCurrentEquippedItem().getItemDamage()]);
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
