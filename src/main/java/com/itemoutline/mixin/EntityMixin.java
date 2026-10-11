package com.itemoutline.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "isCurrentlyGlowing", at = @At("HEAD"), cancellable = true)
    private void itemoutline$forceGlow(CallbackInfoReturnable<Boolean> cir) {
        if (this.itemoutline$isClientItem()) {
            cir.setReturnValue(true);
        }
    }

    // Outline color as 0xRRGGBB (hot pink-purple, sampled from your screenshots)
    @Inject(method = "getTeamColor", at = @At("HEAD"), cancellable = true, require = 0)
    private void itemoutline$color(CallbackInfoReturnable<Integer> cir) {
        if (this.itemoutline$isClientItem()) {
            cir.setReturnValue(0xF050F8);
        }
    }

    @Unique
    private boolean itemoutline$isClientItem() {
        Entity self = (Entity) (Object) this;
        return self instanceof ItemEntity && self.level().isClientSide();
    }
}
