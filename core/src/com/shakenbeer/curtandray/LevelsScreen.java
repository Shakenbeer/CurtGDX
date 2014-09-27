package com.shakenbeer.curtandray;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class LevelsScreen implements Screen {

    final CurtAndRay game;
    private final OrthographicCamera camera;
    private Vector3 touchPos;

    private static final int LEVELS_PER_PAGE = 28;
    private static final int PAGE_COUNT = 3;
    Rectangle right;
    Rectangle left;
    Rectangle levels;
    int page;

    public LevelsScreen(CurtAndRay curtAndRay) {
        game = curtAndRay;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 768, 1184);

        left = new Rectangle(0, 0, 120, 100);
        right = new Rectangle(648, 0, 120, 100);
        levels = new Rectangle(54, 164, 656, 980);
        page = 1;

        touchPos = new Vector3();

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                boolean result = true;
                touchPos.set(screenX, screenY, 0);
                camera.unproject(touchPos);

                if (levels.contains(touchPos.x, touchPos.y)) {
                    int selected = (1144 - (int) touchPos.y) / 140 * 4 + ((int) touchPos.x - 54) / 164 + 1
                            + LEVELS_PER_PAGE * (page - 1);
                    if (selected <= Settings.currentLevel) {
                        game.setScreen(new GameScreen(game, selected));
                    }
                } else if (page < PAGE_COUNT && right.contains(touchPos.x, touchPos.y)) {
                    page++;
                } else if (page > 1 && left.contains(touchPos.x, touchPos.y)) {
                    page--;
                } else if (page == 1 && left.contains(touchPos.x, touchPos.y)) {
                    game.setScreen(new MainMenuScreen(game));
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
        game.batch.draw(Assets.buttonArrowLeft, left.x, left.y);
        if (page < PAGE_COUNT) {
            game.batch.draw(Assets.buttonArrowRight, right.x, right.y);
        }

        for (int i = 0; i < LEVELS_PER_PAGE; i++) {
            if (i + 1 + LEVELS_PER_PAGE * (page - 1) <= Settings.currentLevel) {
                game.batch.draw(Assets.levelNum, i % 4 * 164 + 54, 1004 - i / 4 * 140);
            } else {
                game.batch.draw(Assets.levelNumClosed, i % 4 * 164 + 54, 1004 - i / 4 * 140);
            }
        }

        for (int i = 0; i < LEVELS_PER_PAGE; i++) {
            drawText(String.valueOf(i + LEVELS_PER_PAGE * (page - 1) + 1), i % 4 * 164 + 54, 1010 - i / 4 * 140);
        }

        game.batch.end();
    }

    public void drawText(String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);
            int n = character - '0';
            game.batch.draw(Assets.digits, x, y, n * 82, 0, 82, 130);
            x += 82;
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

    }

}
