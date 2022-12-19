package talsumi.marderlib.mixins;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import talsumi.marderlib.mixininterfaces.MarderLibItemExtendedBehaviour;
import talsumi.marderlib.networking.MarderLibClientPacketsOut;
import talsumi.marderlib.networking.MarderLibServerPacketsOut;

@Mixin(PlayerInventory.class)
public class MarderLibPlayerInventoryMixin {

    //TODO: Reimplement this
    @Inject(at = @At(value = "HEAD"), method = "scrollInHotbar", cancellable = true)
    public void scrollInHotbar(double scrollAmount, CallbackInfo info)
    {
        var inv = (PlayerInventory)(Object) this;
        var selectedSlot = ((PlayerInventory)(Object) this).selectedSlot;
        var stack = inv.getStack(selectedSlot);
        if (stack.getItem() instanceof MarderLibItemExtendedBehaviour) {
            var item = (MarderLibItemExtendedBehaviour) stack.getItem();
            if (item.onItemScrolled(stack, inv.player, inv.player.world, true, inv.player.isSneaking(), scrollAmount))
                info.cancel();
            MarderLibClientPacketsOut.INSTANCE.sendScrolledItemPacket(scrollAmount, selectedSlot);
        }
    }
}
