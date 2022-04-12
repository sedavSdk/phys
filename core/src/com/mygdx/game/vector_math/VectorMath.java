package com.mygdx.game.vector_math;

import com.badlogic.gdx.math.Vector2;

public class VectorMath {
    public static boolean isCollide(Vector2 v1, Vector2 v2, Vector2 v3, Vector2 v4){ //v1 v2 ? v3 v4
        Vector2 dop1, dop2, dop3;
        boolean ans1, ans2;

        dop1 = v2.cpy().sub(v1);
        dop2 = v3.cpy().sub(v1);
        dop3 = v4.cpy().sub(v1);

        ans1 = (dop1.x * dop2.y - dop1.y * dop2.x) * (dop1.x * dop3.y - dop1.y * dop3.x) <= 0;

        dop1 = v1.cpy().sub(v3);
        dop2 = v2.cpy().sub(v3);
        dop3 = v4.cpy().sub(v3);

        ans2 = (dop3.x * dop1.y - dop3.y * dop1.x) * (dop3.x * dop2.y - dop3.y * dop2.x) <= 0;

        return ans1 && ans2;
    }

    public static Vector2 findPerpendicular(Vector2 v){
        if (v.y == 0) return new Vector2(0, 10);
        return new Vector2(10, -v.x * 10 / v.y);
    }

    public static float findProjection(Vector2 v, Vector2 from){
        return from.len() * (from.x * v.x + from.y * v.y) / (from.len() * v.len());
    }
}
