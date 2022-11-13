package talsumi.marderlib.compat

import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.BufferBuilder
import net.minecraft.client.render.BufferRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.EntityRendererFactory.Context

object MLCompatRendering {

    fun drawBuffer(builder: BufferBuilder)
    {
        builder.end()
        BufferRenderer.draw(builder)
    }

    fun makeEntityRendererContext(): EntityRendererFactory.Context
    {
        val mc = MinecraftClient.getInstance()
        return EntityRendererFactory.Context(mc.entityRenderDispatcher, mc.itemRenderer, mc.resourceManager, mc.entityModelLoader, mc.textRenderer)
    }

    fun makeBlockEntityRendererContext(): BlockEntityRendererFactory.Context
    {
        val mc = MinecraftClient.getInstance()
        return BlockEntityRendererFactory.Context(mc.blockEntityRenderDispatcher, mc.blockRenderManager, mc.entityModelLoader, mc.textRenderer)
    }
}