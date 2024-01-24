package org.example.components;

import org.example.utils.AssetPool;

public class BreakableObject extends Block {
    @Override
    void playerHit(PlayerController playerController) {
        if (!playerController.isRegular()) {
            //AssetPool.getSound("assets/sounds/break_block.ogg").play();
            gameObject.destroy();
        }
    }
}
