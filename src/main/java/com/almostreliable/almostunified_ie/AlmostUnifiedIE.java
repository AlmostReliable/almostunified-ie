package com.almostreliable.almostunified_ie;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.common.Mod;

@Mod(ModConstants.MOD_ID)
public final class AlmostUnifiedIE {

    public static ResourceLocation getRL(String key) {
        return ResourceLocation.fromNamespaceAndPath(ModConstants.MOD_ID, key);
    }
}
