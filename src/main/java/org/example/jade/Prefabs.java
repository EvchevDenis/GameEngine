package org.example.jade;

import org.example.components.*;
import org.example.physics2d.components.*;
import org.example.physics2d.enums.BodyType;
import org.example.utils.AssetPool;
import org.joml.Vector2f;

public class Prefabs {

    public static GameObject generateSpriteObject(Sprite sprite, float sizeX, float sizeY) {
        GameObject block = Window.getScene().createGameObject("Sprite_Object_Gen");
        block.transform.scale.x = sizeX;
        block.transform.scale.y = sizeY;
        SpriteRenderer renderer = new SpriteRenderer();
        renderer.setSprite(sprite);
        block.addComponent(renderer);

        return block;
    }

    public static GameObject generateMainCharacter() {
        Spritesheet playerHoodie = AssetPool.getSpritesheet("assets/images/Hoodie.png");
        Spritesheet playerHoodieShadow = AssetPool.getSpritesheet("assets/images/HoodieShadow.png");
        Spritesheet playerHoodieRed = AssetPool.getSpritesheet("assets/images/HoodieRed.png");

        GameObject mainCharacter = generateSpriteObject(playerHoodie.getSprite(0), 0.25f, 0.25f);

        // ------------------------------------------------------- //
        AnimationState run = new AnimationState();
        run.title = "Run";
        float defaultFrameTime = 0.1f;
        run.addFrame(playerHoodie.getSprite(24), defaultFrameTime);
        run.addFrame(playerHoodie.getSprite(25), defaultFrameTime);
        run.addFrame(playerHoodie.getSprite(26), defaultFrameTime);
        run.addFrame(playerHoodie.getSprite(27), defaultFrameTime);
        run.addFrame(playerHoodie.getSprite(28), defaultFrameTime);
        run.addFrame(playerHoodie.getSprite(29), defaultFrameTime);
        run.addFrame(playerHoodie.getSprite(30), defaultFrameTime);
        run.addFrame(playerHoodie.getSprite(31), defaultFrameTime);
        run.setLoop(true);

        AnimationState switchDirection = new AnimationState();
        switchDirection.title = "Switch Direction";
        switchDirection.addFrame(playerHoodie.getSprite(0), defaultFrameTime);
        switchDirection.setLoop(false);

        AnimationState idle = new AnimationState();
        idle.title = "Idle";
        idle.addFrame(playerHoodie.getSprite(0), 0.4f);
        idle.addFrame(playerHoodie.getSprite(1), 0.4f);
        idle.setLoop(true);

        AnimationState jump = new AnimationState();
        jump.title = "Jump";
        jump.addFrame(playerHoodie.getSprite(40), defaultFrameTime);
        jump.addFrame(playerHoodie.getSprite(41), defaultFrameTime);
        jump.addFrame(playerHoodie.getSprite(42), defaultFrameTime);
        jump.addFrame(playerHoodie.getSprite(43), defaultFrameTime);
        jump.addFrame(playerHoodie.getSprite(44), defaultFrameTime);
        jump.addFrame(playerHoodie.getSprite(45), defaultFrameTime);
        jump.addFrame(playerHoodie.getSprite(46), defaultFrameTime);
        jump.addFrame(playerHoodie.getSprite(47), defaultFrameTime);
        jump.setLoop(true);

        AnimationState beInvisible = new AnimationState();
        beInvisible.title = "Be Invisible";
        beInvisible.addFrame(playerHoodie.getSprite(47), 0.2f);
        beInvisible.addFrame(playerHoodie.getSprite(48), 0.2f);
        beInvisible.addFrame(playerHoodie.getSprite(49), 0.2f);
        beInvisible.setLoop(false);

        AnimationState backToRegular = new AnimationState();
        backToRegular.title = "Back To Regular";
        backToRegular.addFrame(playerHoodie.getSprite(49), 0.2f);
        backToRegular.addFrame(playerHoodie.getSprite(48), 0.2f);
        backToRegular.addFrame(playerHoodie.getSprite(47), 0.2f);
        backToRegular.setLoop(false);
        // ------------------------------------------------------- //

        // ------------------------------------------------------- //
        AnimationState shadowRun = new AnimationState();
        shadowRun.title = "ShadowRun";
        shadowRun.addFrame(playerHoodieShadow.getSprite(24), defaultFrameTime);
        shadowRun.addFrame(playerHoodieShadow.getSprite(25), defaultFrameTime);
        shadowRun.addFrame(playerHoodieShadow.getSprite(26), defaultFrameTime);
        shadowRun.addFrame(playerHoodieShadow.getSprite(27), defaultFrameTime);
        shadowRun.addFrame(playerHoodieShadow.getSprite(28), defaultFrameTime);
        shadowRun.addFrame(playerHoodieShadow.getSprite(29), defaultFrameTime);
        shadowRun.addFrame(playerHoodieShadow.getSprite(30), defaultFrameTime);
        shadowRun.addFrame(playerHoodieShadow.getSprite(31), defaultFrameTime);
        shadowRun.setLoop(true);

        AnimationState switchShadowDirection = new AnimationState();
        switchShadowDirection.title = "Switch Shadow Direction";
        switchShadowDirection.addFrame(playerHoodieShadow.getSprite(0), defaultFrameTime);
        switchShadowDirection.setLoop(false);

        AnimationState idleShadow = new AnimationState();
        idleShadow.title = "IdleShadow";
        idleShadow.addFrame(playerHoodieShadow.getSprite(0), 0.4f);
        idleShadow.addFrame(playerHoodieShadow.getSprite(1), 0.4f);
        idleShadow.setLoop(true);

        AnimationState jumpShadow = new AnimationState();
        jumpShadow.title = "JumpShadow";
        jumpShadow.addFrame(playerHoodieShadow.getSprite(40), defaultFrameTime);
        jumpShadow.addFrame(playerHoodieShadow.getSprite(41), defaultFrameTime);
        jumpShadow.addFrame(playerHoodieShadow.getSprite(42), defaultFrameTime);
        jumpShadow.addFrame(playerHoodieShadow.getSprite(43), defaultFrameTime);
        jumpShadow.addFrame(playerHoodieShadow.getSprite(44), defaultFrameTime);
        jumpShadow.addFrame(playerHoodieShadow.getSprite(45), defaultFrameTime);
        jumpShadow.addFrame(playerHoodieShadow.getSprite(46), defaultFrameTime);
        jumpShadow.addFrame(playerHoodieShadow.getSprite(47), defaultFrameTime);
        jumpShadow.setLoop(true);
        // ------------------------------------------------------- //

        // ------------------------------------------------------- //
        AnimationState redRun = new AnimationState();
        redRun.title = "RedRun";
        redRun.addFrame(playerHoodieRed.getSprite(24), defaultFrameTime);
        redRun.addFrame(playerHoodieRed.getSprite(25), defaultFrameTime);
        redRun.addFrame(playerHoodieRed.getSprite(26), defaultFrameTime);
        redRun.addFrame(playerHoodieRed.getSprite(27), defaultFrameTime);
        redRun.addFrame(playerHoodieRed.getSprite(28), defaultFrameTime);
        redRun.addFrame(playerHoodieRed.getSprite(29), defaultFrameTime);
        redRun.addFrame(playerHoodieRed.getSprite(30), defaultFrameTime);
        redRun.addFrame(playerHoodieRed.getSprite(31), defaultFrameTime);
        redRun.setLoop(true);

        AnimationState switchRedDirection = new AnimationState();
        switchRedDirection.title = "Switch Red Direction";
        switchRedDirection.addFrame(playerHoodieRed.getSprite(0), defaultFrameTime);
        switchRedDirection.setLoop(false);

        AnimationState idleRed = new AnimationState();
        idleRed.title = "IdleRed";
        idleRed.addFrame(playerHoodieRed.getSprite(0), 0.4f);
        idleRed.addFrame(playerHoodieRed.getSprite(1), 0.4f);
        idleRed.setLoop(true);

        AnimationState jumpRed = new AnimationState();
        jumpRed.title = "JumpRed";
        jumpRed.addFrame(playerHoodieRed.getSprite(40), defaultFrameTime);
        jumpRed.addFrame(playerHoodieRed.getSprite(41), defaultFrameTime);
        jumpRed.addFrame(playerHoodieRed.getSprite(42), defaultFrameTime);
        jumpRed.addFrame(playerHoodieRed.getSprite(43), defaultFrameTime);
        jumpRed.addFrame(playerHoodieRed.getSprite(44), defaultFrameTime);
        jumpRed.addFrame(playerHoodieRed.getSprite(45), defaultFrameTime);
        jumpRed.addFrame(playerHoodieRed.getSprite(46), defaultFrameTime);
        jumpRed.addFrame(playerHoodieRed.getSprite(47), defaultFrameTime);
        jumpRed.setLoop(true);
        // ------------------------------------------------------- //

        AnimationState die = new AnimationState();
        die.title = "Die";
        die.addFrame(playerHoodie.getSprite(55), defaultFrameTime);
        die.addFrame(playerHoodie.getSprite(56), defaultFrameTime);
        die.addFrame(playerHoodie.getSprite(57), defaultFrameTime);
        die.addFrame(playerHoodie.getSprite(58), defaultFrameTime);
        die.addFrame(playerHoodie.getSprite(59), defaultFrameTime);
        die.addFrame(playerHoodie.getSprite(60), defaultFrameTime);
        die.addFrame(playerHoodie.getSprite(61), defaultFrameTime);
        die.addFrame(playerHoodie.getSprite(62), defaultFrameTime);
        die.setLoop(false);

        AnimationState attackRed = new AnimationState();
        attackRed.title = "Attack";
        attackRed.addFrame(playerHoodieRed.getSprite(63), 0.05f);
        attackRed.addFrame(playerHoodieRed.getSprite(64), 0.05f);
        attackRed.addFrame(playerHoodieRed.getSprite(65), 0.05f);
        attackRed.addFrame(playerHoodieRed.getSprite(66), 0.05f);
        attackRed.addFrame(playerHoodieRed.getSprite(67), 0.05f);
        attackRed.addFrame(playerHoodieRed.getSprite(68), 0.05f);
        attackRed.addFrame(playerHoodieRed.getSprite(69), 0.05f);
        attackRed.addFrame(playerHoodieRed.getSprite(70), 0.05f);
        attackRed.setLoop(false);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(run);
        stateMachine.addState(idle);
        stateMachine.addState(switchDirection);
        stateMachine.addState(jump);
        stateMachine.addState(die);
        stateMachine.addState(beInvisible);
        stateMachine.addState(backToRegular);

        stateMachine.addState(shadowRun);
        stateMachine.addState(switchShadowDirection);
        stateMachine.addState(idleShadow);
        stateMachine.addState(jumpShadow);

        stateMachine.addState(redRun);
        stateMachine.addState(idleRed);
        stateMachine.addState(switchRedDirection);
        stateMachine.addState(jumpRed);
        stateMachine.addState(attackRed);

        // Default state of main character
        stateMachine.setDefaultState(idle.title);
        stateMachine.addState(run.title, switchDirection.title, "switchDirection");
        stateMachine.addState(run.title, idle.title, "stopRunning");
        stateMachine.addState(run.title, jump.title, "jump");
        stateMachine.addState(switchDirection.title, idle.title, "stopRunning");
        stateMachine.addState(switchDirection.title, run.title, "startRunning");
        stateMachine.addState(switchDirection.title, jump.title, "jump");
        stateMachine.addState(idle.title, run.title, "startRunning");
        stateMachine.addState(idle.title, jump.title, "jump");
        stateMachine.addState(jump.title, idle.title, "stopJumping");

        // Invisibility for default state
        stateMachine.addState(run.title, beInvisible.title, "invisible");
        stateMachine.addState(switchDirection.title, beInvisible.title, "invisible");
        stateMachine.addState(idle.title, beInvisible.title, "invisible");
        stateMachine.addState(jump.title, beInvisible.title, "invisible");
        stateMachine.addState(beInvisible.title, backToRegular.title, "notInvisible");
        stateMachine.addState(backToRegular.title, run.title, "startRunning");
        stateMachine.addState(backToRegular.title, switchDirection.title, "switchDirection");
        stateMachine.addState(backToRegular.title, idle.title, "stopRunning");
        stateMachine.addState(backToRegular.title, jump.title, "jump");

        // Shadow state
        stateMachine.addState(shadowRun.title, switchShadowDirection.title, "switchDirection");
        stateMachine.addState(shadowRun.title, idleShadow.title, "stopRunning");
        stateMachine.addState(shadowRun.title, jumpShadow.title, "jump");
        stateMachine.addState(switchShadowDirection.title, idleShadow.title, "stopRunning");
        stateMachine.addState(switchShadowDirection.title, shadowRun.title, "startRunning");
        stateMachine.addState(switchShadowDirection.title, jumpShadow.title, "jump");
        stateMachine.addState(idleShadow.title, shadowRun.title, "startRunning");
        stateMachine.addState(idleShadow.title, jumpShadow.title, "jump");
        stateMachine.addState(jumpShadow.title, idleShadow.title, "stopJumping");

        // Invisibility for shadow state
        stateMachine.addState(shadowRun.title, beInvisible.title, "invisible");
        stateMachine.addState(switchShadowDirection.title, beInvisible.title, "invisible");
        stateMachine.addState(idleShadow.title, beInvisible.title, "invisible");
        stateMachine.addState(jumpShadow.title, beInvisible.title, "invisible");
        stateMachine.addState(beInvisible.title, backToRegular.title, "notInvisible");
        stateMachine.addState(backToRegular.title, run.title, "startRunning");
        stateMachine.addState(backToRegular.title, switchDirection.title, "switchDirection");
        stateMachine.addState(backToRegular.title, idle.title, "stopRunning");
        stateMachine.addState(backToRegular.title, jump.title, "jump");

        // Red state
        stateMachine.addState(redRun.title, switchRedDirection.title, "switchDirection");
        stateMachine.addState(redRun.title, idleRed.title, "stopRunning");
        stateMachine.addState(redRun.title, jumpRed.title, "jump");
        stateMachine.addState(switchRedDirection.title, idleRed.title, "stopRunning");
        stateMachine.addState(switchRedDirection.title, redRun.title, "startRunning");
        stateMachine.addState(switchRedDirection.title, jumpRed.title, "jump");
        stateMachine.addState(idleRed.title, redRun.title, "startRunning");
        stateMachine.addState(idleRed.title, jumpRed.title, "jump");
        stateMachine.addState(jumpRed.title, idleRed.title, "stopJumping");

        // Invisibility for shadow state
        stateMachine.addState(redRun.title, beInvisible.title, "invisible");
        stateMachine.addState(switchRedDirection.title, beInvisible.title, "invisible");
        stateMachine.addState(idleRed.title, beInvisible.title, "invisible");
        stateMachine.addState(jumpRed.title, beInvisible.title, "invisible");
        stateMachine.addState(beInvisible.title, backToRegular.title, "notInvisible");
        stateMachine.addState(backToRegular.title, run.title, "startRunning");
        stateMachine.addState(backToRegular.title, switchDirection.title, "switchDirection");
        stateMachine.addState(backToRegular.title, idle.title, "stopRunning");
        stateMachine.addState(backToRegular.title, jump.title, "jump");

        // Changing states
        stateMachine.addState(run.title, shadowRun.title, "powerup");
        stateMachine.addState(idle.title, idleShadow.title, "powerup");
        stateMachine.addState(switchDirection.title, switchShadowDirection.title, "powerup");
        stateMachine.addState(jump.title, jumpShadow.title, "powerup");
        stateMachine.addState(shadowRun.title, redRun.title, "powerup");
        stateMachine.addState(idleShadow.title, idleRed.title, "powerup");
        stateMachine.addState(switchShadowDirection.title, switchRedDirection.title, "powerup");
        stateMachine.addState(jumpShadow.title, jumpRed.title, "powerup");

        // Dead state
        stateMachine.addState(run.title, die.title, "die");
        stateMachine.addState(switchDirection.title, die.title, "die");
        stateMachine.addState(idle.title, die.title, "die");
        stateMachine.addState(jump.title, die.title, "die");
        stateMachine.addState(shadowRun.title, run.title, "die");
        stateMachine.addState(switchShadowDirection.title, switchDirection.title, "die");
        stateMachine.addState(idleShadow.title, idle.title, "die");
        stateMachine.addState(jumpShadow.title, jump.title, "die");
        stateMachine.addState(redRun.title, shadowRun.title, "die");
        stateMachine.addState(switchRedDirection.title, switchShadowDirection.title, "die");
        stateMachine.addState(idleRed.title, idleShadow.title, "die");
        stateMachine.addState(jumpRed.title, jumpShadow.title, "die");

        // Attacking states
        stateMachine.addState(redRun.title, attackRed.title, "attack");
        stateMachine.addState(idleRed.title, attackRed.title, "attack");
        stateMachine.addState(switchRedDirection.title, attackRed.title, "attack");
        stateMachine.addState(jumpRed.title, attackRed.title, "attack");
        stateMachine.addState(attackRed.title, attackRed.title, "attack");
        stateMachine.addState(attackRed.title, redRun.title, "stopAttack");
        stateMachine.addState(attackRed.title, idleRed.title, "stopAttack");
        stateMachine.addState(attackRed.title, switchRedDirection.title, "stopAttack");
        stateMachine.addState(attackRed.title, jumpRed.title, "stopAttack");
        mainCharacter.addComponent(stateMachine);

        PillboxCollider pb = new PillboxCollider();
        pb.width = 0.16f;
        pb.height = 0.21f;

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Dynamic);
        rb.setContinuousCollision(false);
        rb.setFixedRotation(true);
        rb.setMass(25.0f);

