package talsumi.marderlib.compat

import net.minecraft.text.MutableText
import net.minecraft.text.Text

object MLCompatText {

    fun makeText(contents: String): Text = Text.of(contents)

    fun makeLiteralText(contents: String): MutableText = Text.literal(contents)

    fun makeTranslatableText(contents: String): MutableText = Text.translatable(contents)

    fun makeTranslatableText(contents: String, args: Array<Any>): MutableText = Text.translatable(contents, *args)
}