package com.shakenbeer.curtandray;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class InterfaceObject {   
    Texture texture;
    Rectangle rect;
    
    public InterfaceObject(int x, int y, Texture texture) {
        this.texture = texture;
        rect = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }
    
    public void draw(Batch batch) {
        batch.draw(texture, rect.x, rect.y);
    }
}
