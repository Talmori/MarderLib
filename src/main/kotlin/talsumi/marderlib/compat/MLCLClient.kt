package talsumi.marderlib.compat

import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.BufferBuilder
import net.minecraft.client.render.BufferRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.EntityRendererFactory.Context
import net.minecraft.client.render.entity.MinecartEntityRenderer
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

/**
 * MarderLib Compatibility Layer, client version.
 *
 * Includes a bunch of methods to allow building on both 1.18 & 1.19 with minimal changes to code. Very incomplete.
 */
object MLCLClient {

    fun buildBuffer(bufferBuilder: BufferBuilder)
    {
        bufferBuilder.end()
        BufferRenderer.draw(bufferBuilder)
    }

    fun getEntityRendererFactoryContext(): EntityRendererFactory.Context
    {
        val mc = MinecraftClient.getInstance()
        return EntityRendererFactory.Context(mc.entityRenderDispatcher, mc.itemRenderer, mc.resourceManager, mc.entityModelLoader, mc.textRenderer)
    }
}