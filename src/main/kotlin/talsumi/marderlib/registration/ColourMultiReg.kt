package talsumi.marderlib.registration

import net.minecraft.util.DyeColor

class ColourMultiReg<T: Any>(private val format: String, private val factory: (DyeColor) -> T): MultiReg<T, DyeColor>() {

    private val values: Array<Any> = Array(16) { i -> factory.invoke(DyeColor.values()[i]) }

    override fun get(type: DyeColor): T = values[type.ordinal] as T

    override fun getAll(): Array<Pair<T, String>> = Array(16) { i -> Pair(values[i] as T, format.replace("%s", DyeColor.values()[i].name.lowercase())) }

    override fun count(): Int = 16
    override fun values(): Array<T> = values as Array<T>
}