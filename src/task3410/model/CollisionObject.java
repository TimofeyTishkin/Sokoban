package task3410.model;


public abstract class CollisionObject extends GameObject {
    public CollisionObject(int x, int y) {
        super(x, y);
    }
    public boolean isCollision(GameObject gameObject, Direction direction){
        if (gameObject==null || direction==null) throw new IllegalArgumentException();

        int yC = this.getY();
        int xC = this.getX();

        if (direction.equals(Direction.UP)) yC-=GameObject.FIELD_CELL_SIZE;
        if (direction.equals(Direction.DOWN)) yC+=GameObject.FIELD_CELL_SIZE;
        if (direction.equals(Direction.LEFT)) xC-=GameObject.FIELD_CELL_SIZE;
        if (direction.equals(Direction.RIGHT)) xC+=GameObject.FIELD_CELL_SIZE;


        return xC == gameObject.getX() && yC == gameObject.getY();
    }
}
