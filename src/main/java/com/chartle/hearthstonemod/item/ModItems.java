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

    public static final RegistryObject<Item> HEARTHSTONE = ITEMS.register("hearthstone",
            () -> new HearthstoneItem(
                    new Item.Properties()
                            .stacksTo(1)
                            .durability(50)
                            .useItemDescriptionPrefix()
                            .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("hearthstonemod:hearthstone")))
            )
    );


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
