package talsumi.marderlib.mixins;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import talsumi.marderlib.mixininterfaces.MarderLibItemExtendedBehaviour;

@Mixin(PlayerEntity.class)
public class MarderLibPlayerEntityMixin {

    //TODO: Reimplement this
    @Inject(at = @At(value = "HEAD"), target = @Desc(value = "dropItem", args = {ItemStack.class, boolean.class}, ret = ItemEntity.class), cancellable = true)
    public void dropItem(ItemStack stack, boolean retainOwnership, CallbackInfoReturnable info)
    {
        if (stack.getItem() instanceof MarderLibItemExtendedBehaviour) {
            var item = (MarderLibItemExtendedBehaviour) stack.getItem();
            var player = (PlayerEntity)(Object) this;
            if (item.onItemDropped(player, stack, player.world))
                info.cancel();
        }
    }
}
