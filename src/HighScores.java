import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class HighScores extends JFrame {
    private JButton backButton;
    private JTable highScoresTable;
    private JButton deleteButton;

    public HighScores() {
        setTitle("Skor Tertinggi");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set latar belakang frame menjadi putih
        getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Skor Tertinggi");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets.top = 5; // Menambahkan jarak di bawah judul
        panel.add(titleLabel, gbc);

        try {
            // Database connection details
            String url = "jdbc:mysql://localhost:3306/gameular";
            String username = "root"; // Ubah sesuai dengan pengaturan MySQL Anda
            String password = ""; // Kosongkan jika tidak ada password

            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            String query = "SELECT Nama, Skor FROM skor_tertinggi ORDER BY Skor DESC";
            ResultSet resultSet = statement.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("No");
            model.addColumn("Nama");
            model.addColumn("Skor");

            int position = 1;
            while (resultSet.next()) {
                String name = resultSet.getString("Nama");
                int score = resultSet.getInt("Skor");
                model.addRow(new Object[]{position, name, score});
                position++;
            }

            highScoresTable = new JTable(model);
            highScoresTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            JScrollPane tableScrollPane = new JScrollPane(highScoresTable);

            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(tableScrollPane, gbc);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal mengambil skor tertinggi", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Menambahkan tombol "Delete" dengan format yang diminta
        deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Georgia", Font.PLAIN, 25));
        deleteButton.setBackground(new Color(255, 182, 193));

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.insets.top = 10;
        panel.add(deleteButton, gbc);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = highScoresTable.getSelectedRow();
                if (selectedRow != -1) {
                    DefaultTableModel model = (DefaultTableModel) highScoresTable.getModel();
                    String nameToDelete = (String) model.getValueAt(selectedRow, 1);

                    JOptionPane.showConfirmDialog(
                    HighScores.this,
                    "Apakah Anda yakin untuk menghapus data skor ini?",
                    "aku yakin",
                    JOptionPane.YES_NO_OPTION);
                    
                    try {
                        // Establish a new connection for deletion
                        String url = "jdbc:mysql://localhost:3306/gameular";
                        String username = "root"; // Ubah sesuai dengan pengaturan MySQL Anda
                        String password = ""; // Kosongkan jika tidak ada password

                        Connection connection = DriverManager.getConnection(url, username, password);
                        
                        // Delete the record from the database
                        String deleteQuery = "DELETE FROM skor_tertinggi WHERE Nama=?";
                        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                        preparedStatement.setString(1, nameToDelete);
                        preparedStatement.executeUpdate();
                        
                        // Remove the selected row from the table
                        model.removeRow(selectedRow);
                        
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(HighScores.this, "Gagal menghapus data", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(HighScores.this, "Pilih baris yang akan dihapus", "Peringatan", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Menambahkan tombol "Kembali" dengan format yang diminta
        backButton = new JButton("Back");
        backButton.setFont(new Font("Georgia", Font.PLAIN, 25));
        backButton.setBackground(new Color(170, 215, 81));

        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.insets.top = 10;
        panel.add(backButton, gbc);

        backButton.addActionListener(e -> {
            // Tutup frame HighScores
            dispose();

            // Tampilkan frame GameOption
            SwingUtilities.invokeLater(() -> new GameOption());
        });

        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HighScores());
    }
}
