package main;

import engine.graphics.*;
import engine.io.Input;
import engine.io.Window;
import engine.objects.Camera;
import engine.objects.GameObject;
import engine.utils.maths.Vector2f;
import engine.utils.maths.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Main implements Runnable {
    public final int WIDTH = 1280, HEIGHT = 720;
    public Thread game;
    public Window window;
    public Renderer renderer;
    public Shader shader;
    public Mesh mesh = new Mesh(new Vertex[]{
            //Back face
            new Vertex(new Vector3f(-0.5f, 0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f(0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f(0.5f, 0.5f, -0.5f), new Vector2f(1.0f, 0.0f)),

            //Front face
            new Vertex(new Vector3f(-0.5f, 0.5f, 0.5f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, 0.5f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f(0.5f, -0.5f, 0.5f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f(0.5f, 0.5f, 0.5f), new Vector2f(1.0f, 0.0f)),

            //Right face
            new Vertex(new Vector3f(0.5f, 0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f(0.5f, -0.5f, 0.5f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f(0.5f, 0.5f, 0.5f), new Vector2f(1.0f, 0.0f)),

            //Left face
            new Vertex(new Vector3f(-0.5f, 0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, 0.5f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f(-0.5f, 0.5f, 0.5f), new Vector2f(1.0f, 0.0f)),

            //Top face
            new Vertex(new Vector3f(-0.5f, 0.5f, 0.5f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, 0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f(0.5f, 0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f(0.5f, 0.5f, 0.5f), new Vector2f(1.0f, 0.0f)),

            //Bottom face
            new Vertex(new Vector3f(-0.5f, -0.5f, 0.5f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f(0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f(0.5f, -0.5f, 0.5f), new Vector2f(1.0f, 0.0f)),
    }, new int[]{
            //Back face
            0, 1, 3,
            3, 1, 2,

            //Front face
            4, 5, 7,
            7, 5, 6,

            //Right face
            8, 9, 11,
            11, 9, 10,

            //Left face
            12, 13, 15,
            15, 13, 14,

            //Top face
            16, 17, 19,
            19, 17, 18,

            //Bottom face
            20, 21, 23,
            23, 21, 22
    }, new Material("C:\\Users\\jllar\\IdeaProjects\\3D Engine\\resources\\textures\\beautiful.png"));

    public GameObject object = new GameObject(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), mesh);
    public GameObject[] objects = new GameObject[500];
    public Camera camera = new Camera(new Vector3f(0, 0, 1), new Vector3f(0, 0, 0));

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        game = new Thread(this, "game");
        game.run();
    }

    public void init() {
        window = new Window(WIDTH, HEIGHT, "Game Engine");
        shader = new Shader("C:\\Users\\jllar\\IdeaProjects\\3D Engine\\resources\\shaders\\mainVertex.glsl", "C:\\Users\\jllar\\IdeaProjects\\3D Engine\\resources\\shaders\\mainFragment.glsl");
        renderer = new Renderer(window, shader);
        window.setBackgroundColor(0.0f, 0.0f, 0.0f);
        window.create();
        mesh.create();
        shader.create();

        objects[0] = object;

        int x = 0, z = 0;

        for (int dx = 0; dx < 50; dx++) {
            for (int dz = 0; dz < 50; dz++) {
                z = dz;
            }
            x = dx;
        }

        for (int i = 0; i < objects.length; i++) {
            objects[i] = new GameObject(new Vector3f(x, 1, z), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), mesh);
        }
    }

    public void run() {
        init();
        while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            update();
            render();
            if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
            if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) window.mouseState(true);
        }
        close();
    }

    private void update() {
        window.update();
        camera.update();
    }

    private void render() {
        for (int i = 0; i < objects.length; i++) {
            renderer.renderMesh(objects[i], camera);
        }
        renderer.renderMesh(object, camera);
        window.swapBuffers();
    }

    private void close() {
        window.destroy();
        mesh.destroy();
        shader.destroy();
    }

}
