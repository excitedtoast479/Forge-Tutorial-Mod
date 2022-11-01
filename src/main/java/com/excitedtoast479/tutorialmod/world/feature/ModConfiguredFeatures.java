package com.excitedtoast479.tutorialmod.world.feature;

import com.excitedtoast479.tutorialmod.TutorialMod;
import com.excitedtoast479.tutorialmod.block.ModBlocks;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, TutorialMod.MOD_ID);

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_MYTHRIL_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.MYTHRIL_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_MYTHRIL_ORE.get().defaultBlockState())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> MYTHRIL_ORE = CONFIGURED_FEATURES.register("mythril_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_MYTHRIL_ORES.get(), 4)));

    public static void regsier(IEventBus eventBus) {
        CONFIGURED_FEATURES.register(eventBus);
    }
}
