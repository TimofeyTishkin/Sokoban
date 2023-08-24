package task3410.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class LevelLoader {
    private Path levels;

    public LevelLoader(Path levels){
        this.levels = levels;
    }

    public GameObjects getLevel(int level) {
        while(level > 60) {
            level -= 60;
        }

        Set<Wall> walls = new HashSet<>();
        Set<Box> boxes = new HashSet<>();
        Set<Home> homes = new HashSet<>();
        Player player = null;
        int y;
        int x;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(levels.toFile()));
            String str;
            int x0 = GameObject.FIELD_CELL_SIZE/2;
            int y0 = GameObject.FIELD_CELL_SIZE/2;
            while ((str = reader.readLine()) != null) {
                // ищем уровень Maze:
                if (str.equals("Maze: " + level)) {
                    String tmp;
                    while ((tmp = reader.readLine()) != null) {
                        if (tmp.startsWith("Length:")){
                            reader.readLine();
                            break;}
                    }

                    y = y0;// Y Самого верхнего левого объекта
                    while ((str = reader.readLine()) != null) {
                        if (str.startsWith("************")) break;
                        x = x0; // Х Самого верхнего левого объекта
                        for (int i = 0; i < str.length(); i++) { // по символам строки

                            char c = str.charAt(i);
                            switch (c) {
                                case 'X':
                                    walls.add(new Wall(x, y));
                                    break;
                                case '*':
                                    boxes.add(new Box(x, y));
                                    break;// ящик
                                case '&': // ящик в домике
                                    boxes.add(new Box(x, y));
                                    homes.add(new Home(x, y));
                                    break;
                                case '.':
                                    homes.add(new Home(x, y));
                                    break;
                                case '@':
                                    player = new Player(x, y);
                            }
                            x += GameObject.FIELD_CELL_SIZE; //смещение X каждого следующего на FIELD_CELL_SIZE
                        }
                        y += GameObject.FIELD_CELL_SIZE; //смещение Y каждого следующего на FIELD_CELL_SIZE
                    }
                }
            }

            return new GameObjects(walls, boxes, homes, player);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
