package net.d4.src;

import com.laststandstudio.java.engine.src.Engine;
import com.laststandstudio.java.engine.src.RenderUtils;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by joshu on 8/21/2015.
 */
public class MainClass {

    MainClass() {
        try {
            Engine.getInstance().init(1200, 900);
            Engine.getInstance().addRender(1, () -> {
                glBegin(GL_TRIANGLES);
                {
                    RenderUtils.setColor(Color.RED);
                    glVertex2f(100, 100);
                    RenderUtils.setColor(Color.GREEN);
                    glVertex2f(100 + 200, 100);
                    RenderUtils.setColor(Color.BLUE);
                    glVertex2f(100 + 200, 100 + 200);
                }
                glEnd();

                RenderUtils.renderSprite();
            });
            RenderUtils.setClearColor(Color.BLACK);
            Engine.getInstance().start();

            glfwDestroyWindow(Engine.getInstance().getWindow());
            Engine.getInstance().getKeyCallback().release();
        } finally {
            // Terminate GLFW and release the GLFWerrorfun
            glfwTerminate();
            Engine.getInstance().getErrorCallback().release();
        }
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        System.out.println(System.getProperty("java.library.path"));
        new MainClass();
    }

}
