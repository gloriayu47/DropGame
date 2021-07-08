package com.gloria.drop;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * 暂停screen
 * @author gloria yu
 */
public class PauseScreen implements Screen {

    final Drop game;
    OrthographicCamera camera;

    public PauseScreen(final Drop game) {
        this.game = game;
        camera=new OrthographicCamera();
        camera.setToOrtho(false,800,640);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    }
}
