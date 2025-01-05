package com.chartle.hearthstonemod.item.custom;

import net.minecraft.network.chat.Component;
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
    private Vec3 teleportToVec = null;

    public HearthstoneItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (!pLevel.isClientSide()) {
            if (pPlayer.isCrouching()) {
                teleportToVec = new Vec3(pPlayer.getX(), pPlayer.getY(), pPlayer.getZ());
                pPlayer.displayClientMessage(Component.literal("Hearthstone location saved."), false);
                return InteractionResult.SUCCESS;
            } else {
                if (teleportToVec == null) {
                    pPlayer.displayClientMessage(Component.literal("Hearthstone is not set. Crouch and right-click to set."), false);
                    return InteractionResult.PASS;
                }
                pPlayer.teleportTo(teleportToVec.x, teleportToVec.y, teleportToVec.z);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.literal("Teleport you to the last set block."));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
