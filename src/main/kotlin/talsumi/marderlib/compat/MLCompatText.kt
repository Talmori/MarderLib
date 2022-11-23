package talsumi.marderlib.compat

import net.minecraft.text.LiteralText
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

object MLCompatText {

    fun makeText(contents: String): Text = Text.of(contents)

    fun makeLiteralText(contents: String): MutableText = LiteralText(contents)

    fun makeTranslatableText(contents: String): MutableText = TranslatableText(contents)

    fun makeTranslatableText(contents: String, vararg args: Any): MutableText = TranslatableText(contents, *args)
}