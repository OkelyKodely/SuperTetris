import java.util.*;

public class Piece {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    Direction direction = Direction.UP;

    private String type = "line";

    public String setType = "";

    public ArrayList<Block> blocks = new ArrayList<Block>();

    public void setType(String setType) {
        this.setType = setType;
    }

    public String getType() {
        return type;
    }

    public Piece(String type) {
        this.type = type;
        Block block;
        if(type.equals("line")) {
                block = new Block();
                block.x = 0;
                block.y = 3;
                blocks.add(block);
                block = new Block();
                block.x = 0;
                block.y = 2;
                blocks.add(block);
                block = new Block();
                block.x = 0;
                block.y = 1;
                blocks.add(block);
                block = new Block();
                block.x = 0;
                block.y = 0;
                blocks.add(block);

        } else if(type.equals("square")) {

                block = new Block();
                block.x = 0;
                block.y = 0;
                blocks.add(block);
                block = new Block();
                block.x = 1;
                block.y = 0;
                blocks.add(block);
                block = new Block();
                block.x = 0;
                block.y = 1;
                blocks.add(block);
                block = new Block();
                block.x = 1;
                block.y = 1;
                blocks.add(block);

        } else if(type.equals("LArm")) {

                block = new Block();
                block.x = 0;
                block.y = 2;
                blocks.add(block);
                block = new Block();
                block.x = -1;
                block.y = 2;
                blocks.add(block);
                block = new Block();
                block.x = 0;
                block.y = 1;
                blocks.add(block);
                block = new Block();
                block.x = 0;
                block.y = 0;
                blocks.add(block);

        } else if(type.equals("RArm")) {

                block = new Block();
                block.x = 0;
                block.y = 2;
                blocks.add(block);
                block = new Block();
                block.x = 1;
                block.y = 2;
                blocks.add(block);
                block = new Block();
                block.x = 0;
                block.y = 1;
                blocks.add(block);
                block = new Block();
                block.x = 0;
                block.y = 0;
                blocks.add(block);

        } else if(type.equals("Hat")) {

                block = new Block();
                block.x = 0;
                block.y = 1;
                blocks.add(block);
                block = new Block();
                block.x = -1;
                block.y = 1;
                blocks.add(block);
                block = new Block();
                block.x = 1;
                block.y = 1;
                blocks.add(block);
                block = new Block();
                block.x = -1;
                block.y = 0;
                blocks.add(block);

        } else if(type.equals("LShoulder")) {

                block = new Block();
                block.x = 0;
                block.y = 1;
                blocks.add(block);
                block = new Block();
                block.x = -1;
                block.y = 1;
                blocks.add(block);
                block = new Block();
                block.x = 0;
                block.y = 0;
                blocks.add(block);
                block = new Block();
                block.x = 1;
                block.y = 0;
                blocks.add(block);

        } else if(type.equals("RShoulder")) {

                block = new Block();
                block.x = 0;
                block.y = 1;
                blocks.add(block);
                block = new Block();
                block.x = 1;
                block.y = 1;
                blocks.add(block);
                block = new Block();
                block.x = 0;
                block.y = 0;
                blocks.add(block);
                block = new Block();
                block.x = -1;
                block.y = 0;
                blocks.add(block);
        }
    }

