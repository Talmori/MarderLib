package talsumi.marderlib.mixininterfaces;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public interface MarderLibItemExtendedBehaviour {

    /**
     * Called before this item is dropped. Return true to cancel further operations.
     * @param player
     * @param item
     * @param world
     * @return
     */
    boolean onItemDropped(@Nonnull PlayerEntity player, @Nonnull ItemStack item, @Nonnull World world);

    /**
     * Called when a player scrolls while holding this item. Return true to cancel further operations. This is called on the server via a packet. Returning true will only have an effect clientside!
     * @param item
     * @param player
     * @param world
     * @param scrollAmount
     * @return
     */
    boolean onItemScrolled(@Nonnull ItemStack item, @Nonnull PlayerEntity player, @Nonnull World world, boolean isClient, boolean sneaking, double scrollAmount);
}
