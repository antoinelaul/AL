package models;

import gameframework.base.ObservableValue;
import gameframework.game.CanvasDefaultImpl;
import gameframework.game.Game;
import gameframework.game.GameLevel;
import gameframework.game.GameLevelDefaultImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class BreakoutGame implements Game, Observer {
    private final static int WIDTH = 640;
    private final static int HEIGHT = 480;
    private static final int NUMBER_OF_LIVES = 3;


    protected ObservableValue<Integer> score[] = new ObservableValue[1];
    protected ObservableValue<Integer> life[] = new ObservableValue[1];
    protected ObservableValue<Boolean> endOfGame = null;

    private Frame frame;
    private CanvasDefaultImpl canvas;

    private ArrayList<GameLevel> gameLevels;
    private GameLevelDefaultImpl currentPlayedLevel = null;
    private int levelNumber;

    protected Label lifeText, scoreText;
    protected Label lifeValue, scoreValue;
    protected Label currentLevel, currentLevelValue;


    public BreakoutGame() {
        life[0] = new ObservableValue<Integer>(0);
        score[0] = new ObservableValue<Integer>(0);

        lifeText = new Label("Lives:");
        scoreText = new Label("Score:");
        currentLevel = new Label("Level:");

        createGUI();
    }

    @Override
    public void createGUI() {
        frame = new Frame("Firewall Breaker");
        canvas = new CanvasDefaultImpl();

        createMenuBar();

        canvas.setSize(WIDTH, HEIGHT);
        frame.add(canvas);
        Container sb = createStatusBar();
        frame.add(sb, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");
        MenuItem start = new MenuItem("New");
        MenuItem quit = new MenuItem("Quit");
        Menu game = new Menu("Game");
        MenuItem pause = new MenuItem("Pause");
        MenuItem resume = new MenuItem("Resume");

        menuBar.add(file);
        menuBar.add(game);
        frame.setMenuBar(menuBar);

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        pause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pause();
            }
        });
        resume.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resume();
            }
        });

        file.add(start);
        file.add(quit);
        game.add(pause);
        game.add(resume);
    }


    private Container createStatusBar() {
        JPanel statusBar = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        
        statusBar.setLayout(layout);
        lifeValue = new Label(Integer.toString(life[0].getValue()));
        scoreValue = new Label(Integer.toString(score[0].getValue()));
        currentLevelValue = new Label(Integer.toString(levelNumber));

        scoreValue.setPreferredSize(new Dimension(100, 10));

        statusBar.add(lifeText);
        statusBar.add(lifeValue);
        statusBar.add(scoreText);
        statusBar.add(scoreValue);
        statusBar.add(currentLevel);
        statusBar.add(currentLevelValue);
        
        return statusBar;
    }

        @Override
    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    public void start() {
        levelNumber = 0;

        score[0].addObserver(this);
        life[0].addObserver(this);
        life[0].setValue(NUMBER_OF_LIVES);
        score[0].setValue(0);

        for (GameLevel level: gameLevels) {
            endOfGame = new ObservableValue<Boolean>(false);
            endOfGame.addObserver(this);

            try {
                if (currentPlayedLevel != null && currentPlayedLevel.isAlive()) {
                    currentPlayedLevel.interrupt();
                    currentPlayedLevel = null;
                }

                currentPlayedLevel = (GameLevelDefaultImpl) level;
                currentLevelValue.setText(String.valueOf(levelNumber + 1));
                ++levelNumber;

                currentPlayedLevel.start();
                currentPlayedLevel.join();
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
        return score;
    }

    @Override
    public ObservableValue<Integer>[] life() {
        return life;
    }

    @Override
    public ObservableValue<Boolean> endOfGame() {
        return endOfGame;
    }

    public void setLevels(ArrayList<GameLevel> levels) {
        gameLevels = levels;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == endOfGame) {
            if (endOfGame.getValue()) {
                currentPlayedLevel.interrupt();
                currentPlayedLevel.end();
            }
        }
        else {
            for (ObservableValue<Integer> lifeObservable : life) {
                if (o == lifeObservable) {
                    int lives = ((ObservableValue<Integer>) o).getValue();
                    lifeValue.setText(Integer.toString(lives));

                    if (lives == 0) {
                        currentPlayedLevel.interrupt();
                        currentPlayedLevel.end();

                        JOptionPane.showMessageDialog(frame, "You lost... what kind of shit are you ?",
                                "Message for loosers", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }

            for (ObservableValue<Integer> scoreObservable : score) {
                if (o == scoreObservable) {
                    scoreValue
                            .setText(Integer
                                    .toString(((ObservableValue<Integer>) o)
                                            .getValue()));
                }
            }
        }
    }
}
