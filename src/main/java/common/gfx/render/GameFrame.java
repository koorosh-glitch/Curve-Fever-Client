package common.gfx.render;

import common.gfx.util.Logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame {
    GameEngine engine;
    Logic gameLogic;
    public GameFrame(GameEngine engine) {
        this.engine = engine;
        gameLogic = engine.getGameLogic();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
        setLayout(null);
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                engine.paint(g);
            }
        };
        setContentPane(mainPanel);
        mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                engine.resize(getSize());
            }
        });
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                gameLogic.handleKeyPress(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                gameLogic.handleKeyRelease(e.getKeyCode());
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameLogic.handleMouseClick(e.getX() + engine.getViewPort().getX(),
                        e.getY() + engine.getViewPort().getY());
            }
        });
    }

    public JPanel getPanel() {
        return (JPanel) getContentPane();
    }
}
