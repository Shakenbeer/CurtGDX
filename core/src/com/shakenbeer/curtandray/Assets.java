package com.shakenbeer.curtandray;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Assets {

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

        backgrounds[0] = new Texture(Gdx.files.internal("background0.png"));
        backgrounds[1] = new Texture(Gdx.files.internal("background1.png"));
        backgrounds[2] = new Texture(Gdx.files.internal("background2.png"));
        backgrounds[3] = new Texture(Gdx.files.internal("background3.png"));

        buttonArrowLeft = new Texture(Gdx.files.internal("button_arrow_left.png"));
        buttonArrowRight = new Texture(Gdx.files.internal("button_arrow_right.png"));
        curt = new Texture(Gdx.files.internal("curt.png"));
        digits = new Texture(Gdx.files.internal("digits.png"));
        flag = new Texture(Gdx.files.internal("flag.png"));
        buttonHowTo = new Texture(Gdx.files.internal("button_how_to.png"));
        screenHowTo1 = new Texture(Gdx.files.internal("screen_how_to_1.png"));
        screenHowTo2 = new Texture(Gdx.files.internal("screen_how_to_2.png"));
        screenHowTo3 = new Texture(Gdx.files.internal("screen_how_to_3.png"));
        screenLevel = new Texture(Gdx.files.internal("screen_level.png"));
        logo = new Texture(Gdx.files.internal("logo.png"));
        mine = new Texture(Gdx.files.internal("mine.png"));
        nextLevel = new Texture(Gdx.files.internal("next_level.png"));
        screenPause = new Texture(Gdx.files.internal("screen_pause.png"));
        buttonPlay = new Texture(Gdx.files.internal("button_play.png"));
        ray = new Texture(Gdx.files.internal("ray.png"));
        buttonSoundOn = new Texture(Gdx.files.internal("button_sound_on.png"));
        buttonSoundOff = new Texture(Gdx.files.internal("button_sound_off.png"));
        buttonStart = new Texture(Gdx.files.internal("button_start.png"));
        buttonClose = new Texture(Gdx.files.internal("button_close.png"));
        buttonPause = new Texture(Gdx.files.internal("button_pause.png"));
        levelNum = new Texture(Gdx.files.internal("level_num.png"));
        levelNumClosed = new Texture(Gdx.files.internal("level_num_closed.png"));
        levelFailed = new Texture(Gdx.files.internal("failed.png"));
        buttonHardModeOff = new Texture(Gdx.files.internal("button_hard_mode_off.png"));
        buttonHardModeOn = new Texture(Gdx.files.internal("button_hard_mode_on.png"));
        present = new Texture(Gdx.files.internal("present.png"));
        buttonSpeed = new Texture(Gdx.files.internal("speed.png"));

        FileHandle handle = Gdx.files.internal("levels");

        InputStream in = null;
        try {
            in = handle.read();
            List<String> list = IOUtils.readLines(in);
            String[] buff = new String[list.size()];
            levels = new Array<String>(list.toArray(buff));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load levels from asset");
        } finally {
            if (in != null) {
                IOUtils.closeQuietly(in);
            }
        }
        
        soundClick = Gdx.audio.newSound(Gdx.files.internal("click.ogg"));
        soundHideMine = Gdx.audio.newSound(Gdx.files.internal("hide_mine.ogg"));
        soundSetupFlag = Gdx.audio.newSound(Gdx.files.internal("setup_flag.ogg"));
        soundSetupMine = Gdx.audio.newSound(Gdx.files.internal("setup_mine.ogg"));
        soundWin = Gdx.audio.newSound(Gdx.files.internal("win.ogg"));
        soundLose = Gdx.audio.newSound(Gdx.files.internal("lose.ogg"));
        soundWoosh = Gdx.audio.newSound(Gdx.files.internal("woosh.ogg"));
        soundPresent = Gdx.audio.newSound(Gdx.files.internal("nishtyak.ogg"));
        
    }

}