    public void setLocation(int x, int y) {

        Block block;

        if(type.equals("line")) {

                block = blocks.get(0);
                block.x = x;
                block.y = y + 3;
                block = blocks.get(1);
                block.x = x;
                block.y = y + 2;
                block = blocks.get(2);
                block.x = x;
                block.y = y + 1;
                block = blocks.get(3);
                block.x = x;
                block.y = y;

        } else if(type.equals("square")) {

                block = blocks.get(0);
                block.x = x;
                block.y = y + 1;
                block = blocks.get(1);
                block.x = x + 1;
                block.y = y + 1;
                block = blocks.get(2);
                block.x = x;
                block.y = y;
                block = blocks.get(3);
                block.x = x + 1;
                block.y = y;

        } else if(type.equals("LArm")) {

                block = blocks.get(0);
                block.x = x;
                block.y = y + 2;
                block = blocks.get(1);
                block.x = x - 1;
                block.y = y + 2;
                block = blocks.get(2);
                block.x = x;
                block.y = y + 1;
                block = blocks.get(3);
                block.x = x;
                block.y = y;

        } else if(type.equals("RArm")) {

                block = blocks.get(0);
                block.x = x;
                block.y = y + 2;
                block = blocks.get(1);
                block.x = x + 1;
                block.y = y + 2;
                block = blocks.get(2);
                block.x = x;
                block.y = y + 1;
                block = blocks.get(3);
                block.x = x;
                block.y = y;
    
        } else if(type.equals("Hat")) {

                block = blocks.get(0);
                block.x = x;
                block.y = y + 1;
                block = blocks.get(1);
                block.x = x - 1;
                block.y = y + 1;
                block = blocks.get(2);
                block.x = x + 1;
                block.y = y + 1;
                block = blocks.get(3);
                block.x = x;
                block.y = y;

        } else if(type.equals("LShoulder")) {

                block = blocks.get(0);
                block.x = x;
                block.y = y + 1;
                block = blocks.get(1);
                block.x = x - 1;
                block.y = y + 1;
                block = blocks.get(2);
                block.x = x;
                block.y = y;
                block = blocks.get(3);
                block.x = x + 1;
                block.y = y;

        } else if(type.equals("RShoulder")) {

                block = blocks.get(0);
                block.x = x;
                block.y = y + 1;
                block = blocks.get(1);
                block.x = x + 1;
                block.y = y + 1;
                block = blocks.get(2);
                block.x = x;
                block.y = y;
                block = blocks.get(3);
                block.x = x - 1;
                block.y = y;
        }
    }

