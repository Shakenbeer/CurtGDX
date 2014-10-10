package com.shakenbeer.curtandray;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class SplashScreen implements Screen {
    private Texture texture = new Texture(Gdx.files.internal("splash.png"));

    final CurtAndRay game;
    private final OrthographicCamera camera;
    BitmapFont font =  new BitmapFont(Gdx.files.internal("KG10kReasons.fnt"));

    public SplashScreen(CurtAndRay game) {

        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 768, 1184);
        font.setColor(Color.BLACK);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(texture, 0, 0);
        int persentage = (int) (Assets.getProgress() * 100);
        font.draw(game.batch, Integer.toString(persentage) + "%", 340, 540);
        game.batch.end();       
        
        if (Assets.update()) { 
            game.setScreen(new MainMenuScreen(game));
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        texture.dispose();
    }

}
