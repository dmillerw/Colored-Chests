package dmillerw.chestsplus.core.handler;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.chestsplus.ChestsPlus;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
						} else {
							ChestHandler.mimick(player.worldObj, event.x, event.y, event.z, item.copy());
						}
					}

					event.setResult(Event.Result.DENY);
				}
			}
		}
	}

}
