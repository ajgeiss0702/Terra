package com.dfsek.terra.fabric.mixin.implementations.entity;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;

import com.dfsek.terra.api.entity.Player;


@Mixin(PlayerEntity.class)
@Implements(@Interface(iface = Player.class, prefix = "terra$", remap = Interface.Remap.NONE))
public abstract class PlayerEntityMixin extends EntityMixin {

}
