package dev.xueria.clumps.mixins;

import dev.xueria.clumps.Clumps;
import dev.xueria.clumps.WorldMergeScheduler;
import net.minecraft.Entity;
import net.minecraft.EntityXPOrb;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class MixinWorld {

    @Inject(method = "spawnEntityInWorld", at = @At("RETURN"))
    private void clumpsMarkSpawned(Entity entity, CallbackInfoReturnable<Boolean> callback) {
        if (!(entity instanceof EntityXPOrb)) {
            return;
        }
        World world = (World) (Object) this;
        WorldMergeScheduler scheduler = Clumps.INSTANCE.getScheduler();
        if (!world.isRemote && scheduler != null) {
            scheduler.onOrbSpawned(world);
        }
    }

    @Inject(method = "updateEntities", at = @At("TAIL"))
    private void clumpsTick(CallbackInfo callback) {
        World world = (World) (Object) this;
        WorldMergeScheduler scheduler = Clumps.INSTANCE.getScheduler();
        if (!world.isRemote && scheduler != null) {
            scheduler.tick(world);
        }
    }
}
