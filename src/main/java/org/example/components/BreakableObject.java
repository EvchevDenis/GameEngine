package org.example.components;

public class BreakableObject extends Block {
    @Override
    void playerHit(PlayerController playerController) {
        if (!playerController.isRegular()) {
            //AssetPool.getSound("assets/sounds/break_block.ogg").play();
            gameObject.destroy();
        }
    }
}