    public void flip() {

        Block block;

        if(direction == Direction.UP) {


            if(type.equals("line")) {


                direction = Direction.RIGHT;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;

                
                block = blocks.get(1);


                block.x += 1;

                block.y += 1;


                block = blocks.get(2);


                block.x += 2;

                block.y += 2;

                block = blocks.get(3);


                block.x += 3;

                block.y += 3;

                
            } else if(type.equals("square")) {

                
                direction = Direction.RIGHT;

            
            } else if(type.equals("LArm")) {


                direction = Direction.RIGHT;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x += 1;

                block.y -= 1;


                block = blocks.get(2);


                block.x += 1;

                block.y += 1;

                block = blocks.get(3);


                block.x += 2;

                block.y += 2;

            } else if(type.equals("RArm")) {


                direction = Direction.RIGHT;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x -= 1;

                block.y += 1;


                block = blocks.get(2);


                block.x += 1;

                block.y += 1;

                block = blocks.get(3);


                block.x += 2;

                block.y += 2;

            } else if(type.equals("LShoulder")) {


                direction = Direction.RIGHT;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x += 1;

                block.y -= 1;


                block = blocks.get(2);


                block.x += 1;

                block.y += 1;

                block = blocks.get(3);


                block.x -= 0;

                block.y += 2;

            } else if(type.equals("RShoulder")) {


                direction = Direction.RIGHT;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x -= 1;

                block.y += 1;


                block = blocks.get(2);


                block.x += 1;

                block.y += 1;

                block = blocks.get(3);


                block.x += 2;

                block.y += 0;

            } else if (type.equals("Hat")) {


                direction = Direction.RIGHT;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x += 1;

                block.y -= 1;


                block = blocks.get(2);


                block.x -= 1;

                block.y += 1;

                block = blocks.get(3);


                block.x += 1;

                block.y += 1;

            }

        }

        else if(direction == Direction.RIGHT) {


            if(type.equals("line")) {


                direction = Direction.UP;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x -= 1;

                block.y -= 1;


                block = blocks.get(2);


                block.x -= 2;

                block.y -= 2;

                block = blocks.get(3);


                block.x -= 3;

                block.y -= 3;
                

            } else if(type.equals("square")) {


                direction = Direction.DOWN;

            
            } else if(type.equals("LArm")) {


                direction = Direction.DOWN;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x += 1;

                block.y += 1;


                block = blocks.get(2);


                block.x -= 1;

                block.y += 1;

                block = blocks.get(3);


                block.x -= 2;

                block.y += 2;

            } else if(type.equals("RArm")) {


                direction = Direction.DOWN;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x -= 1;

                block.y -= 1;


                block = blocks.get(2);


                block.x -= 1;

                block.y += 1;

                block = blocks.get(3);


                block.x -= 2;

                block.y += 2;

            
            } else if(type.equals("LShoulder")) {


                direction = Direction.DOWN;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x += 1;

                block.y += 1;


                block = blocks.get(2);


                block.x -= 1;

                block.y += 1;

                block = blocks.get(3);


                block.x -= 2;

                block.y -= 0;

            } else if(type.equals("RShoulder")) {


                direction = Direction.DOWN;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x -= 1;

                block.y -= 1;


                block = blocks.get(2);


                block.x -= 1;

                block.y += 1;

                block = blocks.get(3);


                block.x += 0;

                block.y += 2;

            } else if (type.equals("Hat")) {


                direction = Direction.DOWN;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x += 1;

                block.y += 1;


                block = blocks.get(2);


                block.x -= 1;

                block.y -= 1;

                block = blocks.get(3);


                block.x -= 1;

                block.y += 1;

            }

        }

        else if(direction == Direction.DOWN) {


            if(type.equals("square")) {


                direction = Direction.LEFT;
            


            } else if(type.equals("LArm")) {


                direction = Direction.LEFT;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x -= 1;

                block.y += 1;


                block = blocks.get(2);


                block.x -= 1;

                block.y -= 1;

                block = blocks.get(3);


                block.x -= 2;

                block.y -= 2;

            } else if(type.equals("RArm")) {


                direction = Direction.LEFT;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x += 1;

                block.y -= 1;


                block = blocks.get(2);


                block.x -= 1;

                block.y -= 1;

                block = blocks.get(3);


                block.x -= 2;

                block.y -= 2;

            } else if(type.equals("LShoulder")) {


                direction = Direction.LEFT;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x -= 1;

                block.y += 1;


                block = blocks.get(2);


                block.x -= 1;

                block.y -= 1;

                block = blocks.get(3);


                block.x += 0;

                block.y -= 2;

            } else if(type.equals("RShoulder")) {


                direction = Direction.LEFT;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x += 1;

                block.y -= 1;


                block = blocks.get(2);


                block.x -= 1;

                block.y -= 1;

                block = blocks.get(3);


                block.x -= 2;

                block.y += 0;

            } else if (type.equals("Hat")) {


                direction = Direction.LEFT;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x -= 1;

                block.y += 1;


                block = blocks.get(2);


                block.x += 1;

                block.y -= 1;

                block = blocks.get(3);


                block.x -= 1;

                block.y -= 1;

            }

        }

        else if(direction == Direction.LEFT) {

            if(type.equals("square")) {


                direction = Direction.UP;


            } else if(type.equals("LArm")) {


                direction = Direction.UP;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x -= 1;

                block.y -= 1;


                block = blocks.get(2);


                block.x += 1;

                block.y -= 1;

                block = blocks.get(3);


                block.x += 2;

                block.y -= 2;


            } else if(type.equals("RArm")) {


                direction = Direction.UP;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x += 1;

                block.y += 1;


                block = blocks.get(2);


                block.x += 1;

                block.y -= 1;

                block = blocks.get(3);


                block.x += 2;

                block.y -= 2;

            } else if(type.equals("LShoulder")) {


                direction = Direction.UP;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x -= 1;

                block.y -= 1;


                block = blocks.get(2);


                block.x += 1;

                block.y -= 1;

                block = blocks.get(3);


                block.x += 2;

                block.y += 0;

            } else if(type.equals("RShoulder")) {


                direction = Direction.UP;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x += 1;

                block.y += 1;


                block = blocks.get(2);


                block.x += 1;

                block.y -= 1;

                block = blocks.get(3);


                block.x += 0;

                block.y -= 2;

            } else if (type.equals("Hat")) {


                direction = Direction.UP;



                block = blocks.get(3);


                block.x += 0;

                block.y -= 0;


                block = blocks.get(1);


                block.x -= 1;

                block.y -= 1;


                block = blocks.get(2);


                block.x += 1;

                block.y += 1;

                block = blocks.get(3);


                block.x += 1;

                block.y -= 1;

            }

        }


    }

    public void moveDown() {

        Block block = blocks.get(0);

        block.y++;

        block = blocks.get(1);

        block.y++;

        block = blocks.get(2);

        block.y++;

        block = blocks.get(3);
        
        block.y++;

    }


    public void moveLeft() {

        Block block = blocks.get(0);

        block.x--;

        block = blocks.get(1);

        block.x--;

        block = blocks.get(2);

        block.x--;

        block = blocks.get(3);

        block.x--;

    }


    public void moveRight() {

        Block block = blocks.get(0);

        block.x++;

        block = blocks.get(1);

        block.x++;

        block = blocks.get(2);

        block.x++;

        block = blocks.get(3);

        block.x++;

    }


}