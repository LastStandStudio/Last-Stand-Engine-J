package com.laststandstudio.java.engine.src;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor4f;

/**
 * Created by joshu on 8/25/2015.
 */
public class RenderUtils {

    private static Color clearColor = Color.BLACK;

    /**
     * Non normailized (out of 255)
     */
    public static void setClearColor(Integer r, Integer g, Integer b, Integer a) {
        setClearColor(new Color(r, g, b, a));
    }

    public static void setClearColor(Color cC) {
        clearColor = cC;
    }

    /**
     * Non normailized (out of 255)
     */
    public static void setColor(Integer r, Integer g, Integer b, Integer a) {
        setColor(new Color(r, g, b, a));
    }

    public static void setColor(Color cC) {
        glColor4f(nC(cC.getRed()), nC(cC.getGreen()), nC(cC.getBlue()), nC(cC.getAlpha()));
    }


    public static void getClearColor() {
        glClearColor(nC(clearColor.getRed()), nC(clearColor.getGreen()), nC(clearColor.getBlue()), nC(clearColor.getAlpha()));
    }

    private static float nC(Integer cV) {
        return ((float) cV) / 255f;
    }
}
