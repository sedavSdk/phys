package com.mygdx.game.collision;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.vector_math.VectorMath;

import java.util.Vector;

public class RectangleHitBox extends Actor {
    public Vector2 v1;
    public Vector2 v2, v3, massCenter;
    public Vector2 prevV1, prevV2, prevV3;
    public Vector2 nextV1, nextV2, nextV3;
    public Vector<Vector2> rotationPoints = new Vector<>();

    public ShapeRenderer shapeRenderer;

    public void addRotationPoint(Vector2 point){
        rotationPoints.add(point);
    }

    public RectangleHitBox(Vector2 v1, Vector2 v2, Vector2 v3, Camera camera) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;

        prevV1 = new Vector2(v1);
        prevV2 = new Vector2(v2);
        prevV3 = new Vector2(v3);

        nextV1 = new Vector2(v1);
        nextV2 = new Vector2(v2);
        nextV3 = new Vector2(v3);

        massCenter = v1.cpy().add(v2).add(v3).scl(1/3f);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 0, 1);
        shapeRenderer.line(v1,v2);
        shapeRenderer.line(v2,v3);
        shapeRenderer.line(v3,v1);
        shapeRenderer.circle(massCenter.x, massCenter.y, 1);
        shapeRenderer.end();
    }

    public void act(float delta, Vector2 mass) {
        massCenter = v1.cpy().add(v2).add(v3).scl(0.33f);
    }

    public Vector<Vector2> tryToMove(RectangleHitBox rectangle, Vector2 linearSpeed){
        nextV1.add(linearSpeed);
        nextV2.add(linearSpeed);
        nextV3.add(linearSpeed);

        return collide(rectangle);
    }

    public void move(Vector2 linearSpeed){
        v1.add(linearSpeed);
        v2.add(linearSpeed);
        v3.add(linearSpeed);
    }

    public Vector<Vector2> collide(RectangleHitBox rectangle){ // v1 ? rec.v1, rec.v2
        Vector<Vector2> ans = new Vector<>();
        ans.addElement(new Vector2(10000, 0));
        ans.addElement(new Vector2(0, 0));
        if(VectorMath.isCollide(this.v1.cpy(), this.nextV1.cpy(), rectangle.v1, rectangle.v2)) {
            ans.elementAt(0).set(v1);
            ans.elementAt(1).set(rectangle.v1.cpy().sub(rectangle.v2));
        }
        if(VectorMath.isCollide(this.v1.cpy(), this.nextV1.cpy(), rectangle.v2, rectangle.v3)) {
            ans.elementAt(0).set(v1);
            ans.elementAt(1).set(rectangle.v2.cpy().sub(rectangle.v3));
        }
        if(VectorMath.isCollide(this.v1.cpy(), this.nextV1.cpy(), rectangle.v3, rectangle.v1)) {
            ans.elementAt(0).set(v1);
            ans.elementAt(1).set(rectangle.v3.cpy().sub(rectangle.v1));
        }

        if(VectorMath.isCollide(this.v2.cpy(), this.nextV2.cpy(), rectangle.v1, rectangle.v2)) {
            ans.elementAt(0).set(v2);
            ans.elementAt(1).set(rectangle.v1.cpy().sub(rectangle.v2));
        }
        if(VectorMath.isCollide(this.v2.cpy(), this.nextV2.cpy(), rectangle.v2, rectangle.v3)) {
            ans.elementAt(0).set(v2);
            ans.elementAt(1).set(rectangle.v2.cpy().sub(rectangle.v3));
        }
        if(VectorMath.isCollide(this.v2.cpy(), this.nextV2.cpy(), rectangle.v3, rectangle.v1)) {
            ans.elementAt(0).set(v2);
            ans.elementAt(1).set(rectangle.v3.cpy().sub(rectangle.v1));
        }

        if(VectorMath.isCollide(this.v3.cpy(), this.nextV3.cpy(), rectangle.v1, rectangle.v2)) {
            ans.elementAt(0).set(v3);
            ans.elementAt(1).set(rectangle.v1.cpy().sub(rectangle.v2));
        }
        if(VectorMath.isCollide(this.v3.cpy(), this.nextV3.cpy(), rectangle.v2, rectangle.v3)) {
            ans.elementAt(0).set(v3);
            ans.elementAt(1).set(rectangle.v2.cpy().sub(rectangle.v3));
        }
        if(VectorMath.isCollide(this.v3.cpy(), this.nextV3.cpy(), rectangle.v3, rectangle.v1)) {
            ans.elementAt(0).set(v3);
            ans.elementAt(1).set(rectangle.v1.cpy().sub(rectangle.v3));
        }

        return ans;
    }

    public void rotate(Vector2 point, float angle){
        v1 = rotatePoint(point, angle, v1);
        v2 = rotatePoint(point, angle, v2);
        v3 = rotatePoint(point, angle, v3);
    }

    private Vector2 rotatePoint(Vector2 point, float angle, Vector2 v){
        Vector2 dop = new Vector2();
        dop.x = (float) ((v.x - point.x) * Math.cos(angle) - (v.y - point.y) * Math.sin(angle) + point.x);
        dop.y = (float) ((v.x - point.x) * Math.sin(angle) + (v.y - point.y) * Math.cos(angle) + point.y);
        return dop;
    }
}
