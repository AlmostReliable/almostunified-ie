package com.almostreliable.almostunified_ie.plugin;

import com.almostreliable.almostunified_ie.AlmostUnifiedIE;
import com.almostreliable.unified.api.plugin.AlmostUnifiedNeoPlugin;
import com.almostreliable.unified.api.plugin.AlmostUnifiedPlugin;
import com.almostreliable.unified.api.unification.recipe.RecipeUnifierRegistry;

import net.minecraft.resources.ResourceLocation;

import blusunrize.immersiveengineering.ImmersiveEngineering;

@AlmostUnifiedNeoPlugin
public class AUPlugin implements AlmostUnifiedPlugin {

    @Override
    public ResourceLocation getPluginId() {
        return AlmostUnifiedIE.getRL(ImmersiveEngineering.MODID);
    }

    @Override
    public void registerRecipeUnifiers(RecipeUnifierRegistry registry) {
        registry.registerForModId(ImmersiveEngineering.MODID, new IERecipeUnifier());
    }
}
