package controllers;

import gameframework.game.GameUniverse;
import gameframework.game.OverlapRulesApplierDefaultImpl;


public class BreakoutOverlapRules extends OverlapRulesApplierDefaultImpl {
    protected GameUniverse universe;

    @Override
    public void setUniverse(GameUniverse universe) {
        this.universe = universe;
    }
}

