package entities;

/**
 * Created by raphael on 1/29/16.
 */
public class Trash {

    public void commentedCode() {

        // First way
        /*if(bkw.getPosition().x < ball.getPosition().x){
            ballStr = new MoveStrategyLine(-p.x, p.y);
        }else if(bkw.getPosition().x >= ball.getPosition().x){
            ballStr = new MoveStrategyLine(-p.x, p.y);
        }else if(bkw.getPosition().y < ball.getPosition().y){
            ballStr = new MoveStrategyLine(p.x, -p.y);
        }else if(bkw.getPosition().y >= ball.getPosition().y ){
            ballStr = new MoveStrategyLine(p.x, -p.y);
        }else{
            ballStr = new MoveStrategyLine(-p.x, -p.y);
        }*/




        //Third way, still does not work..
        // Avant ça ne marchait pas car je ne prenais pas en compte la taille de la balle ou de la brique
        // pour le calcul des positions. Là je l'ai rajouter avec aussi un setPosition() pour que la balle
        // ne soit pas "enfoncée" dans la brique mais ça fait de la merde toujours
        /*
        if ((bkw.getPosition().getY() + bkw.getBoundingBox().getHeight()) >= ball.getPosition().getY()) {
            ball.setPosition(new Point(ball.getPosition().x,
                                      (bkw.getPosition().y + (int)bkw.getBoundingBox().getHeight())));
            ballStr = new MoveStrategyLine(p.x, -p.y);
        }else if (bkw.getPosition().getY() <= (ball.getPosition().getY() + ball.getBoundingBox().getHeight())){
            ball.setPosition(new Point(ball.getPosition().x,
                                      (bkw.getPosition().y - (int)bkw.getBoundingBox().getHeight())));
            ballStr = new MoveStrategyLine(p.x, -p.y);
        }else if(bkw.getPosition().getX() <= (ball.getPosition().getY() + ball.getBoundingBox().getWidth())) {
            ball.setPosition(new Point(ball.getPosition().y,
                                      (bkw.getPosition().x - (int)bkw.getBoundingBox().getWidth())));
            ballStr = new MoveStrategyLine(-p.x, p.y);
        }else if((bkw.getPosition().getX() + bkw.getBoundingBox().getWidth()) >= ball.getPosition().getX()) {
            ball.setPosition(new Point(ball.getPosition().y,
                                      (bkw.getPosition().x + (int)bkw.getBoundingBox().getWidth())));
            ballStr = new MoveStrategyLine(-p.x, p.y);
        }else{
            ballStr = new MoveStrategyLine(-p.x, -p.y);
        }*/

          /*
            if (brick.getPosition().getX() + brick.getBoundingBox().getWidth()
                    == ball.getPosition().getX() + ball.getBoundingBox().getWidth()) {
                ballStr = new MoveStrategyLine(direction.x, -direction.y);
            }
            else if (brick.getPosition().getY() + brick.getBoundingBox().getHeight()
                    == ball.getPosition().getY() + ball.getBoundingBox().getHeight()) {
                ballStr = new MoveStrategyLine(-direction.x, direction.y);
            }
            */
    }

    public void placement() {
                       /*
               AbstractBrick brick = null;
               switch(values[i][j]) {
                    case 1:
                        brick = new UnbreakableBrick(canvas, 0, 0, SPRITE_SIZE * 2, SPRITE_SIZE);
                        break;

                    case 2:
                        brick = new BasicBrick(canvas, 0, 0, SPRITE_SIZE * 2, SPRITE_SIZE);
                        totalBreakableWalls++;
                        break;

                    case 3:
                        brick = new BonusBrick(canvas, 0, 0, SPRITE_SIZE * 2, SPRITE_SIZE);
                        totalBreakableWalls++;
                        break;

                    case 4:
                        brick = new ExplosionBrick(canvas, 0, 0, SPRITE_SIZE * 2, SPRITE_SIZE);
                        totalBreakableWalls++;
                        break;

                    default:
                        brick = new BasicBrick(canvas, 0, 0, SPRITE_SIZE * 2, SPRITE_SIZE);
                        totalBreakableWalls++;
                        break;
                }

                brick.setPosition(new Point(
                        (2 * j + SPRITE_OFFSET_X) * SPRITE_SIZE,        // x
                        (i     + SPRITE_OFFSET_Y) * SPRITE_SIZE));      // y

                universe.addGameEntity(brick); */


                /*switch (values[i][j]) {
                    case 1:
                        universe.addGameEntity(new UnbreakableBrick(canvas,
                                (2 * j + SPRITE_OFFSET_X) * SPRITE_SIZE,  // x
                                (i     + SPRITE_OFFSET_Y) * SPRITE_SIZE,  // y
                                2 * SPRITE_SIZE, SPRITE_SIZE));
                        break;

                    case 2:
                        universe.addGameEntity(new BasicBrick(canvas,
                                (2 * j + SPRITE_OFFSET_X) * SPRITE_SIZE,  // x
                                (i     + SPRITE_OFFSET_Y) * SPRITE_SIZE,  // y
                                2 * SPRITE_SIZE, SPRITE_SIZE));
                        totalBreakableWalls++;
                        break;

                    case 3:
                        universe.addGameEntity(new BonusBrick(canvas,
                                (2 * j + SPRITE_OFFSET_X) * SPRITE_SIZE,  // x
                                (i     + SPRITE_OFFSET_Y) * SPRITE_SIZE,  // y
                                2 * SPRITE_SIZE, SPRITE_SIZE));
                        totalBreakableWalls++;
                        break;

                    case 4:
                        universe.addGameEntity(new ExplosionBrick(canvas,
                                (2 * j + SPRITE_OFFSET_X) * SPRITE_SIZE,  // x
                                (i     + SPRITE_OFFSET_Y) * SPRITE_SIZE,  // y
                                2 * SPRITE_SIZE, SPRITE_SIZE));
                        totalBreakableWalls++;
                        break;

                    default: break;
                }*/
    }
}
