package talsumi.marderlib.mixininterfaces;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;


public interface MarderLibItemExtendedBehaviour {

    /**
     * Called before this item is dropped. Return true to cancel further operations.
     * @param player
     * @param item
     * @param world
     * @return
     */
    boolean onItemDropped(@NotNull PlayerEntity player, @NotNull ItemStack item, @NotNull World world);

    /**
     * Called when a player scrolls while holding this item. Return true to cancel further operations. This is called on the server via a packet. Returning true will only have an effect clientside!
     * @param item
     * @param player
     * @param world
     * @param scrollAmount
     * @return
     */
    boolean onItemScrolled(@NotNull ItemStack item, @NotNull PlayerEntity player, @NotNull World world, boolean isClient, boolean sneaking, double scrollAmount);
}
