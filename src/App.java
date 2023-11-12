import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class App extends JFrame {
    public App() {
        setTitle("Selamat Datang Di Game Ular");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.5; // Set bobot gambar ular

        // Menambahkan gambar ular di tengah
        ImageIcon snakeImage = new ImageIcon("logo.png");
        Image originalImage = snakeImage.getImage(); // Ambil gambar asli
        Image resizedImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        // Buat ImageIcon baru dengan gambar yang sudah diperkecil
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel imageLabel = new JLabel(resizedIcon); // Menggunakan resizedIcon
        panel.add(imageLabel, gbc);


        gbc.gridy = 1;
        gbc.weighty = 0.1; // Set bobot judul
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("Game Ular");
        titleLabel.setFont(new Font("Ravie", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.1; // Set bobot tombol
        gbc.anchor = GridBagConstraints.CENTER;

        JButton startButton = new JButton("Mulai Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGamePage();
            }
        });
        startButton.setBackground(new Color(170, 215, 81)); // Warna latar belakang
        startButton.setForeground(Color.BLACK); // Warna teks
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setOpaque(true);
        startButton.setFont(new Font("Georgia", Font.BOLD, 24));
        startButton.setPreferredSize(new Dimension(200, 50));
        panel.add(startButton, gbc);

        add(panel);
        setVisible(true);
        Audio clicked = new Audio("Theme.wav");
        clicked.audio.start();
    }

    private void openGamePage() {

    setVisible(false);
    // Mendapatkan seluruh perangkat grafis
    GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

    // Menggunakan perangkat pertama (utama) untuk mendapatkan ukuran layar
    GraphicsDevice primaryDevice = devices[0];
    Dimension screenSize = primaryDevice.getDefaultConfiguration().getBounds().getSize();

    // Mengatur ukuran frame sesuai dengan ukuran layar
    screenSize.getWidth();
    screenSize.getHeight();

    JFrame frame = new JFrame("Game Ular");
    frame.setSize(600, 600); // Mengatur ukuran frame menjadi 600x600
    frame.setLocationRelativeTo(null);
    frame.setResizable(false); // Menambahkan baris ini untuk menghentikan pengguna untuk mengubah ukuran frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    GameOption gameOption = new GameOption();
    gameOption.setBackground(Color.BLACK);
    frame.add(gameOption);
    frame.pack();
    gameOption.requestFocus();
    

    frame.setVisible(true);
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App();
            }

        });
    }
}
