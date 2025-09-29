package com.almostreliable.almostunified_ie.mixin;

import com.almostreliable.unified.api.AlmostUnified;

import net.minecraft.core.RegistryAccess;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import blusunrize.immersiveengineering.api.IEApi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IEApi.class)
public class IEApiMixin {

    @Inject(method = "getPreferredTagStack", at = @At("HEAD"), cancellable = true)
    private static void getPreferredTagStack(RegistryAccess tags, TagKey<Item> tag, CallbackInfoReturnable<ItemStack> cir) {
        Item preferredItem = AlmostUnified.INSTANCE.getTagTargetItem(tag);
        if (preferredItem != null) {
            cir.setReturnValue(preferredItem.getDefaultInstance());
        }
    }

    @Inject(method = "getPreferredStackbyMod", at = @At("HEAD"), cancellable = true)
    private static void getPreferredStackbyMod(ItemStack[] array, CallbackInfoReturnable<ItemStack> cir) {
        if (array.length == 0) {
            return;
        }

        Item firstItem = array[0].getItem();
        var firstTag = AlmostUnified.INSTANCE.getRelevantItemTag(firstItem);
        if (firstTag == null) {
            return;
        }

        if (array.length > 1) {
            Item secondItem = array[1].getItem();
            var secondTag = AlmostUnified.INSTANCE.getRelevantItemTag(secondItem);

            if (!firstTag.equals(secondTag)) {
                return;
            }
        }

        Item preferredItem = AlmostUnified.INSTANCE.getTagTargetItem(firstTag);
        if (preferredItem != null) {
            cir.setReturnValue(preferredItem.getDefaultInstance());
        }
    }
}
