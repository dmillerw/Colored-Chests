package dmillerw.chestsplus.core.handler;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.chestsplus.ChestsPlus;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class InteractionHandler {

	@SubscribeEvent
	public void onBlockInteract(PlayerInteractEvent event) {
		if (event.action == Action.RIGHT_CLICK_BLOCK) {
			EntityPlayer player = event.entityPlayer;
			Block block = player.worldObj.getBlock(event.x, event.y, event.z);

			if (block == Blocks.chest) {
				if (player.isSneaking() && player.getCurrentEquippedItem() != null) {
					ItemStack item = player.getCurrentEquippedItem();

					if (!player.worldObj.isRemote) {
						if (item.getItem() == Items.dye) {
							ChestHandler.dye(player.worldObj, event.x, event.y, event.z, ChestsPlus.DYE_REVERSE_MAPPING[player.getCurrentEquippedItem().getItemDamage()]);
						}
					}

					event.useItem = Event.Result.DENY;
				}
			} else if (player.isSneaking()) {
				ItemStack held = player.getHeldItem();

				if (!player.worldObj.isRemote) {
					if (held != null) {
						if (block == ChestsPlus.instance.blockColoredChest) {
							if (held.getItem() == Items.dye) {
								ChestHandler.dye(player.worldObj, event.x, event.y, event.z, ChestsPlus.DYE_REVERSE_MAPPING[player.getCurrentEquippedItem().getItemDamage()]);
							} else if (held.getItem() == Items.water_bucket) {
								ChestHandler.vanilla(player.worldObj, event.x, event.y, event.z);
							}

							event.useItem = Event.Result.DENY;
							event.useBlock = Event.Result.DENY;
						} else if (block == ChestsPlus.instance.blockDynamicChest) {
							if (held.getItem() instanceof ItemBlock) {
								ChestHandler.mimick(player.worldObj, event.x, event.y, event.z, player.getCurrentEquippedItem().copy());
							}

							event.useItem = Event.Result.DENY;
							event.useBlock = Event.Result.DENY;
						}
					} else if (block == ChestsPlus.instance.blockDynamicChest) {
						ChestHandler.vanilla(player.worldObj, event.x, event.y, event.z);

						event.useItem = Event.Result.DENY;
						event.useBlock = Event.Result.DENY;
					}
				}
			}
		}
	}

}
