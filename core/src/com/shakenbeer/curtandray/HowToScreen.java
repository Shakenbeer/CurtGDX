package com.shakenbeer.curtandray;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class HowToScreen implements Screen {
    
    final CurtAndRay game;
    private final OrthographicCamera camera;
    private Vector3 touchPos;
    private int screenNumber;
    private Texture howTo;
    private final Rectangle right;

    public HowToScreen(CurtAndRay curtAndRay) {
        this.game = curtAndRay;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 768, 1184);
        
        screenNumber = 1;
        right = new Rectangle(648, 0, 120, 100);
        howTo = chooseTexture();
        
        touchPos = new Vector3();
        
        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                boolean result = true;
                touchPos.set(screenX, screenY, 0);
                camera.unproject(touchPos);
                if (right.contains(touchPos.x, touchPos.y)) {
                    if (screenNumber < 3) {
                        screenNumber++;
                        howTo = chooseTexture();
                    } else {
                        game.setScreen(new MainMenuScreen(game));
                    }
                } else {
                    result = false;
                }
                
                if (result && Settings.soundEnabled) {
                    Assets.soundClick.play();
                }
                
                return result;
            }
            
        });
    }

    private Texture chooseTexture() {
        if (screenNumber == 1) {
            return Assets.screenHowTo1;
        } else if (screenNumber == 2) {
            return Assets.screenHowTo2;
        } else {
            return Assets.screenHowTo3;
        }
    }

    @Override
    public void render(float delta) {
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(Assets.backgrounds[0], 0, 0);
        game.batch.draw(howTo, 34, 284);
        if (screenNumber < 3) {
            game.batch.draw(Assets.buttonArrowRight, right.x, right.y);
        } else {
            game.batch.draw(Assets.buttonClose, right.x, right.y);
        }
        
        game.batch.end();
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
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
