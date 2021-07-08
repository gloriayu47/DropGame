package com.gloria.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class Drop extends Game {

    // SpriteBatch: render objects onto the screen
    // BitmapFont: render text onto the screen
    SpriteBatch batch;
    BitmapFont font;

    @Override
    public void create() {
       batch=new SpriteBatch();
        // use LibGDX's default Arial font
       font=new BitmapFont();
       this.setScreen(new MainMenuScreen(this));
    }

    // 渲染
    @Override
    public void render() {

        //
       super.render();// important
    }

    @Override
    public void dispose() {
		batch.dispose(); // SpriteBatch记得手动处理
        font.dispose();
    }
    /*
    通常情况下，人们会实现一个暂停屏幕，并要求用户触摸屏幕来继续。
    看看ApplicationAdapter.pause()和ApplicationAdapter.resume()方法。
     */
}
