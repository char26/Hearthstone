package com.chartle.hearthstonemod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HearthstoneItem extends Item {
    private Map<Integer, Tuple<Vec3, ResourceKey<Level>>> playerIdToHearthstoneInfo = null;
    // TODO: add cast time

    public HearthstoneItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (!pLevel.isClientSide()) {
            if (playerIdToHearthstoneInfo == null) {playerIdToHearthstoneInfo = new HashMap<>();}
            ServerLevel serverLevel = (ServerLevel) pLevel;
            if (pPlayer.isCrouching()) {
                ResourceKey<Level> boundDimension = serverLevel.dimension();
                Vec3 boundVec = new Vec3(pPlayer.getX(), pPlayer.getY(), pPlayer.getZ());
                Tuple<Vec3, ResourceKey<Level>> hearthstoneInfo = new Tuple<>(boundVec, boundDimension);
                playerIdToHearthstoneInfo.put(pPlayer.getId(), hearthstoneInfo);
                pPlayer.displayClientMessage(Component.literal("Hearthstone location saved."), false);
                return InteractionResult.SUCCESS;
            } else {
                Tuple<Vec3, ResourceKey<Level>> hearthstoneInfo = playerIdToHearthstoneInfo.get(pPlayer.getId());
                if (hearthstoneInfo == null) {
                    pPlayer.displayClientMessage(Component.literal("Hearthstone is not set. Crouch and right-click to set."), false);
                    return InteractionResult.PASS;
                }
                // TODO: allow teleporting to/from nether
                if (serverLevel.dimension() != hearthstoneInfo.getB()) {
                    pPlayer.displayClientMessage(Component.literal("Cannot teleport to another dimension. (Coming soon)"), false);
                    return InteractionResult.PASS;
                }
                Vec3 boundVec = hearthstoneInfo.getA();
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