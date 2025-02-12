package com.chartle.hearthstonemod.item.custom;

import com.chartle.hearthstonemod.item.ModDataComponents;
import com.chartle.hearthstonemod.item.ModItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class HearthstoneItem extends Item {
    // TODO: add cast time

    public HearthstoneItem(Properties pProperties) {
        super(pProperties);
    }

    private static int dimensionAsInt(ResourceKey<Level> dimension) {
        if (dimension == Level.OVERWORLD) {
            return 0;
        } else if (dimension == Level.NETHER) {
            return 1;
        } else if (dimension == Level.END) {
            return 2;
        } else {
            return -1;
        }
    }

    @Nullable
    private static HearthstoneProperties getProperties(final ItemStack stack) {
        return stack.getOrDefault(
                ModDataComponents.HEARTHSTONE_PROPERTIES.get(),
                HearthstoneProperties.DEFAULT
        );
    }

    private static void setProperties(final ItemStack stack, final HearthstoneProperties properties) {
        stack.set(ModDataComponents.HEARTHSTONE_PROPERTIES.get(), properties);
    }

    private static void updateSavedPosition(final ItemStack itemStack,
                                            final double x, final double y, final double z,
                                            final int dimension) {
        HearthstoneProperties newProperties = new HearthstoneProperties(x, y, z, dimension);
        setProperties(itemStack, newProperties);
    }

    @Override
    public InteractionResult use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (!pLevel.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) pLevel;
            ItemStack itemStack = pPlayer.getItemInHand(pHand);
            HearthstoneProperties properties = getProperties(itemStack);
            if (pPlayer.isCrouching()) {
                updateSavedPosition(
                        itemStack,
                        pPlayer.getX(),
                        pPlayer.getY(),
                        pPlayer.getZ(),
                        dimensionAsInt(serverLevel.dimension()));

                pPlayer.displayClientMessage(Component.literal("Hearthstone location saved."), false);
                return InteractionResult.SUCCESS;
            } else {
                if (properties == null) {
                    pPlayer.displayClientMessage(Component.literal("Hearthstone is not set. Crouch and right-click to set."), false);
                    return InteractionResult.PASS;
                }
                // TODO: allow teleporting to/from nether
                if (dimensionAsInt(serverLevel.dimension()) != properties.dimension) {
                    pPlayer.displayClientMessage(Component.literal("Cannot teleport to another dimension. (Coming soon)"), false);
                    return InteractionResult.PASS;
                }
                pPlayer.teleportTo(properties.x, properties.y, properties.z);
                // TODO: add sound effect
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.literal("Teleports you to the last saved block."));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }

    public record HearthstoneProperties(double x, double y, double z, int dimension) {
        public static final Codec<HearthstoneProperties> CODEC = RecordCodecBuilder.create(builder ->
                builder.group(
                        Codec.DOUBLE
                                .fieldOf("x")
                                .forGetter(HearthstoneProperties::x),
                        Codec.DOUBLE
                                .fieldOf("y")
                                .forGetter(HearthstoneProperties::y),
                        Codec.DOUBLE
                                .fieldOf("z")
                                .forGetter(HearthstoneProperties::z),

                        Codec.INT
                                .fieldOf("dimension")
                                .forGetter(HearthstoneProperties::dimension)

                ).apply(builder, HearthstoneProperties::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, HearthstoneProperties> NETWORK_CODEC = StreamCodec.composite(
                ByteBufCodecs.DOUBLE,
                HearthstoneProperties::x,
                ByteBufCodecs.DOUBLE,
                HearthstoneProperties::y,
                ByteBufCodecs.DOUBLE,
                HearthstoneProperties::z,
                ByteBufCodecs.INT,
                HearthstoneProperties::dimension,
                HearthstoneProperties::new
        );

        public static final HearthstoneProperties DEFAULT = null;
    }
}
