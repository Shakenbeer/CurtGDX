package com.shakenbeer.curtandray;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MainMenuScreen implements Screen {
    
    final CurtAndRay game;  
    OrthographicCamera camera;
    
    BitmapFont font;

    public MainMenuScreen(CurtAndRay game) {
        this.game = game;
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 768, 1184);
        
        font = new BitmapFont(Gdx.files.internal("KG10kReasons.fnt"));
        font.setColor(Color.WHITE);
    }

    @Override
    public void render(float delta) {
        update();
        draw();
        
    }

    private void update() {
        // TODO Auto-generated method stub
        
    }
    
    private void draw() {
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        
        game.batch.draw(Assets.backgrounds[0], 0, 0);
        game.batch.draw(Assets.logo, 34, 654);
        game.batch.draw(Assets.buttonPlay, 134, 384);
        game.batch.draw(Assets.buttonHowTo, 184, 184);
        if (Settings.soundEnabled) {
            game.batch.draw(Assets.buttonSoundOn, 0, 0); 
        } else {
            game.batch.draw(Assets.buttonSoundOff, 0, 0);
        }
        if (Settings.hardMode) {
            game.batch.draw(Assets.buttonHardModeOn, 628, 0);
        } else {
            game.batch.draw(Assets.buttonHardModeOff, 628, 0);
        }
        game.batch.draw(Assets.buttonSpeed, 488, 0);
        font.draw(game.batch, Integer.toString(Settings.gameSpeed), 550, 75);
        
        
        game.batch.end();
        
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void show() {
        
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        
    }

}
