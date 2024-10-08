package org.example.scenes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.components.Component;
import org.example.components.ComponentDeserializer;
import org.example.jade.*;
import org.example.physics2d.Physics2D;
import org.example.renderer.Renderer;
import org.example.utils.CustomFileChooser;
import org.example.utils.EncryptionUtils;
import org.joml.Vector2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.crypto.spec.SecretKeySpec;

import static org.example.utils.CustomFileChooser.windowsJFileChooser;

public class Scene {
    private transient Logger logger = LoggerFactory.getLogger(Scene.class);

    private Renderer renderer;
    private Camera camera;
    private boolean isRunning;
    private List<GameObject> gameObjects;
    private List<GameObject> pendingObjects;
    private Physics2D physics2D;

    private SceneInitializer sceneInitializer;
    private String currentLevel;

    public Scene(SceneInitializer sceneInitializer) {
        this.sceneInitializer = sceneInitializer;
        this.physics2D = new Physics2D();
        this.renderer = new Renderer();
        this.gameObjects = new ArrayList<>();
        this.pendingObjects = new ArrayList<>();
        this.isRunning = false;
    }

    public Physics2D getPhysics() {
        return this.physics2D;
    }

    public void init() {
        this.camera = new Camera(new Vector2f(0, 0));
        this.sceneInitializer.loadResources(this);
        this.sceneInitializer.init(this);
    }

    public void start() {
        for (int i=0; i < gameObjects.size(); i++) {
            GameObject go = gameObjects.get(i);
            go.start();
            this.renderer.add(go);
            this.physics2D.add(go);
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject go) {
        if (!isRunning) {
            gameObjects.add(go);
        } else {
            pendingObjects.add(go);
        }
    }

    public void destroy() {
        for (GameObject go : gameObjects) {
            go.destroy();
        }
    }

    public <T extends Component> GameObject getGameObjectWith(Class<T> clazz) {
        for (GameObject go : gameObjects) {
            if (go.getComponent(clazz) != null) {
                return go;
            }
        }

        return null;
    }

    public List<GameObject> getGameObjects() {
        return this.gameObjects;
    }

    public GameObject getGameObject(int gameObjectId) {
        Optional<GameObject> result = this.gameObjects.stream()
                .filter(gameObject -> gameObject.getUid() == gameObjectId)
                .findFirst();
        return result.orElse(null);
    }

    public GameObject getGameObject(String gameObjectName) {
        Optional<GameObject> result = this.gameObjects.stream()
                .filter(gameObject -> gameObject.name.equals(gameObjectName))
                .findFirst();
        return result.orElse(null);
    }

    public void editorUpdate(float dt) {
        this.camera.adjustProjection();

        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject go = gameObjects.get(i);
            go.editorUpdate(dt);

            if (go.isDead()) {
                gameObjects.remove(i);
                this.renderer.destroyGameObject(go);
                this.physics2D.destroyGameObject(go);
                i--;
            }
        }

        for (GameObject go : pendingObjects) {
            gameObjects.add(go);
            go.start();
            this.renderer.add(go);
            this.physics2D.add(go);
        }
        pendingObjects.clear();
    }

    public void update(float dt) {
        this.camera.adjustProjection();
        this.physics2D.update(dt);

        for (int i=0; i < gameObjects.size(); i++) {
            GameObject go = gameObjects.get(i);
            go.update(dt);

            if (go.isDead()) {
                gameObjects.remove(i);
                this.renderer.destroyGameObject(go);
                this.physics2D.destroyGameObject(go);
                i--;
            }
        }

        for (GameObject go : pendingObjects) {
            gameObjects.add(go);
            go.start();
            this.renderer.add(go);
            this.physics2D.add(go);
        }
        pendingObjects.clear();
    }

    public void render() {
        this.renderer.render();
    }

    public Camera camera() {
        return this.camera;
    }

    public void imgui() {
        this.sceneInitializer.imgui();
    }

    public GameObject createGameObject(String name) {
        GameObject go = new GameObject(name);
        go.addComponent(new Transform());
        go.transform = go.getComponent(Transform.class);
        return go;
    }

    public void encryptCurrentLevel() {
        File selectedFile = new File(currentLevel);
        EncryptionUtils.encryptFile(selectedFile);
    }

