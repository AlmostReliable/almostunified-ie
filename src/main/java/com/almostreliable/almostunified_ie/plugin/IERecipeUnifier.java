package com.almostreliable.almostunified_ie.plugin;

import com.almostreliable.unified.api.constant.RecipeConstants;
import com.almostreliable.unified.api.unification.bundled.GenericRecipeUnifier;
import com.almostreliable.unified.api.unification.recipe.RecipeJson;
import com.almostreliable.unified.api.unification.recipe.RecipeUnifier;
import com.almostreliable.unified.api.unification.recipe.UnificationHelper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.List;

public class IERecipeUnifier implements RecipeUnifier {

    // inputs
    private static final String INPUT_0 = "input0";
    private static final String INPUT_1 = "input1";
    private static final String ADDITIVES = "additives";
    private static final String SOIL = "soil";

    // outputs
    private static final String SLAG = "slag";
    private static final String SECONDARIES = "secondaries";
    private static final String SECONDARY_OUTPUTS = "secondaryOutputs";
    private static final String STRIPPING_SECONDARIES = "strippingSecondaries";
    private static final String STRIPPED = "stripped";

    // both
    private static final String BASE_PREDICATE = "basePredicate";

    // catalysts
    private static final String MOLD = "mold";
    private static final String CATALYST = "catalyst";

    @Override
    public void unify(UnificationHelper helper, RecipeJson recipe) {
        GenericRecipeUnifier.INSTANCE.unify(helper, recipe);

        List.of(
            // alloy
            INPUT_0, INPUT_1,
            // arc_furnace, blast_furnace_fuel, blast_furnace, bottling_machine, cloche, coke_oven, crusher, fermenter,
            // fertilizer, metal_press, sawmill, squeezer
            RecipeConstants.INPUT,
            // blueprint, mixer
            RecipeConstants.INPUTS,
            // arc_furnace
            ADDITIVES,
            // cloche
            SOIL,
            // refinery
            CATALYST
        ).forEach(key -> unifyInputs(helper, recipe, key));

        List.of(
            // alloy, blast_furnace, coke_oven, crusher, fermenter, metal_press, mixer, sawmill, squeezer
            RecipeConstants.RESULT,
            // arc_furnace, bottling_machine, cloche
            RecipeConstants.RESULTS,
            // arc_furnace, blast_furnace
            SLAG,
            // sawmill
            SECONDARY_OUTPUTS, STRIPPING_SECONDARIES, STRIPPED
        ).forEach(key -> helper.unifyOutputs(recipe, key, true, BASE_PREDICATE, RecipeConstants.OUTPUT));

        // arc_furnace, crusher
        unifySecondaries(helper, recipe);
        // metal_press
        unifyMold(helper, recipe);
    }

    private void unifyInputs(UnificationHelper helper, RecipeJson recipe, String key) {
        if (recipe.getProperty(key) instanceof JsonArray json) {
            for (JsonElement element : json) {
                if (!(element instanceof JsonObject jsonObject)) {
                    continue;
                }
                unifyBasePredicate(helper, jsonObject);
            }
        }

        if (recipe.getProperty(key) instanceof JsonObject jsonObject) {
            unifyBasePredicate(helper, jsonObject);
        }

        helper.unifyInputs(recipe, key);
    }

    private void unifyBasePredicate(UnificationHelper helper, JsonObject json) {
        if (json.has(BASE_PREDICATE)) {
            helper.unifyInputElement(json.get(BASE_PREDICATE));
        }
    }

    private void unifySecondaries(UnificationHelper helper, RecipeJson recipe) {
        JsonElement secondaries = recipe.getProperty(SECONDARIES);
        if (secondaries == null) {
            return;
        }

        helper.unifyOutputArray(secondaries.getAsJsonArray(), true, RecipeConstants.OUTPUT);
    }

    private void unifyMold(UnificationHelper helper, RecipeJson recipe) {
        JsonElement mold = recipe.getProperty(MOLD);
        if (!(mold instanceof JsonPrimitive moldJson)) {
            return;
        }

        JsonPrimitive replacement = helper.handleOutputItemReplacement(moldJson);
        if (replacement != null) {
            recipe.setProperty(MOLD, replacement);
        }
    }
}
