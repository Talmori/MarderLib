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

package talsumi.marderlib
import net.fabricmc.api.ModInitializer
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import talsumi.marderlib.networking.ClientPacketHandlers
import talsumi.marderlib.networking.ServerPacketHandlers
import talsumi.marderlib.registration.ColourMultiReg
import talsumi.marderlib.registration.EasyRegisterableHolder
import talsumi.marderlib.util.RegUtil

@Suppress("UNUSED")
object MarderLib: ModInitializer {

    const val MODID = "marderlib"

    val LOGGER: Logger = LogManager.getLogger()

    override fun onInitialize() {
        val sTime = System.currentTimeMillis()
        LOGGER.info("MarderLib initializing...")

        ServerPacketHandlers.register()

        Test.regAll(Registry.ITEM, Item::class, MODID)

        val eTime = System.currentTimeMillis()
        LOGGER.info("MarderLib initialization complete in ${eTime-sTime} milliseconds.")
    }

    object Test: EasyRegisterableHolder<Item>() {

        val item1 = reg(Item(RegUtil.itemSettings(ItemGroup.FOOD)))
        val coloured = reg(ColourMultiReg("testitem_%s_colour") { Item(RegUtil.itemSettings(ItemGroup.FOOD)) })
        val item2 = reg(Item(RegUtil.itemSettings(ItemGroup.FOOD)))
    }
}