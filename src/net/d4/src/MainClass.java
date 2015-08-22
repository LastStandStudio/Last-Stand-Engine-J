package net.d4.src;

import com.laststandstudio.java.engine.src.Engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by joshu on 8/21/2015.
 */
public class MainClass {

    MainClass() {
        try {
            Engine.getInstance().init(1200, 900);
            Engine.getInstance().addRender(() -> {
                glBegin(GL_TRIANGLES);
                {
                    glColor3f(1f, 1f, 0f);
                    glVertex2f(100, 100);
                    glColor3f(0f, 1f, 1f);
                    glVertex2f(100 + 200, 100);
                    glColor3f(1f, 0f, 1f);
                    glVertex2f(100 + 200, 100 + 200);
                }
                glEnd();

            });
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
