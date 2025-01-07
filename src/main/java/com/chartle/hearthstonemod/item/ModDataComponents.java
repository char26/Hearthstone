package com.chartle.hearthstonemod.item;

import com.chartle.hearthstonemod.HearthstoneMod;
import com.chartle.hearthstonemod.item.custom.HearthstoneItem;
import com.mojang.serialization.Codec;
import kotlin.Unit;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.UnaryOperator;

// Thank you to Choonster from
// https://forums.minecraftforge.net/topic/150109-storingretrieving-nbt-data-in-an-itemstack-in-121/
public class ModDataComponents
{
    private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, HearthstoneMod.MOD_ID);

    private static boolean isInitialised = false;

    public static final RegistryObject<DataComponentType<HearthstoneItem.HearthstoneProperties>> HEARTHSTONE_PROPERTIES = register("hearthstone_properties",
            builder -> builder
                    .persistent(HearthstoneItem.HearthstoneProperties.CODEC)
                    .networkSynchronized(HearthstoneItem.HearthstoneProperties.NETWORK_CODEC)
                    .cacheEncoding()
    );

    /**
     * Registers the {@link DeferredRegister} instance with the mod event bus.
     * <p>
     * This should be called during mod construction.
     *
     * @param modEventBus The mod event bus
     */
    public static void initialise(final IEventBus modEventBus) {
        if (isInitialised) {
            throw new IllegalStateException("Already initialised");
        }

        DATA_COMPONENT_TYPES.register(modEventBus);

        isInitialised = true;
    }

    private static <T> RegistryObject<DataComponentType<T>> register(final String name, final UnaryOperator<DataComponentType.Builder<T>> builder) {
        return DATA_COMPONENT_TYPES.register(name, () -> builder.apply(DataComponentType.builder()).build());
    }

    private static DataComponentType.Builder<Unit> unit(final DataComponentType.Builder<Unit> builder) {
        return builder
                .persistent(Codec.unit(Unit.INSTANCE))
                .networkSynchronized(StreamCodec.unit(Unit.INSTANCE));
    }
}