        mainCharacter.addComponent(rb);
        mainCharacter.addComponent(pb);
        mainCharacter.addComponent(new PlayerController());

        return mainCharacter;
    }

    public static GameObject generateQuestionBlock() {
        Spritesheet playerSprites = AssetPool.getSpritesheet("assets/images/items.png");
        GameObject questionBlock = generateSpriteObject(playerSprites.getSprite(0), 0.25f, 0.25f);

        AnimationState flicker = new AnimationState();
        flicker.title = "Question";
        float defaultFrameTime = 0.23f;
        flicker.addFrame(playerSprites.getSprite(0), 0.57f);
        flicker.addFrame(playerSprites.getSprite(1), defaultFrameTime);
        flicker.addFrame(playerSprites.getSprite(2), defaultFrameTime);
        flicker.addFrame(playerSprites.getSprite(2), defaultFrameTime);
        flicker.addFrame(playerSprites.getSprite(2), defaultFrameTime);
        flicker.addFrame(playerSprites.getSprite(2), defaultFrameTime);
        flicker.addFrame(playerSprites.getSprite(2), defaultFrameTime);
        flicker.setLoop(true);

        AnimationState inactive = new AnimationState();
        inactive.title = "Inactive";
        inactive.addFrame(playerSprites.getSprite(3), 0.1f);
        inactive.setLoop(false);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(flicker);
        stateMachine.addState(inactive);
        stateMachine.setDefaultState(flicker.title);
        stateMachine.addState(flicker.title, inactive.title, "setInactive");
        questionBlock.addComponent(stateMachine);
        questionBlock.addComponent(new QuestionBlock());

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Static);
        questionBlock.addComponent(rb);
        Box2DCollider b2d = new Box2DCollider();
        b2d.setHalfSize(new Vector2f(0.25f, 0.25f));
        questionBlock.addComponent(b2d);
        questionBlock.addComponent(new Ground());

        return questionBlock;
    }

    public static GameObject generateBlockDiamond() {
        Spritesheet items = AssetPool.getSpritesheet("assets/images/diamond.png");
        GameObject diamond = generateSpriteObject(items.getSprite(0), 0.15f, 0.15f);

        AnimationState diamondFlip = new AnimationState();
        diamondFlip.title = "DiamondFlip";
        float defaultFrameTime = 0.23f;
        diamondFlip.addFrame(items.getSprite(0), 0.57f);
        diamondFlip.addFrame(items.getSprite(1), defaultFrameTime);
        diamondFlip.addFrame(items.getSprite(2), defaultFrameTime);
        diamondFlip.addFrame(items.getSprite(3), defaultFrameTime);
        diamondFlip.addFrame(items.getSprite(4), defaultFrameTime);
        diamondFlip.addFrame(items.getSprite(5), defaultFrameTime);
        diamondFlip.addFrame(items.getSprite(6), defaultFrameTime);

        diamondFlip.setLoop(true);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(diamondFlip);
        stateMachine.setDefaultState(diamondFlip.title);
        diamond.addComponent(stateMachine);
        diamond.addComponent(new QuestionBlock());

        diamond.addComponent(new BlockDiamond());

        return diamond;
    }

    public static GameObject generateBottle() {
        Spritesheet items = AssetPool.getSpritesheet("assets/images/bottles.png");
        GameObject bottle = generateSpriteObject(items.getSprite(14), 0.15f, 0.15f);

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Dynamic);
        rb.setFixedRotation(true);
        rb.setContinuousCollision(false);
        bottle.addComponent(rb);

        CircleCollider circleCollider = new CircleCollider();
        circleCollider.setRadius(0.08f);
        bottle.addComponent(circleCollider);
        bottle.addComponent(new BottleAI());

        return bottle;
    }

    public static GameObject generateNecklace() {
        Spritesheet weapons = AssetPool.getSpritesheet("assets/images/weapons.png");
        GameObject necklace = generateSpriteObject(weapons.getSprite(55), 0.15f, 0.15f);

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Static);
        rb.setFixedRotation(true);
        rb.setContinuousCollision(false);
        necklace.addComponent(rb);

        CircleCollider circleCollider = new CircleCollider();
        circleCollider.setRadius(0.08f);
        necklace.addComponent(circleCollider);
        necklace.addComponent(new Necklace());

        return necklace;
    }

    public static GameObject generateRing() {
        Spritesheet weapons = AssetPool.getSpritesheet("assets/images/weapons.png");
        GameObject ring = generateSpriteObject(weapons.getSprite(29), 0.15f, 0.15f);

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Static);
        rb.setFixedRotation(true);
        rb.setContinuousCollision(false);
        ring.addComponent(rb);

        CircleCollider circleCollider = new CircleCollider();
        circleCollider.setRadius(0.08f);
        ring.addComponent(circleCollider);
        ring.addComponent(new Ring());

        return ring;
    }

    public static GameObject generateCrate() {
        Spritesheet items = AssetPool.getSpritesheet("assets/images/crate.png");
        GameObject crate = generateSpriteObject(items.getSprite(0), 0.25f, 0.25f);

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Dynamic);
        rb.setFixedRotation(false);
        rb.setContinuousCollision(false);
        crate.addComponent(rb);

        GlideBox2DCollider glideBox2DCollider = new GlideBox2DCollider();
        glideBox2DCollider.setCircleRadius(0.02f);
        glideBox2DCollider.setBoxSize(0.22f, 0.22f);
        crate.addComponent(glideBox2DCollider);
        crate.addComponent(new Ground());

        return crate;
    }

    public static GameObject generateBarrel() {
        Spritesheet items = AssetPool.getSpritesheet("assets/images/barrel.png");
        GameObject barrel = generateSpriteObject(items.getSprite(0), 0.25f, 0.25f);

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Dynamic);
        rb.setFixedRotation(false);
        rb.setContinuousCollision(false);
        barrel.addComponent(rb);

        CircleCollider circleCollider = new CircleCollider();
        circleCollider.setRadius(0.11f);
        barrel.addComponent(circleCollider);
        barrel.addComponent(new Ground());

        return barrel;
    }

    public static GameObject generateCuteEnemy(String spritesheetPath) {
        Spritesheet sprites = AssetPool.getSpritesheet(spritesheetPath);
        GameObject owletEnemy = generateSpriteObject(sprites.getSprite(0), 0.25f, 0.25f);

        AnimationState walk = new AnimationState();
        walk.title = "Walk";
        float defaultFrameTime = 0.1f;
        walk.addFrame(sprites.getSprite(15), defaultFrameTime);
        walk.addFrame(sprites.getSprite(16), defaultFrameTime);
        walk.addFrame(sprites.getSprite(17), defaultFrameTime);
        walk.addFrame(sprites.getSprite(18), defaultFrameTime);
        walk.addFrame(sprites.getSprite(19), defaultFrameTime);
        walk.addFrame(sprites.getSprite(20), defaultFrameTime);
        walk.setLoop(true);

        AnimationState switchDirection = new AnimationState();
        switchDirection.title = "Switch Direction";
        switchDirection.addFrame(sprites.getSprite(15), defaultFrameTime);
        switchDirection.setLoop(false);

        AnimationState dead = new AnimationState();
        dead.title = "Dead";
        dead.addFrame(sprites.getSprite(67), defaultFrameTime);
        dead.addFrame(sprites.getSprite(68), defaultFrameTime);
        dead.addFrame(sprites.getSprite(69), defaultFrameTime);
        dead.addFrame(sprites.getSprite(70), defaultFrameTime);
        dead.addFrame(sprites.getSprite(71), defaultFrameTime);
        dead.addFrame(sprites.getSprite(72), defaultFrameTime);
        dead.addFrame(sprites.getSprite(73), defaultFrameTime);
        dead.addFrame(sprites.getSprite(74), defaultFrameTime);
        dead.setLoop(false);

        AnimationState idle = new AnimationState();
        idle.title = "Idle";
        idle.addFrame(sprites.getSprite(75), 0.2f);
        idle.addFrame(sprites.getSprite(76), 0.2f);
        idle.addFrame(sprites.getSprite(77), 0.2f);
        idle.addFrame(sprites.getSprite(78), 0.2f);
        idle.setLoop(true);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(walk);
        stateMachine.addState(dead);
        stateMachine.addState(idle);
        stateMachine.addState(switchDirection);

        stateMachine.setDefaultState(idle.title);
        stateMachine.addState(walk.title, switchDirection.title, "switchDirection");
        stateMachine.addState(walk.title, idle.title, "stopWalking");
        stateMachine.addState(switchDirection.title, idle.title, "stopWalking");
        stateMachine.addState(switchDirection.title, walk.title, "startWalking");
        stateMachine.addState(idle.title, walk.title, "startWalking");

        stateMachine.addState(walk.title, dead.title, "dead");
        stateMachine.addState(switchDirection.title, dead.title, "dead");
        stateMachine.addState(idle.title, dead.title, "dead");

        owletEnemy.addComponent(stateMachine);

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Dynamic);
        rb.setMass(0.1f);
        rb.setContinuousCollision(false);
        rb.setFixedRotation(true);
        owletEnemy.addComponent(rb);

        PillboxCollider pillboxCollider = new PillboxCollider();
        pillboxCollider.width = 0.16f;
        pillboxCollider.height = 0.20f;
        owletEnemy.addComponent(pillboxCollider);

        owletEnemy.addComponent(new CuteEnemyAI());

        return owletEnemy;
    }

    public static GameObject generatePortal(Direction direction) {
        Spritesheet portalSprite = AssetPool.getSpritesheet("assets/images/purplePortal.png");
        int index = direction == Direction.Down ? 0 :
                direction == Direction.Up ? 1 :
                        direction == Direction.Right ? 2 :
                                direction == Direction.Left ? 3 : -1;
        assert index != -1 : "Invalid pipe direction.";
        GameObject portal = generateSpriteObject(portalSprite.getSprite(index), 0.5f, 0.75f);

        AnimationState portalAnimation = new AnimationState();
        portalAnimation.title = "Portal Animation";
        float defaultFrameTime = 0.1f;
        portalAnimation.addFrame(portalSprite.getSprite(0), defaultFrameTime);
        portalAnimation.addFrame(portalSprite.getSprite(1), defaultFrameTime);
        portalAnimation.addFrame(portalSprite.getSprite(2), defaultFrameTime);
        portalAnimation.addFrame(portalSprite.getSprite(3), defaultFrameTime);
        portalAnimation.addFrame(portalSprite.getSprite(4), defaultFrameTime);
        portalAnimation.addFrame(portalSprite.getSprite(5), defaultFrameTime);
        portalAnimation.addFrame(portalSprite.getSprite(6), defaultFrameTime);
        portalAnimation.addFrame(portalSprite.getSprite(7), defaultFrameTime);
        portalAnimation.setLoop(true);

        AnimationState portalEndAnimation = new AnimationState();
        portalEndAnimation.title = "Portal End Animation";
        portalEndAnimation.addFrame(portalSprite.getSprite(16), defaultFrameTime);
        portalEndAnimation.addFrame(portalSprite.getSprite(17), defaultFrameTime);
        portalEndAnimation.addFrame(portalSprite.getSprite(18), defaultFrameTime);
        portalEndAnimation.addFrame(portalSprite.getSprite(19), defaultFrameTime);
        portalEndAnimation.addFrame(portalSprite.getSprite(20), defaultFrameTime);
        portalEndAnimation.addFrame(portalSprite.getSprite(21), defaultFrameTime);
        portalEndAnimation.addFrame(portalSprite.getSprite(22), defaultFrameTime);
        portalEndAnimation.addFrame(portalSprite.getSprite(23), defaultFrameTime);
        portalEndAnimation.setLoop(false);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(portalAnimation);
        stateMachine.addState(portalEndAnimation);
        stateMachine.setDefaultState(portalAnimation.title);
        stateMachine.addState(portalAnimation.title, portalEndAnimation.title, "endPortal");
        portal.addComponent(stateMachine);

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Static);
        rb.setFixedRotation(true);
        rb.setContinuousCollision(true);
        portal.addComponent(rb);

        Box2DCollider b2d = new Box2DCollider();
        b2d.setHalfSize(new Vector2f(0.25f, 0.75f));
        portal.addComponent(b2d);
        portal.addComponent(new Portal(direction));
        portal.addComponent(new Ground());

        return portal;
    }

    public static GameObject generateBreaker() {
        Spritesheet sprites = AssetPool.getSpritesheet("assets/images/breaker.png");
        GameObject breakerEnemy = generateSpriteObject(sprites.getSprite(0), 0.50f, 0.50f);

        float defaultFrameTime = 0.1f;
        AnimationState fly = new AnimationState();
        fly.title = "Fly";
        fly.addFrame(sprites.getSprite(16), defaultFrameTime);
        fly.addFrame(sprites.getSprite(17), defaultFrameTime);
        fly.addFrame(sprites.getSprite(18), defaultFrameTime);
        fly.addFrame(sprites.getSprite(19), defaultFrameTime);
        fly.addFrame(sprites.getSprite(19), defaultFrameTime);
        fly.addFrame(sprites.getSprite(18), defaultFrameTime);
        fly.addFrame(sprites.getSprite(17), defaultFrameTime);
        fly.addFrame(sprites.getSprite(16), defaultFrameTime);
        fly.addFrame(sprites.getSprite(32), defaultFrameTime);
        fly.addFrame(sprites.getSprite(33), defaultFrameTime);
        fly.addFrame(sprites.getSprite(34), defaultFrameTime);
        fly.addFrame(sprites.getSprite(35), defaultFrameTime);
        fly.addFrame(sprites.getSprite(36), defaultFrameTime);
        fly.addFrame(sprites.getSprite(37), defaultFrameTime);
        fly.addFrame(sprites.getSprite(38), defaultFrameTime);
        fly.addFrame(sprites.getSprite(39), defaultFrameTime);
        fly.addFrame(sprites.getSprite(8), defaultFrameTime);
        fly.addFrame(sprites.getSprite(9), defaultFrameTime);
        fly.addFrame(sprites.getSprite(10), defaultFrameTime);
        fly.addFrame(sprites.getSprite(11), defaultFrameTime);
        fly.addFrame(sprites.getSprite(11), defaultFrameTime);
        fly.addFrame(sprites.getSprite(10), defaultFrameTime);
        fly.addFrame(sprites.getSprite(9), defaultFrameTime);
        fly.addFrame(sprites.getSprite(8), defaultFrameTime);

        fly.setLoop(true);

        AnimationState switchDirection = new AnimationState();
        switchDirection.title = "Switch Direction";
        switchDirection.addFrame(sprites.getSprite(0), defaultFrameTime);
        switchDirection.setLoop(false);

        AnimationState idleBreaker = new AnimationState();
        idleBreaker.title = "idle";
        idleBreaker.addFrame(sprites.getSprite(32), defaultFrameTime);
        idleBreaker.addFrame(sprites.getSprite(33), defaultFrameTime);
        idleBreaker.addFrame(sprites.getSprite(34), defaultFrameTime);
        idleBreaker.addFrame(sprites.getSprite(35), defaultFrameTime);
        idleBreaker.addFrame(sprites.getSprite(36), defaultFrameTime);
        idleBreaker.addFrame(sprites.getSprite(37), defaultFrameTime);
        idleBreaker.addFrame(sprites.getSprite(38), defaultFrameTime);
        idleBreaker.addFrame(sprites.getSprite(39), defaultFrameTime);
        idleBreaker.setLoop(true);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(idleBreaker);
        stateMachine.addState(fly);
        stateMachine.addState(switchDirection);

        stateMachine.setDefaultState(idleBreaker.title);
        stateMachine.addState(fly.title, switchDirection.title, "switchDirection");
        stateMachine.addState(fly.title, idleBreaker.title, "stopWalking");
        stateMachine.addState(switchDirection.title, idleBreaker.title, "stopWalking");
        stateMachine.addState(switchDirection.title, fly.title, "startWalking");
        stateMachine.addState(idleBreaker.title, fly.title, "startWalking");
        breakerEnemy.addComponent(stateMachine);

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Kinematic);
        rb.setMass(0.0f);
        rb.setContinuousCollision(false);
        rb.setFixedRotation(true);
        breakerEnemy.addComponent(rb);

        Box2DCollider boxCollider = new Box2DCollider();
        boxCollider.setHalfSize(new Vector2f(0.50f, 0.50f));
        breakerEnemy.addComponent(boxCollider);

        breakerEnemy.addComponent(new BreakerAI());

        return breakerEnemy;
    }

    public static GameObject generateSlime() {
        Spritesheet slimeSprites = AssetPool.getSpritesheet("assets/images/slimeSheet.png");
        GameObject slimeEnemy = generateSpriteObject(slimeSprites.getSprite(0), 0.25f, 0.25f);

        AnimationState walk = new AnimationState();
        walk.title = "Walk";
        float defaultFrameTime = 0.1f;
        walk.addFrame(slimeSprites.getSprite(4), defaultFrameTime);
        walk.addFrame(slimeSprites.getSprite(5), defaultFrameTime);
        walk.addFrame(slimeSprites.getSprite(6), defaultFrameTime);
        walk.addFrame(slimeSprites.getSprite(7), defaultFrameTime);
        walk.setLoop(true);

        AnimationState switchDirection = new AnimationState();
        switchDirection.title = "Switch Direction";
        switchDirection.addFrame(slimeSprites.getSprite(0), defaultFrameTime);
        switchDirection.setLoop(false);

        AnimationState dead = new AnimationState();
        dead.title = "Dead";
        dead.addFrame(slimeSprites.getSprite(16), defaultFrameTime);
        dead.addFrame(slimeSprites.getSprite(17), defaultFrameTime);
        dead.addFrame(slimeSprites.getSprite(18), defaultFrameTime);
        dead.addFrame(slimeSprites.getSprite(19), defaultFrameTime);
        dead.addFrame(slimeSprites.getSprite(20), defaultFrameTime);
        dead.setLoop(false);

        AnimationState idle = new AnimationState();
        idle.title = "Idle";
        idle.addFrame(slimeSprites.getSprite(0), defaultFrameTime);
        idle.addFrame(slimeSprites.getSprite(1), defaultFrameTime);
        idle.addFrame(slimeSprites.getSprite(2), defaultFrameTime);
        idle.addFrame(slimeSprites.getSprite(3), defaultFrameTime);
        idle.setLoop(true);

        AnimationState attack = new AnimationState();
        attack.title = "Attack";
        attack.addFrame(slimeSprites.getSprite(8), defaultFrameTime);
        attack.addFrame(slimeSprites.getSprite(9), defaultFrameTime);
        attack.addFrame(slimeSprites.getSprite(10), defaultFrameTime);
        attack.addFrame(slimeSprites.getSprite(11), defaultFrameTime);
        attack.addFrame(slimeSprites.getSprite(12), defaultFrameTime);
        attack.addFrame(slimeSprites.getSprite(13), defaultFrameTime);
        attack.addFrame(slimeSprites.getSprite(14), defaultFrameTime);
        attack.addFrame(slimeSprites.getSprite(15), defaultFrameTime);
        attack.setLoop(true);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(walk);
        stateMachine.addState(dead);
        stateMachine.addState(idle);
        stateMachine.addState(attack);
        stateMachine.addState(switchDirection);

        stateMachine.setDefaultState(idle.title);
        stateMachine.addState(walk.title, switchDirection.title, "switchDirection");
        stateMachine.addState(walk.title, idle.title, "stopWalking");
        stateMachine.addState(walk.title, attack.title, "startAttack");
        stateMachine.addState(switchDirection.title, idle.title, "stopWalking");
        stateMachine.addState(switchDirection.title, walk.title, "startWalking");
        stateMachine.addState(switchDirection.title, attack.title, "startAttack");
        stateMachine.addState(idle.title, walk.title, "startWalking");
        stateMachine.addState(idle.title, attack.title, "startWalking");
        stateMachine.addState(attack.title, walk.title, "startWalking");
        stateMachine.addState(attack.title, switchDirection.title, "switchDirection");
        stateMachine.addState(attack.title, idle.title, "stopAttack");

        stateMachine.addState(walk.title, dead.title, "dead");
        stateMachine.addState(switchDirection.title, dead.title, "dead");
        stateMachine.addState(idle.title, dead.title, "dead");
        stateMachine.addState(attack.title, dead.title, "dead");
        slimeEnemy.addComponent(stateMachine);

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Dynamic);
        rb.setMass(0.1f);
        rb.setContinuousCollision(false);
        rb.setFixedRotation(true);
        slimeEnemy.addComponent(rb);

        BarrelBoatCollider barrelBoatCollider = new BarrelBoatCollider();
        barrelBoatCollider.setBoxSize(new Vector2f(0.2f, 0.05f));
        barrelBoatCollider.setCircleRadius(0.05f);
        slimeEnemy.addComponent(barrelBoatCollider);

        slimeEnemy.addComponent(new SlimeAI());

        return slimeEnemy;
    }

    public static GameObject generateLava() {
        Spritesheet lavaSprites = AssetPool.getSpritesheet("assets/images/lava.png");
        GameObject lava = generateSpriteObject(lavaSprites.getSprite(0), 0.25f, 0.25f);

        AnimationState bubbling = new AnimationState();
        bubbling.title = "Bubbling";
        float defaultFrameTime = 0.2f;
        bubbling.addFrame(lavaSprites.getSprite(0), defaultFrameTime);
        bubbling.addFrame(lavaSprites.getSprite(1), defaultFrameTime);
        bubbling.addFrame(lavaSprites.getSprite(2), defaultFrameTime);
        bubbling.addFrame(lavaSprites.getSprite(3), defaultFrameTime);
        bubbling.setLoop(true);


        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(bubbling);
        stateMachine.setDefaultState(bubbling.title);
        lava.addComponent(stateMachine);
        lava.addComponent(new Lava());

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Static);
        lava.addComponent(rb);
        Box2DCollider b2d = new Box2DCollider();
        b2d.setHalfSize(new Vector2f(0.25f, 0.05f));
        lava.addComponent(b2d);

        return lava;
    }

    public static GameObject generateWater() {
        Spritesheet waterSprites = AssetPool.getSpritesheet("assets/images/water.png");
        GameObject water = generateSpriteObject(waterSprites.getSprite(0), 0.25f, 0.25f);

        AnimationState waving = new AnimationState();
        waving.title = "Waving";
        float defaultFrameTime = 0.1f;
        waving.addFrame(waterSprites.getSprite(0), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(1), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(2), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(3), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(4), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(5), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(6), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(7), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(8), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(9), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(10), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(11), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(12), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(13), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(14), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(15), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(16), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(17), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(18), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(19), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(20), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(21), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(22), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(23), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(24), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(25), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(26), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(27), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(28), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(29), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(30), defaultFrameTime);
        waving.addFrame(waterSprites.getSprite(31), defaultFrameTime);
        waving.setLoop(true);


        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(waving);
        stateMachine.setDefaultState(waving.title);
        water.addComponent(stateMachine);
        water.addComponent(new Water());

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Static);
        water.addComponent(rb);
        Box2DCollider b2d = new Box2DCollider();
        b2d.setHalfSize(new Vector2f(0.25f, 0.05f));
        water.addComponent(b2d);

        return water;
    }

    public static GameObject generateFlag() {
        Spritesheet flagSprites = AssetPool.getSpritesheet("assets/images/flag.png");
        GameObject flag = generateSpriteObject(flagSprites.getSprite(0), 0.75f, 0.9f);

        AnimationState windy = new AnimationState();
        windy.title = "Bubbling";
        float defaultFrameTime = 0.2f;
        windy.addFrame(flagSprites.getSprite(0), defaultFrameTime);
        windy.addFrame(flagSprites.getSprite(1), defaultFrameTime);
        windy.addFrame(flagSprites.getSprite(2), defaultFrameTime);
        windy.addFrame(flagSprites.getSprite(3), defaultFrameTime);
        windy.setLoop(true);


        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(windy);
        stateMachine.setDefaultState(windy.title);
        flag.addComponent(stateMachine);
        flag.addComponent(new Flag());

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Static);
        flag.addComponent(rb);
        Box2DCollider b2d = new Box2DCollider();
        b2d.setHalfSize(new Vector2f(0.1f, 0.7f));
        b2d.setOffset(new Vector2f(-0.13f, -0.1f));
        flag.addComponent(b2d);

        return flag;
    }

    public static GameObject generateSpikes() {
        Spritesheet spikesSprites = AssetPool.getSpritesheet("assets/images/spikes.png");
        GameObject spikes = generateSpriteObject(spikesSprites.getSprite(0), 0.50f, 0.25f);

        AnimationState piercing = new AnimationState();
        piercing.title = "Piercing";
        float defaultFrameTime = 0.05f;
        piercing.addFrame(spikesSprites.getSprite(0), defaultFrameTime);
        piercing.addFrame(spikesSprites.getSprite(1), defaultFrameTime);
        piercing.addFrame(spikesSprites.getSprite(2), defaultFrameTime);
        piercing.addFrame(spikesSprites.getSprite(3), defaultFrameTime);
        piercing.addFrame(spikesSprites.getSprite(4), defaultFrameTime);
        piercing.addFrame(spikesSprites.getSprite(5), defaultFrameTime);
        piercing.addFrame(spikesSprites.getSprite(6), defaultFrameTime);
        piercing.addFrame(spikesSprites.getSprite(7), defaultFrameTime);
        piercing.addFrame(spikesSprites.getSprite(8), defaultFrameTime);
        piercing.addFrame(spikesSprites.getSprite(9), defaultFrameTime);
        piercing.setLoop(true);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(piercing);
        stateMachine.setDefaultState(piercing.title);
        spikes.addComponent(stateMachine);

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Static);
        rb.setFixedRotation(true);
        rb.setContinuousCollision(false);
        spikes.addComponent(rb);

        Box2DCollider b2d = new Box2DCollider();
        b2d.setHalfSize(new Vector2f(0.50f, 0.1f));
        b2d.setOffset(new Vector2f(0, 0.03f));
        spikes.addComponent(b2d);
        spikes.addComponent(new Spikes());

        return spikes;
    }

    public static GameObject generateFireball(Vector2f position) {
        Spritesheet fireballSprites = AssetPool.getSpritesheet("assets/images/fireball.png");
        GameObject fireball = generateSpriteObject(fireballSprites.getSprite(0), 0.1f, 0.1f);
        fireball.transform.position = position;

        AnimationState flying = new AnimationState();
        flying.title = "Flying";
        float defaultFrameTime = 0.05f;
        flying.addFrame(fireballSprites.getSprite(0), defaultFrameTime);
        flying.addFrame(fireballSprites.getSprite(1), defaultFrameTime);
        flying.addFrame(fireballSprites.getSprite(2), defaultFrameTime);
        flying.addFrame(fireballSprites.getSprite(3), defaultFrameTime);
        flying.addFrame(fireballSprites.getSprite(4), defaultFrameTime);
        flying.setLoop(true);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(flying);
        stateMachine.setDefaultState(flying.title);
        fireball.addComponent(stateMachine);

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Dynamic);
        rb.setFixedRotation(true);
        rb.setContinuousCollision(false);
        fireball.addComponent(rb);

        CircleCollider circleCollider = new CircleCollider();
        circleCollider.setRadius(0.02f);
        fireball.addComponent(circleCollider);
        fireball.addComponent(new Projectile());

        return fireball;
    }

    public static GameObject generateSaw() {
        Spritesheet sawSprites = AssetPool.getSpritesheet("assets/images/saw.png");
        GameObject saw = generateSpriteObject(sawSprites.getSprite(0), 0.25f, 0.25f);

        AnimationState rolling = new AnimationState();
        rolling.title = "Rolling";
        float defaultFrameTime = 0.03f;
        rolling.addFrame(sawSprites.getSprite(0), defaultFrameTime);
        rolling.addFrame(sawSprites.getSprite(1), defaultFrameTime);
        rolling.addFrame(sawSprites.getSprite(2), defaultFrameTime);
        rolling.addFrame(sawSprites.getSprite(3), defaultFrameTime);
        rolling.addFrame(sawSprites.getSprite(4), defaultFrameTime);
        rolling.addFrame(sawSprites.getSprite(5), defaultFrameTime);
        rolling.addFrame(sawSprites.getSprite(6), defaultFrameTime);
        rolling.addFrame(sawSprites.getSprite(7), defaultFrameTime);
        rolling.setLoop(true);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(rolling);
        stateMachine.setDefaultState(rolling.title);
        saw.addComponent(stateMachine);

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Static);
        rb.setFixedRotation(true);
        rb.setContinuousCollision(false);
        saw.addComponent(rb);

        CircleCollider circleCollider = new CircleCollider();
        circleCollider.setRadius(0.25f / 2);
        saw.addComponent(circleCollider);
        saw.addComponent(new Spikes());

        return saw;
    }

    public static GameObject generateFan() {
        Spritesheet fanSprites = AssetPool.getSpritesheet("assets/images/fan.png");
        GameObject fan = generateSpriteObject(fanSprites.getSprite(0), 0.50f, 0.25f);

        AnimationState spinning = new AnimationState();
        spinning.title = "Spinning";
        float defaultFrameTime = 0.01f;
        spinning.addFrame(fanSprites.getSprite(0), defaultFrameTime);
        spinning.addFrame(fanSprites.getSprite(1), defaultFrameTime);
        spinning.addFrame(fanSprites.getSprite(2), defaultFrameTime);
        spinning.addFrame(fanSprites.getSprite(3), defaultFrameTime);
        spinning.addFrame(fanSprites.getSprite(4), defaultFrameTime);
        spinning.addFrame(fanSprites.getSprite(5), defaultFrameTime);
        spinning.addFrame(fanSprites.getSprite(6), defaultFrameTime);
        spinning.addFrame(fanSprites.getSprite(7), defaultFrameTime);
        spinning.setLoop(true);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(spinning);
        stateMachine.setDefaultState(spinning.title);
        fan.addComponent(stateMachine);

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Static);
        rb.setFixedRotation(false);
        rb.setContinuousCollision(false);
        fan.addComponent(rb);

        Box2DCollider box2DCollider = new Box2DCollider();
        box2DCollider.setHalfSize(new Vector2f(0.50f, 1.0f));
        box2DCollider.setOffset(new Vector2f(0.0f, 0.4f));
        fan.addComponent(box2DCollider);
        fan.addComponent(new Fan());

        return fan;
    }

    public static GameObject generatePlatform() {
        Spritesheet platformSprites = AssetPool.getSpritesheet("assets/images/platform.png");
        GameObject platform = generateSpriteObject(platformSprites.getSprite(0), 0.50f, 0.25f);

        AnimationState spinning = new AnimationState();
        spinning.title = "Spinning";
        float defaultFrameTime = 0.01f;
        spinning.addFrame(platformSprites.getSprite(0), defaultFrameTime);
        spinning.addFrame(platformSprites.getSprite(1), defaultFrameTime);
        spinning.addFrame(platformSprites.getSprite(2), defaultFrameTime);
        spinning.addFrame(platformSprites.getSprite(3), defaultFrameTime);
        spinning.addFrame(platformSprites.getSprite(4), defaultFrameTime);
        spinning.addFrame(platformSprites.getSprite(5), defaultFrameTime);
        spinning.addFrame(platformSprites.getSprite(6), defaultFrameTime);
        spinning.addFrame(platformSprites.getSprite(7), defaultFrameTime);
        spinning.setLoop(true);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(spinning);
        stateMachine.setDefaultState(spinning.title);
        platform.addComponent(stateMachine);

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Static);
        rb.setFixedRotation(false);
        rb.setContinuousCollision(false);
        platform.addComponent(rb);

        Box2DCollider box2DCollider = new Box2DCollider();
        box2DCollider.setHalfSize(new Vector2f(0.50f, 0.19f));
        box2DCollider.setOffset(new Vector2f(0.0f, 0.04f));
        platform.addComponent(box2DCollider);
        platform.addComponent(new Platform());

        return platform;
    }

    public static GameObject generateJumpingArrow() {
        Spritesheet jumpingArrowSprites = AssetPool.getSpritesheet("assets/images/arrow.png");
        GameObject jumpingArrow = generateSpriteObject(jumpingArrowSprites.getSprite(0), 0.25f, 0.25f);

        AnimationState bouncing = new AnimationState();
        bouncing.title = "Bouncing";
        float defaultFrameTime = 0.1f;
        bouncing.addFrame(jumpingArrowSprites.getSprite(0), defaultFrameTime);
        bouncing.addFrame(jumpingArrowSprites.getSprite(1), defaultFrameTime);
        bouncing.addFrame(jumpingArrowSprites.getSprite(2), defaultFrameTime);
        bouncing.addFrame(jumpingArrowSprites.getSprite(3), defaultFrameTime);
        bouncing.setLoop(true);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(bouncing);
        stateMachine.setDefaultState(bouncing.title);
        jumpingArrow.addComponent(stateMachine);

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Static);
        rb.setFixedRotation(false);
        rb.setContinuousCollision(false);
        jumpingArrow.addComponent(rb);

        CircleCollider circleCollider = new CircleCollider();
        circleCollider.setRadius(0.15f);
        jumpingArrow.addComponent(circleCollider);
        jumpingArrow.addComponent(new JumpingArrow());

        return jumpingArrow;
    }

    public static GameObject generateArrowShooter() {
        Spritesheet arrowShooterSprites = AssetPool.getSpritesheet("assets/images/shooter_arrow.png");
        GameObject arrowShooter = generateSpriteObject(arrowShooterSprites.getSprite(0), 0.25f, 0.25f);

        AnimationState throwArrow = new AnimationState();
        throwArrow.title = "Spinning";
        float defaultFrameTime = 0.1f;
        throwArrow.addFrame(arrowShooterSprites.getSprite(0), 0.4f);
        throwArrow.addFrame(arrowShooterSprites.getSprite(1), 0.4f);
        throwArrow.addFrame(arrowShooterSprites.getSprite(4), defaultFrameTime);
        throwArrow.addFrame(arrowShooterSprites.getSprite(5), defaultFrameTime);
        throwArrow.setLoop(true);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(throwArrow);
        stateMachine.setDefaultState(throwArrow.title);
        arrowShooter.addComponent(stateMachine);

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Static);
        rb.setFixedRotation(false);
        rb.setContinuousCollision(false);
        arrowShooter.addComponent(rb);

        Box2DCollider box2DCollider = new Box2DCollider();
        box2DCollider.setHalfSize(new Vector2f(0.25f, 0.25f));
        arrowShooter.addComponent(box2DCollider);
        arrowShooter.addComponent(new ArrowShooter());

        return arrowShooter;
    }

    public static GameObject generateDeadlyArrow(Vector2f position) {
        Spritesheet arrowSprites = AssetPool.getSpritesheet("assets/images/shooter_arrow.png");
        GameObject arrow = generateSpriteObject(arrowSprites.getSprite(6), 0.25f, 0.25f);
        arrow.transform.position = position;

        AnimationState flying = new AnimationState();
        flying.title = "Flying";
        flying.addFrame(arrowSprites.getSprite(6), 0.1f);
        flying.setLoop(false);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(flying);
        stateMachine.setDefaultState(flying.title);
        arrow.addComponent(stateMachine);

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Dynamic);
        rb.setFixedRotation(true);
        rb.setContinuousCollision(false);
        arrow.addComponent(rb);

        CircleCollider circleCollider = new CircleCollider();
        circleCollider.setRadius(0.02f);
        arrow.addComponent(circleCollider);
        arrow.addComponent(new Projectile());

        return arrow;
    }

    public static GameObject generateFire(Vector2f position) {
        Spritesheet fireSprites = AssetPool.getSpritesheet("assets/images/fire.png");
        GameObject fire = generateSpriteObject(fireSprites.getSprite(0), 0.25f, 0.25f);
        fire.transform.position = position;

        AnimationState fireDefault = new AnimationState();
        fireDefault.title = "Fire";
        float defaultFrameTime = 0.1f;
        fireDefault.addFrame(fireSprites.getSprite(0), defaultFrameTime);
        fireDefault.addFrame(fireSprites.getSprite(1), defaultFrameTime);
        fireDefault.addFrame(fireSprites.getSprite(2), defaultFrameTime);
        fireDefault.addFrame(fireSprites.getSprite(3), defaultFrameTime);
        fireDefault.setLoop(true);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(fireDefault);
        stateMachine.setDefaultState(fireDefault.title);
        fire.addComponent(stateMachine);

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Static);
        rb.setFixedRotation(true);
        rb.setContinuousCollision(false);
        fire.addComponent(rb);

        Box2DCollider box2DCollider = new Box2DCollider();
        box2DCollider.setHalfSize(new Vector2f(0.15f, 0.25f));
        fire.addComponent(box2DCollider);
        fire.addComponent(new Fire());

        return fire;
    }

    public static GameObject generateFirebox() {
        Spritesheet fireboxSprites = AssetPool.getSpritesheet("assets/images/firebox.png");
        GameObject firebox = generateSpriteObject(fireboxSprites.getSprite(0), 0.25f, 0.25f);

        AnimationState fireboxDefault = new AnimationState();
        fireboxDefault.title = "Firebox Default";
        float defaultFrameTime = 0.1f;
        fireboxDefault.addFrame(fireboxSprites.getSprite(4), defaultFrameTime);
        fireboxDefault.addFrame(fireboxSprites.getSprite(5), defaultFrameTime);
        fireboxDefault.addFrame(fireboxSprites.getSprite(6), defaultFrameTime);
        fireboxDefault.addFrame(fireboxSprites.getSprite(7), defaultFrameTime);
        fireboxDefault.addFrame(fireboxSprites.getSprite(9), defaultFrameTime);
        fireboxDefault.addFrame(fireboxSprites.getSprite(10), defaultFrameTime);
        fireboxDefault.addFrame(fireboxSprites.getSprite(11), defaultFrameTime);
        fireboxDefault.addFrame(fireboxSprites.getSprite(12), defaultFrameTime);
        fireboxDefault.setLoop(true);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(fireboxDefault);
        stateMachine.setDefaultState(fireboxDefault.title);
        firebox.addComponent(stateMachine);

        Rigidbody2D rb = new Rigidbody2D();
        rb.setBodyType(BodyType.Static);
        rb.setFixedRotation(true);
        rb.setContinuousCollision(false);
        firebox.addComponent(rb);

        Box2DCollider box2DCollider = new Box2DCollider();
        box2DCollider.setHalfSize(new Vector2f(0.25f, 0.25f));
        firebox.addComponent(box2DCollider);
        firebox.addComponent(new Firebox());
        firebox.addComponent(new Ground());

        return firebox;
    }
}
