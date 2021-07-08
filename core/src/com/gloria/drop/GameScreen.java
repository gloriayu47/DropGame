package com.gloria.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {

    // final修饰 引用类型 时未赋值需要在构造函数中初始化
    final Drop game;

    Texture dropImage;
    Texture bucketImage;
    Sound dropSound;
    Music rainMusic;
    Rectangle bucket;
    // Array class tries to minimize garbage as much as possible
    Array<Rectangle> raindrops;
    // We’ll store the time in nanoseconds(纳秒)
    long lastDropTime;
    int dropsGathered;
    OrthographicCamera camera;

    // 使用构造函数初始化，相当于create
    public GameScreen(final Drop game) {
        this.game = game;

        // 1. load the assets and store references to them
        // load the images for the droplet and the bucket, 64x64 pixels each
        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

        // load the drop sound effect and the rain background "music"
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav")); // sound < 10s
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        // start the playback of the background music immediately
        rainMusic.setLooping(true);
        //rainMusic.play();

        // create the camera and SpriteBatch
        camera = new OrthographicCamera();
        // make sure the camera always shows us an area of our game world that is 800x480 units wide
        camera.setToOrtho(false, 800, 480);

        // create a Rectangle to logically represent the bucket
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2; // centered horizontally
        bucket.y = 20;// 20 pixels above the bottom edge of the screen
        bucket.width = 64;
        bucket.height = 64;

        // create the raindrops array ans spawn the first drop
        raindrops = new Array<Rectangle>();
        spawnRaindrop();
    }

    // To facilitate(促进) the creation of raindrops
    // spawn(创建)
    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);// 水平随机
        raindrop.y = 480;//  top edge of the screen
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    // render(渲染)
    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color
        // red, green, blue and alpha component of that color, each within the range [0, 1]
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // tell the camera to update its matrices.
        camera.update();

        // render bucket

        //SpriteBatch use the coordinate system specified by the camera
        //projection(投影) matrix-camera.combined
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket an all drops
        game.batch.begin();
        // render font
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
        // render bucket
        game.batch.draw(bucketImage, bucket.x, bucket.y);
        for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        game.batch.end();

        // making the bucket move
        // 1. Touch/Mouse
        if (Gdx.input.isTouched()) {
            // 把touchPos编程Drop类的一个字段，而不是一直把它实例化
            // 因为垃圾收集器必须经常启动以收集这些short-lived对象，在Android例会导致卡顿
            Vector3 touchPos = new Vector3();// Vector3-three dimensional vector
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos); //transform to camera's coordinate system
            bucket.x = touchPos.x - 64 / 2; // center around the touch/mouse coordinates
        }
        // 2. keyboard 点击左/右cursor(光标)让200像素/单位每秒向左/右移动
        // ime-based movement需要知道the time that passed in between the last and the current rendering frame.
        // Gdx.graphics.getDeltaTime() returns the time passed between the last and the current frame in seconds
        // 修改bucket的x坐标，但是要限制在screen内
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            bucket.x += 200 * Gdx.graphics.getDeltaTime();
        }
        if (bucket.x < 0) {
            bucket.x = 0;
        }
        if (bucket.x > 800 - 64) {
            bucket.x = 800 - 64;
        }

        // 设置隔 1s 时间创建新雨滴
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            spawnRaindrop();
        }

        // make raindrops move
        for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext(); ) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            // 如果雨滴顶部超过screen底部则移除
            if (raindrop.y + 64 < 0) {
                iter.remove();
            }
            // 如果雨滴碰击bucket，播放音乐，移除雨滴
            if (raindrop.overlaps(bucket)) {
                dropsGathered++;
                dropSound.play();
                iter.remove();
            }

        }

    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        rainMusic.play();
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }
}
