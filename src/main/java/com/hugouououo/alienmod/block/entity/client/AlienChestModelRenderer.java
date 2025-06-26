package com.hugouououo.alienmod.block.entity.client;

import net.minecraft.client.render.block.entity.model.ChestBlockModel;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.client.render.item.model.special.ChestModelRenderer;

/**
 * Renderer do Alien Chest baseado no ChestModelRenderer padrão.
 */
public class AlienChestModelRenderer extends ChestModelRenderer {

    public static final Identifier ALIEN_CHEST_TEXTURE = Identifier.of("alienmod", "entity/alien_chest/alien_chest");

    public AlienChestModelRenderer(ChestBlockModel model, SpriteIdentifier textureId, float openness) {
        super(model, textureId, openness);
    }

    // Você pode adicionar overrides se quiser alterar comportamento específico
}
