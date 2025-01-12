package com.dfsek.terra.fabric.util;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import com.dfsek.terra.api.entity.Player;
import com.dfsek.terra.api.util.generic.pair.Pair;
import com.dfsek.terra.api.util.vector.Vector3;


public final class WorldEditUtil {
    public static Pair<Vector3, Vector3> getSelection(Player player) {
        WorldEdit worldEdit = WorldEdit.getInstance();
        try {
            Region selection = worldEdit.getSessionManager()
                                        .get(com.sk89q.worldedit.fabric.FabricAdapter.adaptPlayer((ServerPlayerEntity) player))
                                        .getSelection(com.sk89q.worldedit.fabric.FabricAdapter.adapt((World) player.world()));
            BlockVector3 min = selection.getMinimumPoint();
            BlockVector3 max = selection.getMaximumPoint();
            Vector3 l1 = new Vector3(min.getBlockX(), min.getBlockY(), min.getBlockZ());
            Vector3 l2 = new Vector3(max.getBlockX(), max.getBlockY(), max.getBlockZ());
            return Pair.of(l1, l2);
        } catch(IncompleteRegionException e) {
            throw new IllegalStateException("No selection has been made", e);
        }
    }
}
