package talsumi.marderlib.client.eventhandlers

import net.minecraft.block.entity.BlockEntity
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.Entity
import talsumi.marderlib.content.IUpdatableBlockEntity
import talsumi.marderlib.content.IUpdatableEntity
import talsumi.marderlib.networking.MarderLibClientPacketsOut

object ClientEventHandlers {

    fun onEntityLoad(entity: Entity, world: ClientWorld)
    {
        if (entity is IUpdatableEntity)
            MarderLibClientPacketsOut.sendRequestEntityUpdate(entity)
    }

    fun onBlockEntityLoad(be: BlockEntity, world: ClientWorld)
    {
        if (be is IUpdatableBlockEntity)
            MarderLibClientPacketsOut.sendRequestBlockEntityUpdate(be)
    }
}