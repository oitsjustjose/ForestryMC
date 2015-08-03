/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Various Contributors including, but not limited to:
 * SirSengir (original work), CovertJaguar, Player, Binnie, MysteriousAges
 ******************************************************************************/
package forestry.core.worldgen;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import forestry.core.config.Defaults;
import forestry.core.config.ForestryBlock;
import forestry.core.gadgets.TileForestry;

public class BlockTypeTileForestry implements IBlockType {

	private final Block block;
	private final int meta;
	private ForgeDirection direction;

	public BlockTypeTileForestry(ForestryBlock forestryBlock, int meta) {
		this.block = forestryBlock.block();
		this.meta = meta;
	}

	@Override
	public void setDirection(ForgeDirection facing) {
		this.direction = facing;
	}

	@Override
	public void setBlock(World world, int x, int y, int z) {
		boolean placed = world.setBlock(x, y, z, block, meta, Defaults.FLAG_BLOCK_SYNCH);
		if (!placed) {
			return;
		}

		Block worldBlock = world.getBlock(x, y, z);
		if (!Block.isEqualTo(block, worldBlock)) {
			return;
		}

		TileEntity tile = world.getTileEntity(x, y, z);
		if (!(tile instanceof TileForestry)) {
			world.setBlockToAir(x, y, z);
			return;
		}

		TileForestry tileForestry = (TileForestry) tile;

		tileForestry.setOrientation(direction);
	}

	@Override
	public int getMeta() {
		return meta;
	}

	@Override
	public Block getBlock() {
		return block;
	}

	@Override
	public ItemStack getItemStack() {
		return new ItemStack(block, 1, meta);
	}
}