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
    private CanvasDefaultImpl canvas;
    private int width;
    private int height;

    private ArrayList<GameLevel> gameLevels;
    private GameLevelDefaultImpl currentPlayedLevel = null;
    private int levelNumber;

    public BreakoutGame(int w, int h) {
        width = w;
        height = h;

        createGUI();
    }

    @Override
    public void createGUI() {
        frame = new Frame("Firewall Breaker");
        canvas = new CanvasDefaultImpl();

        canvas.setSize(width, height);
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
        System.out.println("save(): Unimplemented operation");
    }

    @Override
    public void pause() {
        System.out.println("pause(): Unimplemented operation");
    }

    @Override
    public void resume() {
        System.out.println("resume(): Unimplemented operation");
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
