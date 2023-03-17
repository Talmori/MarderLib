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

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.Entity
import net.minecraft.registry.Registries
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import talsumi.marderlib.MarderLib
import talsumi.marderlib.content.IUpdatableBlockEntity
import talsumi.marderlib.content.IUpdatableEntity

object MarderLibServerPacketsOut {

    val update_entity = Identifier(MarderLib.MODID, "update_entity")
    val update_block_entity = Identifier(MarderLib.MODID, "update_block_entity")

    fun <T> sendUpdateEntityPacket(ent: T, player: ServerPlayerEntity) where T: IUpdatableEntity, T: Entity
    {
        val buf = PacketByteBufs.create()
        buf.writeInt(ent.id)
        ent.writeUpdatePacket(buf)
        ServerPlayNetworking.send(player, update_entity, buf)
    }

    fun <T> sendUpdateBlockEntityPacket(be: T, player: ServerPlayerEntity) where T: IUpdatableBlockEntity, T: BlockEntity
    {
        val buf = PacketByteBufs.create()
        buf.writeBlockPos(be.pos)
        buf.writeIdentifier(Registries.BLOCK_ENTITY_TYPE.getId(be.type))
        be.writeUpdatePacket(buf)
        ServerPlayNetworking.send(player, update_block_entity, buf)
    }
}