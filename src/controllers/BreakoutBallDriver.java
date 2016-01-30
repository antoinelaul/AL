package controllers;

import gameframework.base.Movable;
import gameframework.base.MoveStrategy;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;
import gameframework.game.GameMovableDriverDefaultImpl;


public class BreakoutBallDriver extends GameMovableDriverDefaultImpl {
    private MoveStrategy strategies[] = null;

    public void setStrategy(MoveStrategy strategy) {
        super.setStrategy(strategy);
        strategies = null;
    }

    public void setStrategies(MoveStrategy strategies[]) {
        this.strategies = strategies;
    }

    public SpeedVector getSpeedVector(Movable m) {
        SpeedVector possibleSpeedVector;

        // No many strategies to test.
        if (strategies == null)
            return super.getSpeedVector(m);

        // Try every stored strategy, and apply the first one possible.
        for (MoveStrategy str: strategies) {
            possibleSpeedVector = str.getSpeedVector();
            if (moveBlockerChecker.moveValidation(m, possibleSpeedVector)) {
                setStrategy(str);
                return possibleSpeedVector;
            }
        }

        return SpeedVectorDefaultImpl.createNullVector();
    }
}
