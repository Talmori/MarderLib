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

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.BlockState
import net.minecraft.block.Material
import net.minecraft.entity.EntityType
import net.minecraft.item.ItemGroup
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView

object RegUtil {

	fun itemSettings(group: ItemGroup, maxCount: Int = 64): FabricItemSettings = FabricItemSettings().maxCount(maxCount)

	fun blockSettings(material: Material, hardness: Float, resistance: Float = hardness, sound: BlockSoundGroup = BlockSoundGroup.STONE, luminance: Int = 0, isTicking: Boolean = false, collidable: Boolean = true, requiresTool: Boolean = false): FabricBlockSettings
	{
		val settings = FabricBlockSettings.of(material).hardness(hardness).resistance(resistance).sounds(sound).luminance(luminance).collidable(collidable)
		if (isTicking)
			settings.ticksRandomly()
		return settings
	}

	fun FabricBlockSettings.marderLibAdditionalBlockSettings(allowsSpawning: Boolean? = null, solidBlock: Boolean? = null, suffocates: Boolean? = null, blockVision: Boolean? = null, emissiveLighting: Boolean? = null): FabricBlockSettings
	{
		val settings = this
		allowsSpawning?.let { settings.allowsSpawning(if (it) ::alwaysEnt else ::neverEnt) }
		solidBlock?.let { settings.solidBlock(if (it) ::always else ::never) }
		suffocates?.let { settings.suffocates(if (it) ::always else ::never) }
		blockVision?.let { settings.blockVision(if (it) ::always else ::never) }
		emissiveLighting?.let { settings.emissiveLighting(if (it) ::always else ::never) }
		return settings
	}

	fun always(state: BlockState, world: BlockView, pos: BlockPos): Boolean = true
	fun never(state: BlockState, world: BlockView, pos: BlockPos): Boolean = false
	fun alwaysEnt(state: BlockState, world: BlockView, pos: BlockPos, type: EntityType<*>): Boolean = true
	fun neverEnt(state: BlockState, world: BlockView, pos: BlockPos, type: EntityType<*>): Boolean = false
}