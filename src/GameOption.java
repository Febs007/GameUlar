import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GameOption extends JFrame {
    public GameOption() {
        setTitle("Game Option");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton newGameButton = createStyledButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tutup GameOption
                dispose();
                
                // Buka dan tampilkan SnakeGame
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        SnakeGame snakeGame = new SnakeGame();
                        new GameFrame();
                        snakeGame.setVisible(true);
                    }
                });
            }
        });

        JButton scoreButton = createStyledButton("Skor");
        scoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tutup GameOption
                dispose();

                // Buka HighScores
                showHighScores();
            }
        });

        JButton quitButton = createStyledButton("Exit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin keluar dari game ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 0, 0);
        panel.add(newGameButton, gbc);

        gbc.gridy = 1;
        panel.add(scoreButton, gbc);

        gbc.gridy = 2;
        panel.add(quitButton, gbc);

        add(panel);
        setVisible(true);
    }

    private void showHighScores() {
    new HighScores();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new java.awt.Dimension(200, 70));
        button.setFont(new Font("Georgia", Font.PLAIN, 25));
        button.setBackground(new Color(170, 215, 81));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GameOption();
        });
    }
}
