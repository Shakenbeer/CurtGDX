package com.shakenbeer.curtandray;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameObject {
    
    private Sprite sprite;
    private int pivotX;
    private int pivotY;
    int radius;

    float velX;
    float velY;
    float velNormSqr;
    
    private int opacity = 255;
    
    public GameObject(int pivotX, int pivotY, int radius, Texture texture) {
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        this.radius = radius;
        sprite = new Sprite(texture);
        sprite.setOrigin(pivotX, pivotY);
    }    
    
    public int getOpacity() {
        return opacity;
    }
    
    public float posX() {
        return sprite.getX() + pivotX;
    }
    
    public float posY() {
        return sprite.getY() + pivotY;
    }
    
    public void setPos(float x, float y) {
        sprite.setPosition(x - pivotX, y - pivotY);
    }
    
    public void setAngle(float angle) {
        sprite.setRotation(angle);
    }
    
    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    public void move(float delta) {
        sprite.translate(velX * delta, velY * delta);        
    }
    
    public void setOpacity(int opacity) {
        this.opacity = opacity;
        sprite.setAlpha((float)opacity/255);        
    }

    public int getHeight() {
        return (int) sprite.getHeight();
    }
}
