package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.collision.RectangleHitBox;
import com.mygdx.game.collision.Struct;
import com.mygdx.game.vector_math.VectorMath;

import java.util.Vector;

public class GameScreen implements Screen {
    Stage stage;
    OrthographicCamera camera;
    RectangleHitBox r1, r2, r3;

    Struct struct;
    public GameScreen(Start game) {
        camera = new OrthographicCamera();
        stage = new Stage(new StretchViewport(300, 700, camera));
        camera.position.set(new Vector3(0, 0,3));

        r1 = new RectangleHitBox(new Vector2(0, 150), new Vector2(300, 100), new Vector2(100, 0), camera);

        struct = new Struct();
        struct.addRectangle(new RectangleHitBox(new Vector2(0, 200), new Vector2(0, 250), new Vector2(50, 200), camera));
        struct.addRectangle(new RectangleHitBox(new Vector2(50, 250), new Vector2(0, 250), new Vector2(50, 200), camera));

        stage.addActor(r1);
        stage.addActor(struct);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0/255f, 0/255f, 0/255f, 1);
        struct.addSpeed(new Vector2(0, -0.01f));
        struct.tryToMove(r1);
        stage.act();
        stage.draw();
    }

    private void collide(Array<Actor> actors) {
        Vector<Vector2> asd = struct.collide(r1);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
