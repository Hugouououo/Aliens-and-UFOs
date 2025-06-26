package com.hugouououo.alienmod.block.custom;

import com.hugouououo.alienmod.block.entity.ModBlockEntities;
import com.hugouououo.alienmod.block.entity.custom.AlienChestBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.screen.ScreenHandler.calculateComparatorOutput;

public class AlienChestBlock extends AbstractChestBlock<AlienChestBlockEntity> implements Waterloggable{

    public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
    public static final EnumProperty<ChestType> CHEST_TYPE = Properties.CHEST_TYPE;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public AlienChestBlock(Settings settings) {
        super(settings, () -> ModBlockEntities.ALIEN_CHEST);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(CHEST_TYPE, ChestType.SINGLE)
                .with(WATERLOGGED, false));
    }

    // Colocação
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing().getOpposite();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState()
                .with(FACING, direction)
                .with(CHEST_TYPE, ChestType.SINGLE)
                .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }


    // Forma física do bloco
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(1, 0, 1, 15, 14, 15);
    }

    // Se tem água dentro
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

//    @Override
//    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
//        if (!state.isOf(newState.getBlock())) {
//            BlockEntity blockEntity = world.getBlockEntity(pos);
//            if (blockEntity instanceof ChestBlockEntity) {
//                ItemScatterer.spawn(world, pos, (ChestBlockEntity) blockEntity);
//                world.updateComparators(pos, this);
//            }
//            super.onStateReplaced(state, (ServerWorld) world, pos, moved);
//        }
//    }

    //@Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AlienChestBlockEntity) {
                player.openHandledScreen((NamedScreenHandlerFactory) blockEntity);
            }
            return ActionResult.CONSUME;
        }
    }

    // Bloco tem saída de comparador (redstone)
    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return calculateComparatorOutput(world.getBlockEntity(pos));
    }

    // Rotaciona
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    // Propriedades do bloco
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, CHEST_TYPE, WATERLOGGED);
    }

    // Cria o BlockEntity
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AlienChestBlockEntity(pos, state);
    }

    // Tick no lado cliente para animação da tampa
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
                                                                  BlockEntityType<T> type) {
        return world.isClient ? validateTicker(type, ModBlockEntities.ALIEN_CHEST, AlienChestBlockEntity::clientTick) : null;
    }

    @Override
    protected MapCodec<? extends AbstractChestBlock<AlienChestBlockEntity>> getCodec() {
        return null;
    }

    @Override
    public DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> getBlockEntitySource(BlockState state, World world, BlockPos pos, boolean ignoreBlocked) {
        return null;
    }
}