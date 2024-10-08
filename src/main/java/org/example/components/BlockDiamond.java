package org.example.components;

import org.example.utils.AssetPool;
import org.joml.Vector2f;

import java.util.Objects;

public class BlockDiamond extends Component {
    private Vector2f topY;
    private final float coinSpeed = 1.4f;

    @Override
    public void start() {
        topY = new Vector2f(this.gameObject.transform.position.y).add(0, 0.5f);
        Objects.requireNonNull(AssetPool.getSound("assets/sounds/coin.ogg")).play();
    }

    @Override
    public void update(float dt) {
        if (this.gameObject.transform.position.y < topY.y) {
            this.gameObject.transform.position.y += dt * coinSpeed;
            this.gameObject.transform.scale.x -= (0.5f * dt) % -1.0f;
        } else {
            gameObject.destroy();
        }
    }
}
