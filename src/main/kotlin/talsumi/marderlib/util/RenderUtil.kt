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

package talsumi.marderlib.util

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.render.*
import net.minecraft.client.util.math.MatrixStack

object RenderUtil {

    fun getSnapshot(): Snapshot
    {
        return Snapshot(
            RenderSystem.getShaderTexture(0),
            RenderSystem.getShaderColor().copyOf(),
            RenderSystem.getShader())
    }

    fun unboundDrawTexture(matrices: MatrixStack, x: Float, y: Float, width: Float, height: Float, u: Int, v: Int, zOffset: Float = 0f, texWidth: Float = 256f, texHeight: Float = 256f)
    {
        val snap = getSnapshot()
        matrices.push()

        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        val bufferBuilder = Tessellator.getInstance().buffer
        val matrix = matrices.peek().positionMatrix

        val xMin = x
        val xMax = x + width
        val yMin = y
        val yMax = y + height
        val uMin = u / texWidth
        val uMax = (u + width) / texWidth
        val vMin = v / texHeight
        val vMax = (v + height) / texHeight

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE)
        bufferBuilder.vertex(matrix, xMin, yMax, zOffset).texture(uMin, vMax).next()
        bufferBuilder.vertex(matrix, xMax, yMax, zOffset).texture(uMax, vMax).next()
        bufferBuilder.vertex(matrix, xMax, yMin, zOffset).texture(uMax, vMin).next()
        bufferBuilder.vertex(matrix, xMin, yMin, zOffset).texture(uMin, vMin).next()
        BufferRenderer.drawWithShader(bufferBuilder.end())

        matrices.pop()
        snap.restore()
    }

    class Snapshot(val tex: Int, val colour: FloatArray, val shader: Shader?) {
        fun restore()
        {
            if (shader != null)
                RenderSystem.setShader { shader }
            RenderSystem.setShaderTexture(0, tex)
            RenderSystem.setShaderColor(colour[0], colour[1], colour[2], colour[3])
        }
    }
}