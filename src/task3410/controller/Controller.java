package task3410.controller;


import task3410.model.Direction;
import task3410.model.GameObjects;
import task3410.model.Model;
import task3410.view.View;

import java.net.URISyntaxException;

public class Controller implements EventListener {
    private View view;
    private Model model;

    public static void main(String[] args) {
        Controller controller = new Controller();
    }

    public Controller() {
        try {
            this.model = new Model();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        this.view = new View(this);
        this.view.init();
        this.model.restart();
        this.view.setEventListener(this);
        this.model.setEventListener(this);
    }


    public GameObjects getGameObjects(){
        return model.getGameObjects();
    }

    @Override
    public void move(Direction direction) {
        model.move(direction);
        view.update();
    }

    @Override
    public void restart() {
        model.restart();
        view.update();
    }

    @Override
    public void startNextLevel() {
        model.startNextLevel();
        view.update();
    }

    @Override
    public void levelCompleted(int level) {
        view.completed(level);
    }
}
