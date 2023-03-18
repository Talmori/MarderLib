/*
 * MIT License
 *
 *  Copyright (c) 2022 Talsumi
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 *
 */

package talsumi.marderlib.networking

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import talsumi.marderlib.MarderLib
import talsumi.marderlib.content.IUpdatableBlockEntity
import talsumi.marderlib.content.IUpdatableEntity

object MarderLibClientPacketsOut {

    val request_entity_update = Identifier(MarderLib.MODID, "request_entity_update")
    val request_block_entity_update = Identifier(MarderLib.MODID, "request_block_entity_update")
    val scrolled_item = Identifier(MarderLib.MODID, "scrolled_item")

    fun <T> sendRequestEntityUpdate(entity: T) where T: IUpdatableEntity, T: Entity
    {
        val buf = PacketByteBufs.create()
        buf.writeInt(entity.entityId)
        ClientPlayNetworking.send(request_entity_update, buf)
    }

    fun <T> sendRequestBlockEntityUpdate(be: T) where T: IUpdatableBlockEntity, T: BlockEntity
    {
        val buf = PacketByteBufs.create()
        buf.writeBlockPos(be.pos)
        buf.writeIdentifier(Registry.BLOCK_ENTITY_TYPE.getId(be.type))
        ClientPlayNetworking.send(request_block_entity_update, buf)
    }

    fun sendScrolledItemPacket(scrollAmount: Double, selectedSlot: Int)
    {
        val buf = PacketByteBufs.create()
        buf.writeDouble(scrollAmount)
        buf.writeInt(selectedSlot)
        ClientPlayNetworking.send(scrolled_item, buf)
    }
}