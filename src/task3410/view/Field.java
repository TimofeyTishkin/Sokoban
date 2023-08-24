package task3410.view;


import task3410.controller.EventListener;
import task3410.model.Direction;


import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Field extends JPanel {
    private EventListener eventListener;
    private View view;
    public Field(View view) {
        this.view = view;
        KeyHandler handler = new KeyHandler();
        this.addKeyListener(handler);
        this.setFocusable(true);
    }

    public void paint(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(0, 0, 1000, 1000);
        view.getGameObjects().getAll().forEach(o -> o.draw(g));
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public class KeyHandler extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    eventListener.move(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    eventListener.move(Direction.RIGHT);
                    break;
                case KeyEvent.VK_UP:
                    eventListener.move(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    eventListener.move(Direction.DOWN);
                    break;
                case KeyEvent.VK_R:
                    eventListener.restart();
                    break;
            }
        }
    }
}
