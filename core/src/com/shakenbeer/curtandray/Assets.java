package com.shakenbeer.curtandray;

import java.io.BufferedReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Assets {

    public static AssetManager manager = new AssetManager();

    public static Texture[] backgrounds = new Texture[4];

    public static Texture buttonArrowLeft;
    public static Texture buttonArrowRight;
    public static Texture curt;
    public static Texture digits;
    public static Texture flag;
    public static Texture buttonHighscores;
    public static Texture buttonHowTo;
    public static Texture screenHowTo1;
    public static Texture screenHowTo2;
    public static Texture screenHowTo3;
    public static Texture screenLevel;
    public static Texture logo;
    public static Texture mine;
    public static Texture nextLevel;
    public static Texture screenPause;
    public static Texture buttonPlay;
    public static Texture ray;
    public static Texture buttonSoundOn;
    public static Texture buttonSoundOff;
    public static Texture buttonStart;
    public static Texture buttonClose;
    public static Texture buttonPause;
    public static Texture levelNum;
    public static Texture levelNumClosed;
    public static Texture levelFailed;
    public static Texture buttonHardModeOff;
    public static Texture buttonHardModeOn;
    public static Texture present;
    public static Texture buttonSpeed;

    public static Sound soundClick;
    public static Sound soundHideMine;
    public static Sound soundSetupFlag;
    public static Sound soundSetupMine;
    public static Sound soundWin;
    public static Sound soundLose;
    public static Sound soundWoosh;
    public static Sound soundPresent;

    public static Array<String> levels;

    public static void load() {

        backgrounds[0] = manager.get("background0.png");
        backgrounds[1] = manager.get("background1.png");
        backgrounds[2] = manager.get("background2.png");
        backgrounds[3] = manager.get("background3.png");

        buttonArrowLeft = manager.get("button_arrow_left.png");
        buttonArrowRight = manager.get("button_arrow_right.png");
        curt = manager.get("curt.png");
        digits = manager.get("digits.png");
        flag = manager.get("flag.png");
        buttonHowTo = manager.get("button_how_to.png");
        screenHowTo1 = manager.get("screen_how_to_1.png");
        screenHowTo2 = manager.get("screen_how_to_2.png");
        screenHowTo3 = manager.get("screen_how_to_3.png");
        screenLevel = manager.get("screen_level.png");
        logo = manager.get("logo.png");
        mine = manager.get("mine.png");
        nextLevel = manager.get("next_level.png");
        screenPause = manager.get("screen_pause.png");
        buttonPlay = manager.get("button_play.png");
        ray = manager.get("ray.png");
        buttonSoundOn = manager.get("button_sound_on.png");
        buttonSoundOff = manager.get("button_sound_off.png");
        buttonStart = manager.get("button_start.png");
        buttonClose = manager.get("button_close.png");
        buttonPause = manager.get("button_pause.png");
        levelNum = manager.get("level_num.png");
        levelNumClosed = manager.get("level_num_closed.png");
        levelFailed = manager.get("failed.png");
        buttonHardModeOff = manager.get("button_hard_mode_off.png");
        buttonHardModeOn = manager.get("button_hard_mode_on.png");
        present = manager.get("present.png");
        buttonSpeed = manager.get("speed.png");

        soundClick = manager.get("click.ogg");
        soundHideMine = manager.get("hide_mine.ogg");
        soundSetupFlag = manager.get("setup_flag.ogg");
        soundSetupMine = manager.get("setup_mine.ogg");
        soundWin = manager.get("win.ogg");
        soundLose = manager.get("lose.ogg");
        soundWoosh = manager.get("woosh.ogg");
        soundPresent = manager.get("nishtyak.ogg");
        
        FileHandle handle = Gdx.files.internal("levels");
        levels = new Array<String>();
        BufferedReader in = null;
        try {
            in = handle.reader(1024);
            String line;
            while ((line = in.readLine()) != null) {
                levels.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load levels from asset");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    //
                }
            }
        }

    }

    public static void queueLoading() {
        manager.load("background0.png", Texture.class);
        manager.load("background1.png", Texture.class);
        manager.load("background2.png", Texture.class);
        manager.load("background3.png", Texture.class);

        manager.load("button_arrow_left.png", Texture.class);
        manager.load("button_arrow_right.png", Texture.class);
        manager.load("curt.png", Texture.class);
        manager.load("digits.png", Texture.class);
        manager.load("flag.png", Texture.class);
        manager.load("button_how_to.png", Texture.class);
        manager.load("screen_how_to_1.png", Texture.class);
        manager.load("screen_how_to_2.png", Texture.class);
        manager.load("screen_how_to_3.png", Texture.class);
        manager.load("screen_level.png", Texture.class);
        manager.load("logo.png", Texture.class);
        manager.load("mine.png", Texture.class);
        manager.load("next_level.png", Texture.class);
        manager.load("screen_pause.png", Texture.class);
        manager.load("button_play.png", Texture.class);
        manager.load("ray.png", Texture.class);
        manager.load("button_sound_on.png", Texture.class);
        manager.load("button_sound_off.png", Texture.class);
        manager.load("button_start.png", Texture.class);
        manager.load("button_close.png", Texture.class);
        manager.load("button_pause.png", Texture.class);
        manager.load("level_num.png", Texture.class);
        manager.load("level_num_closed.png", Texture.class);
        manager.load("failed.png", Texture.class);
        manager.load("button_hard_mode_off.png", Texture.class);
        manager.load("button_hard_mode_on.png", Texture.class);
        manager.load("present.png", Texture.class);
        manager.load("speed.png", Texture.class);

        manager.load("click.ogg", Sound.class);
        manager.load("hide_mine.ogg", Sound.class);
        manager.load("setup_flag.ogg", Sound.class);
        manager.load("setup_mine.ogg", Sound.class);
        manager.load("win.ogg", Sound.class);
        manager.load("lose.ogg", Sound.class);
        manager.load("woosh.ogg", Sound.class);
        manager.load("nishtyak.ogg", Sound.class);
    }

    public static boolean update() {
        return manager.update();
    }

    public static float getProgress() {
        return manager.getProgress();
    }

}