    public void encryptLevel() {
        CustomFileChooser encryptChooser = windowsJFileChooser("Default");
        encryptChooser.setCurrentDirectory(new File("levels"));
        encryptChooser.setApproveButtonText("Encrypt Level");
        FileFilter filter = new FileNameExtensionFilter("TXT file", "txt");
        encryptChooser.setFileFilter(filter);
        encryptChooser.addChoosableFileFilter(filter);
        encryptChooser.setDialogTitle("Encrypt File");
        encryptChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        encryptChooser.setAcceptAllFileFilterUsed(false);

        if (encryptChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = encryptChooser.getSelectedFile();
            EncryptionUtils.encryptFile(selectedFile);
        }
    }

    public void decryptLevel() {
        CustomFileChooser decryptChooser = windowsJFileChooser("DecryptLevel");
        decryptChooser.setCurrentDirectory(new File("levels"));
        decryptChooser.setApproveButtonText("Decrypt Level");
        FileFilter filter = new FileNameExtensionFilter("Encrypted file", "encrypted");
        decryptChooser.setFileFilter(filter);
        decryptChooser.addChoosableFileFilter(filter);
        decryptChooser.setDialogTitle("Decrypt File");
        decryptChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        decryptChooser.setAcceptAllFileFilterUsed(false);

        if (decryptChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = decryptChooser.getSelectedFile();
            String encryptionKey = decryptChooser.getEncryptionKeyField();
            EncryptionUtils.decryptFile(decryptChooser, selectedFile, encryptionKey);
        }
    }

    public void createNewLevel() {
        Properties properties = new Properties();
        String loadPath = null;
        String configFileName = "config.cfg";
        File configFile = new File(configFileName);

        JFileChooser createLevelChooser = windowsJFileChooser("Default");
        createLevelChooser.setCurrentDirectory(new File("levels"));
        createLevelChooser.setApproveButtonText("Create Level");
        FileFilter filter = new FileNameExtensionFilter("TXT file", "txt");
        createLevelChooser.setFileFilter(filter);
        createLevelChooser.addChoosableFileFilter(filter);
        createLevelChooser.setDialogTitle("Create Level");
        createLevelChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        createLevelChooser.setAcceptAllFileFilterUsed(false);

        if (createLevelChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = createLevelChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();

                if (!filePath.toLowerCase().endsWith(".txt")) {
                    filePath += ".txt";
                    selectedFile = new File(filePath);
                }

                FileWriter writer = new FileWriter(selectedFile);
                loadPath = selectedFile.getAbsolutePath();
                writer.write("");
                writer.close();
            } catch (IOException e) {
                logger.error("Error: Creating new level file.", e);
            }
        } else {
            try (FileInputStream input = new FileInputStream(configFile)) {
                properties.load(input);
                loadPath = properties.getProperty("PreviousLevel");
            } catch (IOException e) {
                logger.error("Error: Reading property from configuration file.", e);
            }
        }

