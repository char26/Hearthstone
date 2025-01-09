package com.chartle.hearthstonemod.item;
import com.chartle.hearthstonemod.HearthstoneMod;
import com.chartle.hearthstonemod.item.custom.HearthstoneItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, HearthstoneMod.MOD_ID);

    private static Item.Properties getDefaultHearthstoneProperties() {
        return new Item.Properties()
                .stacksTo(1)
                .durability(50)
                .useItemDescriptionPrefix()
                .component(ModDataComponents.HEARTHSTONE_PROPERTIES.get(), HearthstoneItem.HearthstoneProperties.DEFAULT);

    }

    public static final RegistryObject<Item> HEARTHSTONE = ITEMS.register("hearthstone",
            () -> new HearthstoneItem(getDefaultHearthstoneProperties()
                    .component(ModDataComponents.HEARTHSTONE_PROPERTIES.get(), HearthstoneItem.HearthstoneProperties.DEFAULT)
                            .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("hearthstonemod:hearthstone")))
            )
    );

    public static final RegistryObject<Item> PINK_HEARTHSTONE = ITEMS.register("pink_hearthstone",
            () -> new HearthstoneItem(getDefaultHearthstoneProperties()
                    .component(ModDataComponents.HEARTHSTONE_PROPERTIES.get(), HearthstoneItem.HearthstoneProperties.DEFAULT)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("hearthstonemod:pink_hearthstone")))
            )
    );
//
//
    public static final RegistryObject<Item> GREEN_HEARTHSTONE = ITEMS.register("green_hearthstone",
            () -> new HearthstoneItem(getDefaultHearthstoneProperties()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("hearthstonemod:green_hearthstone")))
            )
    );

    public static final RegistryObject<Item> PURPLE_HEARTHSTONE = ITEMS.register("purple_hearthstone",
            () -> new HearthstoneItem(getDefaultHearthstoneProperties()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("hearthstonemod:purple_hearthstone")))
            )
    );

    public static final RegistryObject<Item> RED_HEARTHSTONE = ITEMS.register("red_hearthstone",
            () -> new HearthstoneItem(getDefaultHearthstoneProperties()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("hearthstonemod:red_hearthstone")))
            )
    );

    public static final RegistryObject<Item> YELLOW_HEARTHSTONE = ITEMS.register("yellow_hearthstone",
            () -> new HearthstoneItem(getDefaultHearthstoneProperties()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("hearthstonemod:yellow_hearthstone")))
            )
    );

    public static final RegistryObject<Item> ORANGE_HEARTHSTONE = ITEMS.register("orange_hearthstone",
            () -> new HearthstoneItem(getDefaultHearthstoneProperties()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("hearthstonemod:orange_hearthstone")))
            )
    );


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
