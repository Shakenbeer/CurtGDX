package com.shakenbeer.curtandray;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CurtAndRay extends Game {
    SpriteBatch batch;

    @Override
    public void create() {
       
        batch = new SpriteBatch();
        setScreen(new SplashScreen(this));       
        Assets.queueLoading();
        Settings.load();
    }

    @Override
    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        Assets.clear();
    }
}
