package org.example.components;

import org.example.jade.Camera;
import org.example.jade.GameObject;
import org.example.jade.KeyListener;
import org.example.jade.Window;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class GameCamera extends Component {
    private transient GameObject player;
    private transient Camera gameCamera;
    private transient float lerpSpeed = 0.2f;

    private transient Vector4f skyColor = new Vector4f(92.0f / 255.0f, 148.0f / 255.0f, 252.0f / 255.0f, 1.0f);

    public GameCamera(Camera gameCamera) {
        this.gameCamera = gameCamera;
    }

    @Override
    public void start() {
        this.player = Window.getScene().getGameObjectWith(PlayerController.class);
        this.gameCamera.clearColor.set(skyColor);
    }

    @Override
    public void update(float dt) {
        if (player != null && !player.getComponent(PlayerController.class).hasWon()) {
            float targetX = player.transform.position.x - 3.0f;
            float targetY = player.transform.position.y - 1.5f;

            boolean isMovingLeft = KeyListener.isKeyPressed(GLFW_KEY_A);
            boolean isMovingRight = KeyListener.isKeyPressed(GLFW_KEY_D);
            boolean isJumping = KeyListener.isKeyPressed(GLFW_KEY_SPACE);

            if (isMovingLeft || isMovingRight) {
                gameCamera.position.x = quad(gameCamera.position.x, targetX, lerpSpeed);
            }

            if (isJumping) {
                gameCamera.position.y = quad(gameCamera.position.y, targetY, lerpSpeed);
            }

            if (!isMovingLeft && !isMovingRight && !isJumping) {
                gameCamera.position.x = quad(gameCamera.position.x, targetX, lerpSpeed);
                gameCamera.position.y = quad(gameCamera.position.y, targetY, lerpSpeed);
            }
        }
    }

    private float lerp(float start, float end, float t) {
        return start + (end - start) * t;
    }

    private float quad(float start, float end, float t) {
        return start + (end - start) * t * t;
    }

    private float cubic(float start, float end, float t) {
        return start + (end - start) * t * t * (3 - 2 * t);
    }

    private float quadBezier(float start, float control, float end, float t) {
        float oneMinusT = 1 - t;
        return start * oneMinusT * oneMinusT + control * 2 * oneMinusT * t + end * t * t;
    }
}
