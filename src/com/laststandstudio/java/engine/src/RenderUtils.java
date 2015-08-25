package com.laststandstudio.java.engine.src;

import com.laststandstudio.java.engine.src.images.PNGDecoder;
import com.laststandstudio.java.engine.src.images.Texture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

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

    public static void renderSprite() {
//        new FileInputStream("Z:\\xampp\\htdocs\\0.png");
        try {
            Texture texture = new Texture(new File("Z:\\xampp\\htdocs\\0.png").toURI().toURL());
            texture.bind();

            float u = 0f;
            float v = 0f;
            float u2 = 1f;
            float v2 = 1f;

            float x = 100, y = 100, width = 1000, height = 700;
            glColor4f(1f, 1f, 1f, 1f);
            glBegin(GL_QUADS);
            {
                glTexCoord2f(u, v);
                glVertex2f(x, y);
                glTexCoord2f(u, v2);
                glVertex2f(x, y + height);
                glTexCoord2f(u2, v2);
                glVertex2f(x + width, y + height);
                glTexCoord2f(u2, v);
                glVertex2f(x + width, y);
            }
            glEnd();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
