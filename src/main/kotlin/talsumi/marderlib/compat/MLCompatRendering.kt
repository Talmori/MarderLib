package talsumi.marderlib.compat

import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.BufferBuilder
import net.minecraft.client.render.BufferRenderer

object MLCompatRendering {

    fun drawBuffer(builder: BufferBuilder)
    {
        builder.end()
        BufferRenderer.draw(builder)
    }
}