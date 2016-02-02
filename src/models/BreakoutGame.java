package models;

import gameframework.base.ObservableValue;
import gameframework.game.CanvasDefaultImpl;
import gameframework.game.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Observable;
import java.util.Observer;


public class BreakoutGame implements Game, Observer {
    private final static int WIDTH = 640;
    private final static int HEIGHT = 480;
    private static final int NUMBER_OF_LIVES = 3;


    private ObservableValue<Integer> score[] = new ObservableValue[1];
    private ObservableValue<Integer> life[] = new ObservableValue[1];
    private ObservableValue<Boolean> endOfGame = null;

    private Frame frame;
    private CanvasDefaultImpl canvas;

    private String[] gameLevelDefinitions;
    private ArrayDeque<BreakoutGameLevel> gameLevelQueue = new ArrayDeque<>();
    private BreakoutGameLevel currentPlayedLevel = null;
    private int levelNumber;

    private Label lifeText, scoreText;
    private Label lifeValue, scoreValue;
    private Label currentLevel, currentLevelValue;


    public BreakoutGame() {
        life[0] = new ObservableValue<>(0);
        score[0] = new ObservableValue<>(0);

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

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Put platform look and feel.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(frame);
        }
        catch (ClassNotFoundException e) { }
        catch (InstantiationException e) { }
        catch (IllegalAccessException e) { }
        catch (UnsupportedLookAndFeelException e) { }

        frame.pack();
        frame.setVisible(true);
    }

    private void createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");
        MenuItem start = new MenuItem("New");
        MenuItem quit = new MenuItem("Quit");
        MenuItem load = new MenuItem("Load");

        menuBar.add(file);
        frame.setMenuBar(menuBar);

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset(gameLevelDefinitions);
            }
        });
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadFromFile();
            }
        });

        file.add(start);
        file.add(load);
        file.add(quit);
    }

    private Container createStatusBar() {
        JPanel statusBar = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        
        statusBar.setLayout(layout);
        lifeValue = new Label(life[0].getValue().toString());
        scoreValue = new Label(score[0].getValue().toString());
        currentLevelValue = new Label(String.valueOf(levelNumber));

        lifeValue.setPreferredSize(new Dimension(50, 10));
        scoreValue.setPreferredSize(new Dimension(100, 10));

        statusBar.add(lifeText);
        statusBar.add(lifeValue);
        statusBar.add(scoreText);
        statusBar.add(scoreValue);
        statusBar.add(currentLevel);
        statusBar.add(currentLevelValue);
        
        return statusBar;
    }

    // Re-init every levels, according given definitions.
    private synchronized void reset(String[] levelDefinitions) {
        levelNumber = 0;

        score[0].addObserver(this);
        life[0].addObserver(this);
        life[0].setValue(NUMBER_OF_LIVES);
        score[0].setValue(0);

        gameLevelQueue.clear();
        for (String levelDefinition: levelDefinitions) {
            try {
                gameLevelQueue.add(new BreakoutGameLevel(this, levelDefinition));
            }
            catch (IOException e) {
                System.err.println(levelDefinition + " doesn't exist");
            }
        }

        if (currentPlayedLevel != null && currentPlayedLevel.isAlive()) {
            currentPlayedLevel.interrupt();
            currentPlayedLevel.end();
        }

        notifyAll();        // Ok, there are new levels, loop can continue if it was stopped.
    }

    private void loadFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("TXT files", "txt"));

        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            reset(new String[] { file.getAbsolutePath() });
        }
    }

    // Loop that launch threads.
    private void loop() throws InterruptedException {
        while (true) {
            synchronized (this) {
                while (gameLevelQueue.isEmpty()) wait();    // If there is no level to launch, just wait.
            }

            endOfGame = new ObservableValue<>(false);
            endOfGame.addObserver(this);

            try {
                currentPlayedLevel = gameLevelQueue.pop();
                currentLevelValue.setText(String.valueOf(++levelNumber));

                currentPlayedLevel.start();
                currentPlayedLevel.join();
            }
            catch (InterruptedException e) { }
        }
    }

    @Override
    public void start() {
        try {
            loop();         // Launch loop that executes threads.
        }
        catch (InterruptedException e) { }
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

    @Override
    public Canvas getCanvas() {
        return canvas;
    }

    public void setLevels(String[] levels) {
        gameLevelDefinitions = levels;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == endOfGame) {
            if (endOfGame.getValue()) {
                currentPlayedLevel.interrupt();
                currentPlayedLevel.end();

                if (gameLevelQueue.isEmpty())
                    displayImage("Message for winners", "assets/images/win.png");
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
                        displayImage("Message for losers", "assets/images/lose.png");
                    }
                }
            }

            for (ObservableValue<Integer> scoreObservable : score) {
                if (o == scoreObservable)
                    scoreValue.setText(
                            Integer.toString(((ObservableValue<Integer>) o).getValue()));
            }
        }
    }

    public void displayImage(String title, String filename) {
        try {
            BufferedImage image = ImageIO.read(new File(filename));
            JDialog dialog = new JDialog(frame, title, true);

            dialog.setLocationRelativeTo(frame);
            dialog.add(new JLabel(new ImageIcon(image)));
            dialog.pack();
            dialog.setVisible(true);
        }
        catch (IOException e) { }
    }
}
