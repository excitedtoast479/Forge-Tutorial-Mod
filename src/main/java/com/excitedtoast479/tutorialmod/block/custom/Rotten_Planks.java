package com.excitedtoast479.tutorialmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Random;

public class Rotten_Planks extends Block {
    public Rotten_Planks(Properties properties) {
        super(properties);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float v) {
        super.fallOn(level, state, pos, entity, v);
        if (v > 1.5) {
            if (entity instanceof LivingEntity livingEntity) {
                level.destroyBlock(pos, false);
            }
        } else if (v > 1 && v < 1.5) {
            Random rand = new Random();
            int n = rand.nextInt(3);
            if (entity instanceof LivingEntity livingEntity && n == 1 && !level.isClientSide()) {
                level.destroyBlock(pos, false);
            }
        }
    }
/*
    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
        Random rand = new Random();
        int n = rand.nextInt(3);
            if (n == 1 && !entity.isSteppingCarefully()) {
                if (entity instanceof LivingEntity livingEntity) {
                    level.destroyBlock(pos, false);
                }
            }
        } */
}