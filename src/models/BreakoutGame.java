package models;

import gameframework.base.ObservableValue;
import gameframework.game.CanvasDefaultImpl;
import gameframework.game.Game;
import gameframework.game.GameLevel;
import gameframework.game.GameLevelDefaultImpl;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;


public class BreakoutGame implements Game {
    private Frame frame;
    protected CanvasDefaultImpl canvas;

    protected ArrayList<GameLevel> gameLevels;
    private GameLevelDefaultImpl currentPlayedLevel = null;
    protected int levelNumber;


    public BreakoutGame() {
        createGUI();
    }

    @Override
    public void createGUI() {
        frame = new Frame("Breakout");
        canvas = new CanvasDefaultImpl();

        canvas.setSize(640, 480);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    public void start() {
        this.levelNumber = 0;

        for (GameLevel level: gameLevels) {
            try {
                if(this.currentPlayedLevel != null && this.currentPlayedLevel.isAlive()) {
                    this.currentPlayedLevel.interrupt();
                    this.currentPlayedLevel = null;
                }

                this.currentPlayedLevel = (GameLevelDefaultImpl) level;
                ++this.levelNumber;
                this.currentPlayedLevel.start();
                this.currentPlayedLevel.join();
            }
            catch (Exception e) { }
        }
    }

    @Override
    public void restore() {
        System.out.println("restore(): Unimplemented operation");
    }

    @Override
    public void save() {
        System.out.println("restore(): Unimplemented operation");
    }

    @Override
    public void pause() {
        System.out.println("restore(): Unimplemented operation");
    }

    @Override
    public void resume() {
        System.out.println("restore(): Unimplemented operation");
    }

    @Override
    public ObservableValue<Integer>[] score() {
        return null;
    }

    @Override
    public ObservableValue<Integer>[] life() {
        return null;
    }

    @Override
    public ObservableValue<Boolean> endOfGame() {
        return null;
    }

    public void setLevels(ArrayList<GameLevel> levels) {
        this.gameLevels = levels;
    }
}
