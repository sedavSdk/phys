package com.mygdx.game.collision;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.vector_math.VectorMath;

import java.util.Vector;

public class Struct extends Actor {
    public Vector<RectangleHitBox> rectangles = new Vector<>();
    public Vector2 massCenter;
    RotationPoint point = new RotationPoint();

    public void setSpeed(Vector2 speed) {
        this.linearSpeed = speed;
    }
    public void addSpeed(Vector2 acceleration){this.linearSpeed.add(acceleration);}

    Vector2 linearSpeed = new Vector2(0, 0);

    public void addRectangle(RectangleHitBox rectangle){
        rectangles.add(rectangle);
        updateMassCenter();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        for(RectangleHitBox i: rectangles){
            i.draw(batch, parentAlpha);
        }
    }

    @Override
    public void act(float delta) {
        for(RectangleHitBox i: rectangles){
            i.act(delta, massCenter);
        }
    }


    public Vector<Vector2> collide(RectangleHitBox rectangle){
        Vector<Vector2> dop = new Vector<>();
        for(RectangleHitBox i: rectangles){
            dop.addAll(i.collide(rectangle));
        }
        if(!dop.isEmpty()) return dop;
        return null;
    }


    public void rotate(){
        for(RectangleHitBox i: rectangles){
            i.rotate(point.v, point.speed);
        }
    }

    public void updateMassCenter(){
        massCenter = new Vector2();
        for(RectangleHitBox i: rectangles){
            massCenter.add(i.massCenter);
        }
        massCenter.scl(1f/rectangles.size());
        point.v = massCenter;
    }

    public Vector2 tryToMove(RectangleHitBox rectangle){
        Vector<Vector2> dop = new Vector<>();
        Vector<Vector2> t;
        dop.add(new Vector2(10000, 0));
        for(RectangleHitBox i: rectangles){
                t = i.tryToMove(rectangle, linearSpeed);
                if(t.elementAt(0).x != 10000)
                dop = t;
        }
        if(dop.elementAt(0).x == 10000){
            for(RectangleHitBox i: rectangles){
                i.move(linearSpeed);
            }
        } else {
            point.v = dop.elementAt(0);
            Vector2 perp = VectorMath.findPerpendicular(dop.elementAt(1));
            float proj_len = VectorMath.findProjection(perp, linearSpeed);
            perp.scl(proj_len);
            perp.setLength(Math.abs(proj_len));
            System.out.println(perp);
            linearSpeed.sub(perp);
            for(RectangleHitBox i: rectangles){
                i.move(linearSpeed);
            }
            linearToRotate(perp);
            rotate();
        }
        return null;
    }

    public void tryToRotate(){

    }

    private void linearToRotate(Vector2 surface) {
        Vector2 r = this.point.v.cpy().sub(massCenter);
        Vector2 perp = VectorMath.findPerpendicular(r);
        float dop = VectorMath.findProjection(perp, surface);
        point.speed += dop / r.len();
        //linearSpeed.setZero();
    }
}