        properties.setProperty("PreviousLevel", loadPath);
        try (FileOutputStream output = new FileOutputStream(configFile)) {
            properties.store(output, null);
        } catch (IOException e) {
            logger.error("Error: Writing property to configuration file.", e);
        }
    }

    public void saveLevel() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .enableComplexMapKeySerialization()
                .create();

        try {
            FileWriter writer = new FileWriter(currentLevel);
            List<GameObject> objsToSerialize = new ArrayList<>();
            for (GameObject obj : this.gameObjects) {
                if (obj.doSerialization()) {
                    objsToSerialize.add(obj);
                }
            }
            writer.write(gson.toJson(objsToSerialize));
            writer.close();
        } catch(IOException e) {
            logger.error("Error: Writing information to file.", e);
        }
    }

    public void saveAsLevel() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .enableComplexMapKeySerialization()
                .create();

        CustomFileChooser saveChooser = windowsJFileChooser("SaveLevel");
        saveChooser.setCurrentDirectory(new File("levels"));
        saveChooser.setApproveButtonText("Save Level");
        FileFilter filter = new FileNameExtensionFilter("TXT file", "txt");
        saveChooser.setFileFilter(filter);
        saveChooser.addChoosableFileFilter(filter);
        saveChooser.setDialogTitle("Save File");
        saveChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        saveChooser.setAcceptAllFileFilterUsed(false);

        if (saveChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = saveChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();

                List<GameObject> objsToSerialize = new ArrayList<>();
                for (GameObject obj : this.gameObjects) {
                    if (obj.doSerialization()) {
                        objsToSerialize.add(obj);
                    }
                }

                if (!filePath.toLowerCase().endsWith(".txt")) {
                    filePath += ".txt";
                    selectedFile = new File(filePath);
                }

                FileWriter writer = new FileWriter(selectedFile);
                writer.write(gson.toJson(objsToSerialize));
                writer.close();
            } catch (IOException e) {
                logger.error("Error: Writing information to file.", e);
            }
        }
    }

    public void loadLevel() {
        Properties properties = new Properties();
        String loadPath = null;
        String configFileName = "config.cfg";
        File configFile = new File(configFileName);

        if (!configFile.exists() || configFile.length() == 0) {
            try {
                if (configFile.createNewFile()) {
                    System.out.println("File " + configFileName + " successfully created.");
                } else {
                    System.out.println("Failed to create file " + configFileName + ".");
                }
            } catch (IOException e) {
                logger.error("Error: Creating file " + configFileName + ".", e);
            }
            createNewLevel();
        } else {
            try (FileInputStream input = new FileInputStream(configFileName)) {
                properties.load(input);
                loadPath = properties.getProperty("PreviousLevel");
                currentLevel = loadPath;
            } catch (IOException e) {
                logger.error("Error: Reading property from configuration file.", e);
            }
            loadDataFromFile(loadPath);
        }
    }

    public void loadLevelFrom() {
        Properties properties = new Properties();
        String loadPath = null;
        String configFileName = "config.cfg";
        File configFile = new File(configFileName);

        boolean fileChooserClosed = false;

        CustomFileChooser loadChooser = windowsJFileChooser("Default");
        loadChooser.setCurrentDirectory(new File("levels"));
        loadChooser.setApproveButtonText("Load Level");
        FileFilter filter = new FileNameExtensionFilter("TXT file", "txt");
        loadChooser.setFileFilter(filter);
        loadChooser.addChoosableFileFilter(filter);
        loadChooser.setDialogTitle("Load File");
        loadChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        loadChooser.setAcceptAllFileFilterUsed(false);

        if (loadChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = loadChooser.getSelectedFile();
            loadPath = file.getAbsolutePath();
        } else {
            try (FileInputStream input = new FileInputStream(configFile)) {
                properties.load(input);
                loadPath = properties.getProperty("PreviousLevel");
            } catch (IOException e) {
                logger.error("Error: Reading property from configuration file.", e);
            }
            fileChooserClosed = true;
        }

        if(!fileChooserClosed) {
            properties.setProperty("PreviousLevel", loadPath);
            try (FileOutputStream output = new FileOutputStream(configFile)) {
                properties.store(output, null);
            } catch (IOException e) {
                logger.error("Error: Writing property to configuration file.", e);
            }
        }

        loadDataFromFile(loadPath);
    }

    public void loadDataFromFile(String loadPath) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .enableComplexMapKeySerialization()
                .create();

        String inFile = "";
        try {
            inFile = new String(Files.readAllBytes(Paths.get(loadPath)));
        } catch (IOException e) {
            logger.error("Error: Reading file contents.", e);
        }

        if (!inFile.isEmpty()) {
            int maxGoId = -1;
            int maxCompId = -1;
            GameObject[] objs = gson.fromJson(inFile, GameObject[].class);
            for (int i=0; i < objs.length; i++) {
                addGameObjectToScene(objs[i]);

                for (Component c : objs[i].getAllComponents()) {
                    if (c.getUid() > maxCompId) {
                        maxCompId = c.getUid();
                    }
                }
                if (objs[i].getUid() > maxGoId) {
                    maxGoId = objs[i].getUid();
                }
            }

            maxGoId++;
            maxCompId++;
            GameObject.init(maxGoId);
            Component.init(maxCompId);
        }

        currentLevel = loadPath;
    }
}

