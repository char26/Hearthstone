package com.chartle.hearthstonemod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import java.util.List;

public class HearthstoneItem extends Item {
    private Vec3 boundVec = null;
    private ResourceKey<Level> boundDimension = null;
    // TODO: add cast time

    public HearthstoneItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(Level pLevel, Player pPlayer, InteractionHand pHand) {

        if (!pLevel.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) pLevel;
            if (pPlayer.isCrouching()) {
                boundDimension = serverLevel.dimension();
                boundVec = new Vec3(pPlayer.getX(), pPlayer.getY(), pPlayer.getZ());
                pPlayer.displayClientMessage(Component.literal("Hearthstone location saved."), false);
                return InteractionResult.SUCCESS;
            } else {
                if (boundVec == null) {
                    pPlayer.displayClientMessage(Component.literal("Hearthstone is not set. Crouch and right-click to set."), false);
                    return InteractionResult.PASS;
                }

                // TODO: allow teleporting to/from nether
                if (serverLevel.dimension() != boundDimension) {
                    pPlayer.displayClientMessage(Component.literal("Cannot teleport to another dimension. (Coming soon)"), false);
                    return InteractionResult.PASS;
                }
                pPlayer.teleportTo(boundVec.x, boundVec.y, boundVec.z);
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
}