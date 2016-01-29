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
    }
}
