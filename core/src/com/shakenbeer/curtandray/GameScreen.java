package com.shakenbeer.curtandray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class GameScreen implements Screen {

    private static final int SPEED_FACTOR = 50000;
    private static final int HIDED_OPACITY = 90;
    private static final int START_Y = 14;
    private static final int START_X = 478;
    private static final int ARROW_RIGHT_Y = 14;
    private static final int ARROW_RIGHT_X = 180;
    private static final int ARROW_LEFT_Y = 14;
    private static final int ARROW_LEFT_X = 30;
    private static final int NEXT_LEVEL_Y = 1084;
    private static final int NEXT_LEVEL_X = 34;
    private static final int PLAY_BOUND_BOTTOM = 134;
    private static final int PLAY_BOUND_TOP = 1084;
    private static final int FLAG_PIVOT_Y = 8;
    private static final int FLAG_PIVOT_X = 9;
    private static final int MINE_RADIUS = 32;
    private static final int MINE_PIVOT_Y = 32;
    private static final int MINE_PIVOT_X = 32;
    private static final int TARGET_RADIUS = 100;
    private static final int DEFAULT_CHAR_SPEED = 120000;
    private static final int RAY_INIT_Y = 1115;
    private static final int RAY_INIT_X = 384;
    private static final int CURT_INIT_Y = 66;
    private static final int CURT_INIT_X = 384;
    private static final int CHAR_RADIUS = 32;
    private static final int CHAR_PIVOT_Y = 56;
    private static final int CHAR_PIVOT_X = 55;
    private static final int PRESENT_RADIUS = 40;
    private static final int PRESENT_PIVOT_X = 40;
    private static final int PRESENT_PIVOT_Y = 40;

    enum LevelStage {
        Ray, RayHide, BuildPath, Curt, LevelStart, LevelPaused, LevelFailed
    }

    final CurtAndRay game;
    private final OrthographicCamera camera;
    private Vector3 touchPos;

    int level;
    LevelStage stage = LevelStage.LevelStart;
    LevelStage beforePause;

    final InterfaceObject nextLevel;
    final InterfaceObject arrowLeft;
    final InterfaceObject arrowRight;
    final InterfaceObject start;
    final InterfaceObject pause;

    final GameObject curt;
    final GameObject ray;
    final List<GameObject> chars;
    final List<GameObject> mines;
    final List<GameObject> flags;
    final List<GameObject> removed;
    final List<GameObject> presents;
    Queue<int[]> minePos;

    int[] rayTarget;
    int[] curtTarget;

    private int hidedOpacity;
    private Random random = new Random();
    private int presentsCollected;

    public Texture background;
    private float rvSqrPrev;

    public GameScreen(CurtAndRay curtAndRay, int selected) {
        game = curtAndRay;
        level = selected;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 768, 1184);
        touchPos = new Vector3();

        Gdx.input.setInputProcessor(new GameInputAdapter());

        nextLevel = new InterfaceObject(NEXT_LEVEL_X, NEXT_LEVEL_Y, Assets.nextLevel);
        arrowLeft = new InterfaceObject(ARROW_LEFT_X, ARROW_LEFT_Y, Assets.buttonArrowLeft);
        arrowRight = new InterfaceObject(ARROW_RIGHT_X, ARROW_RIGHT_Y, Assets.buttonArrowRight);
        start = new InterfaceObject(START_X, START_Y, Assets.buttonStart);
        pause = new InterfaceObject(0, 1094, Assets.buttonPause);

        curt = new GameObject(CHAR_PIVOT_X, CHAR_PIVOT_Y, CHAR_RADIUS, Assets.curt);

        ray = new GameObject(CHAR_PIVOT_X, CHAR_PIVOT_Y, CHAR_RADIUS, Assets.ray);

        chars = new ArrayList<>();
        minePos = new LinkedList<int[]>();
        mines = new LinkedList<GameObject>();
        flags = new LinkedList<GameObject>();
        removed = new LinkedList<GameObject>();
        presents = new LinkedList<GameObject>();

        hidedOpacity = Settings.hardMode ? 0 : HIDED_OPACITY;

        initLevel();
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();

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
        Settings.save();
        if (stage != LevelStage.LevelStart && stage != LevelStage.LevelFailed) {
            pauseGame();
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    private void initLevel() {
        int back = ((level - 1) / 7) % 4;
        background = Assets.backgrounds[back];

        curt.setPos(CURT_INIT_X, CURT_INIT_Y);
        curt.setAngle(0);

        ray.setPos(RAY_INIT_X, RAY_INIT_Y);
        ray.setAngle(180);

        chars.add(curt);
        chars.add(ray);

        minePos.clear();
        mines.clear();
        flags.clear();
        removed.clear();
        presents.clear();

        curtTarget = null;
        rayTarget = null;

        loadMines();
        generatePresents();
        presentsCollected = 0;

    }

    private void generatePresents() {
        float f = random.nextFloat();
        int presentsCount = 1;
        if (f >= 0.5f) {
            presentsCount++;
        }
        if (f >= 0.9f) {
            presentsCount++;
        }
        Texture pixmap = Assets.present;
        for (int i = 0; i < presentsCount; i++) {
            GameObject present = new GameObject(PRESENT_PIVOT_X, PRESENT_PIVOT_Y, PRESENT_RADIUS, pixmap);
            float x = random.nextInt(768 - pixmap.getWidth()) + pixmap.getWidth() / 2;
            float y = random.nextInt(PLAY_BOUND_TOP - PLAY_BOUND_BOTTOM - pixmap.getHeight()) + PLAY_BOUND_BOTTOM
                    + pixmap.getHeight() / 2;
            present.setPos(x, y);
            presents.add(present);
        }
    }

    private void loadMines() {

        String levelString = Assets.levels.get(level - 1);
        String[] levelArray = levelString.split(",");
        int len = levelArray.length - levelArray.length % 2;

        for (int i = 0; i < len;) {
            int x = Integer.parseInt(levelArray[i++]);
            int y = Integer.parseInt(levelArray[i++]);
            minePos.add(new int[] { x, y });
        }
    }

    private void update(float delta) {
        if (stage == LevelStage.Ray) {
            updateRayStage(delta);
        } else if (stage == LevelStage.RayHide) {
            updateRayHideStage(delta);
        } else if (stage == LevelStage.Curt) {
            updateCurtStage(delta);
        }
    }

    private void draw() {
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        nextLevel.draw(game.batch);
        arrowLeft.draw(game.batch);
        arrowRight.draw(game.batch);
        start.draw(game.batch);
        pause.draw(game.batch);

        int len = mines.size();
        for (int i = 0; i < len; i++) {
            mines.get(i).draw(game.batch);
        }

        len = chars.size();
        for (int i = 0; i < len; i++) {
            chars.get(i).draw(game.batch);
        }

        len = flags.size();
        for (int i = 0; i < len; i++) {
            flags.get(i).draw(game.batch);
        }
        
        len = presents.size();
        for (int i = 0; i < len; i++) {
            presents.get(i).draw(game.batch);
        }

        if (stage == LevelStage.LevelStart) {
            game.batch.draw(Assets.screenLevel, 84, 334);
            drawText(String.valueOf(level), 492, 620);
        }
        if (stage == LevelStage.LevelPaused) {
            game.batch.draw(Assets.screenPause, 182, 434);
        }
        if (stage == LevelStage.LevelFailed) {
            game.batch.draw(Assets.levelFailed, 182, 359);
        }

        game.batch.end();
    }

    private void drawText(String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);
            int n = character - '0';
            game.batch.draw(Assets.digits, x, y, n * 82, 0, 82, 130);
            x += 82;
        }
    }

    private void updateRayStage(float delta) {
        float[] rv;

        if (rayTarget != null) {
            rv = directionVector(ray, rayTarget);
        } else {
            rayTarget = minePos.remove();
            rv = directionVector(ray, rayTarget);
            changeDirection(ray, rv);
            rvSqrPrev = Float.MAX_VALUE;
        }

        float rvSqr = rv[0] * rv[0] + rv[1] * rv[1];

        if (rvSqr < TARGET_RADIUS || rvSqr > rvSqrPrev) {
            GameObject mine = new GameObject(MINE_PIVOT_X, MINE_PIVOT_Y, MINE_RADIUS, Assets.mine);
            mine.setPos(rayTarget[0], rayTarget[1]);
            mines.add(mine);
            ray.setPos(rayTarget[0], rayTarget[1]);
            rayTarget = null;
            if (Settings.soundEnabled) {
                Assets.soundSetupMine.play();
            }
            if (minePos.isEmpty()) {
                stage = LevelStage.RayHide;
            }
        } else {
            rvSqrPrev = rvSqr;
        }
        ray.move(delta);

    }

    private void updateRayHideStage(float delta) {
        float[] rv;
        GameObject mine;

        if (rayTarget != null) {
            rv = directionVector(ray, rayTarget);
        } else {
            mine = mines.get(0);
            rayTarget = new int[] { (int) mine.posX(), (int) mine.posY() };
            rv = directionVector(ray, rayTarget);
            changeDirection(ray, rv);
            rvSqrPrev = Float.MAX_VALUE;
        }

        float rvSqr = rv[0] * rv[0] + rv[1] * rv[1];

        if (rvSqr < TARGET_RADIUS || rvSqr > rvSqrPrev) {
            minePos.add(new int[] { rayTarget[0], rayTarget[1] });
            mine = mines.remove(0);
            mine.setOpacity(hidedOpacity);
            mines.add(mine);
            ray.setPos(rayTarget[0], rayTarget[1]);
            rayTarget = null;
            if (Settings.soundEnabled) {
                Assets.soundHideMine.play();
            }
            if (mines.get(0).getOpacity() < 255) {
                ray.velNormSqr = 0;
                chars.remove(ray);
                hideMines();
                stage = LevelStage.BuildPath;
            }
        } else {
            rvSqrPrev = rvSqr;
        }
        ray.move(delta);
    }

    private void hideMines() {
        int len = mines.size();
        for (int i = 0; i < len; i++) {
            mines.get(i).setOpacity(0);
        }

    }

    private void updateCurtStage(float delta) {
        float[] rv;

        if (curtTarget != null) {
            rv = directionVector(curt, curtTarget);
        } else {
            if (!flags.isEmpty()) {
                GameObject flag = flags.get(0);
                curtTarget = new int[] { (int) flag.posX(), (int) flag.posY() };
            } else {
                curtTarget = new int[] { (int) curt.posX(), 1384 };
            }
            rv = directionVector(curt, curtTarget);
            changeDirection(curt, rv);
            rvSqrPrev = Float.MAX_VALUE;
        }

        float rvSqr = rv[0] * rv[0] + rv[1] * rv[1];

        if (rvSqr < TARGET_RADIUS || rvSqr > rvSqrPrev) {
            if (Settings.soundEnabled) {
                Assets.soundWoosh.play();
            }
            curt.setPos(curtTarget[0], curtTarget[1]);
            flags.remove(0);
            curtTarget = null;
        } else {
            rvSqrPrev = rvSqr;
        }

        if (curt.posY() > 1184 + curt.getHeight()) {
            curt.velNormSqr = 0;
            chars.remove(curt);
            increaseLevel();
            Settings.presentsCollected += presentsCollected;
            stage = LevelStage.LevelStart;
            initLevel();
            if (Settings.soundEnabled) {
                Assets.soundWin.play();
            }
        }

        curt.move(delta);

        if (checkCollisions()) {
            if (Settings.soundEnabled) {
                Assets.soundLose.play();
            }
            stage = LevelStage.LevelFailed;
        }

        checkPresents();

    }

    private void pauseGame() {
        beforePause = stage;
        stage = LevelStage.LevelPaused;
    }

    private void resumeGame() {
        stage = beforePause;
    }

    private float[] directionVector(GameObject mo, int[] target) {
        return new float[] { target[0] - mo.posX(), target[1] - mo.posY() };
    }

    private void changeDirection(GameObject mo, float[] rv) {
        float k = (float) Math.sqrt(mo.velNormSqr / (rv[0] * rv[0] + rv[1] * rv[1]));
        mo.velX = rv[0] * k;
        mo.velY = rv[1] * k;
        mo.setAngle((float) Math.toDegrees(Math.atan2(mo.velY, mo.velX) - Math.PI / 2));
    }

    private boolean checkCollisions() {
        int len = mines.size();
        for (int i = 0; i < len; i++) {
            GameObject mine = mines.get(i);
            if (collide(curt, mine)) {
                return true;
            }
        }
        return false;
    }

    private void checkPresents() {
        int len = presents.size();
        for (int i = 0; i < len; i++) {
            GameObject present = presents.get(i);
            if (collide(curt, present)) {
                if (Settings.soundEnabled) {
                    Assets.soundPresent.play(1);
                }
                presentsCollected++;
                presents.remove(present);
                return;
            }
        }
    }

    private boolean collide(GameObject go1, GameObject go2) {
        float distSqr = (go1.posX() - go2.posX()) * (go1.posX() - go2.posX()) + (go1.posY() - go2.posY())
                * (go1.posY() - go2.posY());
        return (distSqr < (go1.radius + go2.radius) * (go1.radius + go2.radius));
    }

    private void increaseLevel() {
        if (level == Settings.MAX_LEVEL_NUM) {
            level = 1;
        } else {
            level++;
        }
        if (Settings.currentLevel < level) {
            Settings.currentLevel = level;
            Settings.save();
        }
    }

    public class GameInputAdapter extends InputAdapter {
        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            touchPos.set(screenX, screenY, 0);
            camera.unproject(touchPos);
            boolean result = true;

            if (stage != LevelStage.LevelStart && stage != LevelStage.LevelFailed) {
                if (pause.rect.contains(touchPos.x, touchPos.y)) {
                    pauseGame();
                    if (Settings.soundEnabled) {
                        Assets.soundClick.play();
                    }
                }
            } else {
                result = false;
            }

            if (stage == LevelStage.LevelStart) {
                if ((touchPos.x > 84 && touchPos.x < 684 - 1 && touchPos.y > 334 && touchPos.y < 784 - 1)
                        || start.rect.contains(touchPos.x, touchPos.y)) {
                    stage = LevelStage.Ray;
                    ray.velNormSqr = DEFAULT_CHAR_SPEED + SPEED_FACTOR * (Settings.gameSpeed - 1);
                    if (Settings.soundEnabled) {
                        Assets.soundClick.play();
                    }
                }
            } else if (stage == LevelStage.RayHide) {
                if ((touchPos.y > PLAY_BOUND_BOTTOM && touchPos.y < PLAY_BOUND_TOP)
                        || start.rect.contains(touchPos.x, touchPos.y)) {
                    if (Settings.soundEnabled) {
                        Assets.soundClick.play();
                    }
                    int mCount = mines.size();
                    for (int j = 0; j < mCount; j++) {
                        if (mines.get(j).getOpacity() < 255) {
                            break;
                        }
                        minePos.add(new int[] { (int) mines.get(j).posX(), (int) mines.get(j).posY() });
                    }
                    ray.velNormSqr = 0;
                    chars.remove(ray);
                    hideMines();
                    stage = LevelStage.BuildPath;
                }
            } else if (stage == LevelStage.BuildPath) {
                if (touchPos.y > PLAY_BOUND_BOTTOM && touchPos.y < PLAY_BOUND_TOP) {
                    GameObject flag = new GameObject(FLAG_PIVOT_X, FLAG_PIVOT_Y, 0, Assets.flag);
                    flag.setPos(touchPos.x, touchPos.y);
                    flags.add(flag);
                    if (Settings.soundEnabled) {
                        Assets.soundSetupFlag.play();
                    }
                    removed.clear();
                }
                if (arrowLeft.rect.contains(touchPos.x, touchPos.y)) {
                    if (!flags.isEmpty()) {
                        removed.add(flags.remove(flags.size() - 1));
                    }
                    if (Settings.soundEnabled) {
                        Assets.soundClick.play();
                    }
                }
                if (arrowRight.rect.contains(touchPos.x, touchPos.y)) {
                    if (!removed.isEmpty()) {
                        flags.add(removed.remove(removed.size() - 1));
                    }
                    if (Settings.soundEnabled) {
                        Assets.soundClick.play();
                    }
                }
                if (start.rect.contains(touchPos.x, touchPos.y)) {
                    if (Settings.soundEnabled) {
                        Assets.soundClick.play();
                    }
                    int mCount = minePos.size();
                    for (int j = 0; j < mCount; j++) {
                        int[] pos = minePos.poll();
                        GameObject mine = new GameObject(MINE_PIVOT_X, MINE_PIVOT_Y, MINE_RADIUS, Assets.mine);
                        mine.setPos(pos[0], pos[1]);
                        mines.add(mine);
                    }
                    stage = LevelStage.Curt;
                    curt.velNormSqr = DEFAULT_CHAR_SPEED + SPEED_FACTOR * (Settings.gameSpeed - 1);
                }
            } else if (stage == LevelStage.LevelPaused) {
                if (touchPos.x > 182 && touchPos.x < 582 - 1 && touchPos.y > 584 && touchPos.y < 734 - 1) {
                    if (Settings.soundEnabled) {
                        Assets.soundClick.play();
                    }
                    resumeGame();
                }

                if (touchPos.x > 182 && touchPos.x < 582 - 1 && touchPos.y > 434 && touchPos.y < 584 - 1) {
                    if (Settings.soundEnabled) {
                        Assets.soundClick.play();
                    }
                    game.setScreen(new MainMenuScreen(game));
                }
            } else if (stage == LevelStage.LevelFailed) {
                if (touchPos.x > 182 && touchPos.x < 582 - 1 && touchPos.y > 359 && touchPos.y < 759 - 1) {
                    if (Settings.soundEnabled) {
                        Assets.soundClick.play();
                    }
                    stage = LevelStage.LevelStart;
                    initLevel();
                }
            } else {
                result = false;
            }

            return result;
        }
    }
}
