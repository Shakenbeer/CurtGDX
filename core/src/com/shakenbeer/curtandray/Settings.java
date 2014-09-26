package com.shakenbeer.curtandray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Settings {
    private static final String SETTINGS_FILE = ".curdandray";

    public static final int MAX_LEVEL_NUM = 84;

    public static boolean soundEnabled = true;
    public static boolean hardMode = false;
    public static int currentLevel = 1;
    public static int presentsCollected = 0;
    public static int gameSpeed = 1;

    public static void load() {
        BufferedReader in = null;
        try {
            FileHandle filehandle = Gdx.files.external(SETTINGS_FILE);
            in = filehandle.reader(1024);
            
            soundEnabled = Boolean.parseBoolean(in.readLine());
            hardMode = Boolean.parseBoolean(in.readLine());
            gameSpeed = Integer.parseInt(in.readLine());
            currentLevel = Integer.parseInt(in.readLine());
            presentsCollected = Integer.parseInt(in.readLine());
        } catch (NumberFormatException e) {
            // :( It's ok we have defaults
        } catch (Throwable e) {
            // :/ It's ok, defaults save our day
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }

    public static void save() {
        BufferedWriter out = null;
        try {
            FileHandle filehandle = Gdx.files.external(SETTINGS_FILE);
            
            out = new BufferedWriter(new OutputStreamWriter(filehandle.write(false, 1024)));
            List<String> settings = new ArrayList<String>();
            settings.add(Boolean.toString(soundEnabled));
            settings.add(Boolean.toString(hardMode));
            settings.add(Integer.toString(gameSpeed));
            settings.add(Integer.toString(currentLevel));
            settings.add(Integer.toString(presentsCollected));
            IOUtils.writeLines(settings, null, out);
        } catch (IOException e) {
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
            }
        }
    }
}
