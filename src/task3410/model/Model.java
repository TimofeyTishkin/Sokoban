package task3410.model;

import task3410.controller.EventListener;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Model {
    public LevelLoader levelLoader = new LevelLoader(Paths.
            get(getClass().getResource("../res/levels.txt").toURI()));

    public int currentLevel = 1;

    private GameObjects gameObjects;

    private EventListener eventListener;

    public Model() throws URISyntaxException {
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void restartLevel(int level){
        gameObjects = levelLoader.getLevel(level);
        this.currentLevel = level;
    }

    public void restart(){
        restartLevel(currentLevel);
    }

    public void startNextLevel(){
        currentLevel++;
        restart();
    }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction){
        for(Wall wall : gameObjects.getWalls()){
            if(gameObject.isCollision(wall, direction)) return true;
        }
        return false;
    }

    public boolean checkBoxCollisionAndMoveIfAvailable(Direction direction) {
        Player player = gameObjects.getPlayer();
        Box box = null;
        for (Box box1 : gameObjects.getBoxes()) {
            if (player.isCollision(box1, direction)) {
                box = box1;
                if (checkWallCollision(box, direction)) return true;
            }
        }
        if (box != null)
            for (Box box12 : gameObjects.getBoxes()) {
                if (box12 != box && box.isCollision(box12, direction)) {
                    return true;
                }
            }

        switch (direction) {
            case RIGHT:
                if(box != null) box.move(GameObject.FIELD_CELL_SIZE, 0);
                break;
            case LEFT:
                if(box != null) box.move(-GameObject.FIELD_CELL_SIZE, 0);
                break;
            case UP:
                if(box != null) box.move(0, -GameObject.FIELD_CELL_SIZE);
                break;
            case DOWN:
                if(box != null) box.move(0, GameObject.FIELD_CELL_SIZE);
                break;
        }
        return false;
    }

    public void checkCompletion(){
        boolean allHomesHaveBoxes = true;
        for(Home home : gameObjects.getHomes()){
            boolean homeHasBox = false;
            for(Box box : gameObjects.getBoxes()){
                if (home.getX() == box.getX() && home.getY() == box.getY()) {
                    homeHasBox = true;
                    break;
                }
            }
            if (!homeHasBox) {
                allHomesHaveBoxes = false;
                break;
            }

        }
        if(allHomesHaveBoxes){
            eventListener.levelCompleted(currentLevel);
        }

    }

    public void move(Direction direction){
        if(checkWallCollision(gameObjects.getPlayer(), direction)) return;
        if(checkBoxCollisionAndMoveIfAvailable(direction)) return;
        switch (direction) {
            case DOWN: gameObjects.getPlayer().move(0, GameObject.FIELD_CELL_SIZE); break;
            case UP: gameObjects.getPlayer().move(0, -GameObject.FIELD_CELL_SIZE); break;
            case LEFT: gameObjects.getPlayer().move(-GameObject.FIELD_CELL_SIZE, 0); break;
            case RIGHT: gameObjects.getPlayer().move(GameObject.FIELD_CELL_SIZE,0); break;
        }
        checkCompletion();
    }
}
