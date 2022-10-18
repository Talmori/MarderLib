package talsumi.marderlib.compat

import net.minecraft.client.render.BufferBuilder
import net.minecraft.client.render.BufferRenderer
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

/**
 * MarderLib Compatibility Layer.
 *
 * Includes a bunch of methods to allow building on both 1.18 & 1.19 with minimal changes to code. Very incomplete.
 */
object MLCL {

    fun makeText(contents: String): Text = Text.of(contents)

    fun makeLiteralText(contents: String): LiteralText = LiteralText(contents)

    fun makeTranslatableText(contents: String): TranslatableText = TranslatableText(contents)

    fun makeTranslatableText(contents: String, vararg args: Any): TranslatableText = TranslatableText(contents, args)
}