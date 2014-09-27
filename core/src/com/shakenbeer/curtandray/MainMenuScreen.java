package com.shakenbeer.curtandray;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen {

    Rectangle play, howTo, sound, speed, mode;
    Vector3 touchPos;

    final CurtAndRay game;
    OrthographicCamera camera;

    BitmapFont font;

    public MainMenuScreen(final CurtAndRay game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 768, 1184);

        font = new BitmapFont(Gdx.files.internal("KG10kReasons.fnt"));

        play = new Rectangle(134, 384, 500, 200);
        howTo = new Rectangle(184, 184, 400, 150);
        sound = new Rectangle(0, 0, 140, 140);
        speed = new Rectangle(488, 0, 140, 140);
        mode = new Rectangle(628, 0, 140, 140);

        touchPos = new Vector3();

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                touchPos.set(screenX, screenY, 0);
                camera.unproject(touchPos);
                boolean result = true;
                if (play.contains(touchPos.x, touchPos.y)) {

                } else if (howTo.contains(touchPos.x, touchPos.y)) {
                    game.setScreen(new HowToScreen1());
                } else if (sound.contains(touchPos.x, touchPos.y)) {
                    Settings.soundEnabled = !Settings.soundEnabled;
                } else if (speed.contains(touchPos.x, touchPos.y)) {
                    Settings.gameSpeed++;
                    if (Settings.gameSpeed > 5) {
                        Settings.gameSpeed = 1;
                    }
                } else if (mode.contains(touchPos.x, touchPos.y)) {
                    Settings.hardMode = !Settings.hardMode;
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

    @Override
    public void render(float delta) {
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        game.batch.draw(Assets.backgrounds[0], 0, 0);
        game.batch.draw(Assets.logo, 34, 654);
        game.batch.draw(Assets.buttonPlay, play.x, play.y);
        game.batch.draw(Assets.buttonHowTo, howTo.x, howTo.y);
        if (Settings.soundEnabled) {
            game.batch.draw(Assets.buttonSoundOn, sound.x, sound.y);
        } else {
            game.batch.draw(Assets.buttonSoundOff, sound.x, sound.y);
        }
        game.batch.draw(Assets.buttonSpeed, speed.x, speed.y);
        font.setColor(Color.WHITE);
        font.draw(game.batch, Integer.toString(Settings.gameSpeed), 550, 75);
        if (Settings.hardMode) {
            game.batch.draw(Assets.buttonHardModeOn, mode.x, mode.y);
        } else {
            game.batch.draw(Assets.buttonHardModeOff, mode.x, mode.y);
        }

        game.batch.draw(Assets.present, 164, 14);
        font.setColor(Color.valueOf("00779EFF"));
        font.draw(game.batch, Integer.toString(Settings.presentsCollected), 260, 85);

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
        Settings.save();
    }

    @Override
    public void pause() {
        Settings.save();
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
    }

}
