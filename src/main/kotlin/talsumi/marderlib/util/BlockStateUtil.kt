/*
 * MIT License
 *
 * Copyright (c) 2022 Talsumi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package talsumi.marderlib.util

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.state.State
import net.minecraft.state.property.Property
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import java.util.regex.Pattern

object BlockStateUtil {

    /**
     * Creates a BlockState from the string provided by [BlockState.toString]
     */
    fun blockStateFromString(string: String): BlockState?
    {
        val args = string.substring(6).lowercase().replace("}", "").replace("]", "").split("[")

        val block = Registry.BLOCK.get(RegistryKey.of(Registry.BLOCK_KEY, Identifier(args[0]))) ?: return null
        var state = block.defaultState

        //If we have no properties, return the default state
        if (args.size == 1)
            return state

        val props = state.properties.associateBy({ it.name }, {it})

        args[1].split(',').forEach { propArgs ->
            val wanted = propArgs.split('=')
            val wantedProp = wanted[0]
            val wantedValue = wanted[1]

            val foundProp = props[wantedProp] as? Property<Comparable<Any>>?: return null
            var found = false

            for (value in foundProp.values) {
                val name = foundProp.name(value)
                if (name == wantedValue) {
                    state = state.with(foundProp, value)
                    found = true
                    break
                }
            }

            if (!found)
                return null
        }

        return state
    }
}