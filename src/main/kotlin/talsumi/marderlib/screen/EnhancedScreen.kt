package talsumi.marderlib.screen

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.render.GameRenderer.getPositionTexProgram
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import talsumi.marderlib.screen.widget.BaseWidget
import kotlin.reflect.KClass

abstract class EnhancedScreen<T: ScreenHandler>(handler: T, inventory: PlayerInventory?, title: Text?, val backgroundTex: Identifier) : HandledScreen<T>(handler, inventory, title) {

    val widgets = ArrayList<BaseWidget>()

    fun addWidgets(vararg widgets: BaseWidget)
    {
        for (widget in widgets)
            this.widgets.add(widget)
    }

    /**
     * Returns the first widget of [type]
     */
    fun <T: BaseWidget> getWidget(type: KClass<T>): T?
    {
        for (widget in widgets)
            if (type.isInstance(widget))
                return widget as T

        return null
    }

    /**
     * Returns all widgets of [type]
     */
    fun <T: BaseWidget> getWidgets(type: KClass<T>): List<T>
    {
        val list = ArrayList<T>()

        for (widget in widgets)
            if (type.isInstance(widget))
                list.add(widget as T)

        return list
    }

    /**
     * Returns all widgets
     */
    fun getWidgets(): List<BaseWidget> = widgets

    fun getScreenX(): Int = x
    fun getScreenY(): Int = y

    fun getScreenTrueX(): Int = (width - backgroundWidth) / 2
    fun getScreenTrueY(): Int = (height - backgroundHeight) / 2

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram)
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, backgroundTex)
        val x = (width - backgroundWidth) / 2
        val y = (height - backgroundHeight) / 2
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)
        drawScreenElements(matrices, x, y, delta, mouseX, mouseY)
        for (widget in widgets)
            if (widget.widgetEnabled)
                widget.doRender(matrices, x, y, mouseX, mouseY, delta)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float)
    {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);

        for (widget in widgets)
            if (widget.widgetEnabled && widget.isHovered(widget.x + x, widget.y + y, mouseX, mouseY))
                widget.renderTooltip(this, matrices, mouseX, mouseY)
    }

    open fun drawScreenElements(matrices: MatrixStack, x: Int, y: Int, delta: Float, mouseX: Int, mouseY: Int) = Unit

    override fun init()
    {
        super.init()
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean
    {
        var clicked = false
        val type = if (button == 0) BaseWidget.Button.LEFT else BaseWidget.Button.RIGHT
            for (widget in widgets) {
            if (widget.widgetEnabled && !widget.frozen) {
                if (widget.isHovered(widget.x + x, widget.y + y, mouseX.toInt(), mouseY.toInt())) {
                    widget.onMouseAction(mouseX, mouseY, type, BaseWidget.Type.PRESSED)
                    clicked = true
                }
                else {
                    widget.onMouseActionElsewhere(mouseX, mouseY, type, BaseWidget.Type.PRESSED)
                }
            }
        }

        return if (clicked) true else super.mouseClicked(mouseX, mouseY, button)
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean
    {
        var clicked = false
        val type = if (button == 0) BaseWidget.Button.LEFT else BaseWidget.Button.RIGHT
        for (widget in widgets) {
            if (widget.widgetEnabled && !widget.frozen) {
                if (widget.isHovered(widget.x + x, widget.y + y, mouseX.toInt(), mouseY.toInt())) {
                    widget.onMouseAction(mouseX, mouseY, type, BaseWidget.Type.RELEASED)
                    clicked = true
                }
                else {
                    widget.onMouseActionElsewhere(mouseX, mouseY, type, BaseWidget.Type.RELEASED)
                }
            }
        }

        return super.mouseReleased(mouseX, mouseY, button)
    }

    override fun charTyped(chr: Char, modifiers: Int): Boolean
    {
        for (widget in widgets) {
            if (widget.widgetEnabled && !widget.frozen) {
                widget.charPressed(chr, modifiers, modifiers and 2 > 0, modifiers and 1 > 0, modifiers and 4 > 0)
            }
        }

        return super.charTyped(chr, modifiers)
    }

    override fun mouseDragged(mouseX: Double, mouseY: Double, button: Int, deltaX: Double, deltaY: Double): Boolean
    {
        var dragged = false
        val type = if (button == 0) BaseWidget.Button.LEFT else BaseWidget.Button.RIGHT
        for (widget in widgets) {
            if (widget.widgetEnabled && !widget.frozen) {
                if (widget.isHovered(widget.x + x, widget.y + y, mouseX.toInt(), mouseY.toInt())) {
                    widget.onDragged(mouseX, mouseY, deltaX, deltaY, type)
                    dragged = true
                }
            }
        }

        return if (dragged) true else return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean
    {
        for (widget in widgets) {
            if (widget.widgetEnabled && !widget.frozen) {
                widget.keyPressed(keyCode, scanCode, modifiers, modifiers and 2 > 0, modifiers and 1 > 0, modifiers and 4 > 0)
            }
        }

        return super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun mouseScrolled(mouseX: Double, mouseY: Double, amount: Double): Boolean
    {
        for (widget in widgets) {
            if (widget.widgetEnabled && !widget.frozen) {
                widget.scrolled(mouseX, mouseY, amount, widget.isHovered(widget.x + x, widget.y + y, mouseX.toInt(), mouseY.toInt()))
            }
        }

        return super.mouseScrolled(mouseX, mouseY, amount)
    }
}