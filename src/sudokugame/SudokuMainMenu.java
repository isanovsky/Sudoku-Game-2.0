package sudokugame;

import javax.swing.*;

public class SudokuMainMenu extends JFrame {

    public SudokuMainMenu() {
        initComponents();
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        JPanel jPanel1 = new BackgroundPanel("src/resources/imageBackground.png");
        JButton playGameButton = new JButton();
        JButton exitButton = new JButton();
        JComboBox<String> difficultiesBox = new JComboBox<>();
        JLabel jLabel1 = new JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sudoku Game 3.0");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        playGameButton.setBackground(java.awt.SystemColor.textHighlight);
        playGameButton.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        playGameButton.setForeground(new java.awt.Color(255, 255, 255));
        playGameButton.setText("PLAY GAME");
        playGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playGameButtonActionPerformed(evt, difficultiesBox);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 577;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(28, 6, 0, 6);
        jPanel1.add(playGameButton, gridBagConstraints);

        exitButton.setBackground(java.awt.SystemColor.textHighlight);
        exitButton.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        exitButton.setForeground(java.awt.SystemColor.textHighlightText);
        exitButton.setText("EXIT");
        exitButton.addActionListener(this::exitButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 618;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 6, 59, 6);
        jPanel1.add(exitButton, gridBagConstraints);

        difficultiesBox.setBackground(java.awt.SystemColor.textHighlight);
        difficultiesBox.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        difficultiesBox.setForeground(java.awt.SystemColor.textHighlightText);
        difficultiesBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"SELECT DIFFICULTIES", "EASY", "MEDIUM", "HARD"}));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 482;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 6, 0, 6);
        jPanel1.add(difficultiesBox, gridBagConstraints);
        difficultiesBox.getAccessibleContext().setAccessibleParent(playGameButton);

        jLabel1.setBackground(java.awt.SystemColor.textHighlight);
        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 22)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Main Menu");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 572;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(300, 6, 0, 6);
        jPanel1.add(jLabel1, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    private void playGameButtonActionPerformed(java.awt.event.ActionEvent evt, JComboBox<String> difficultiesBox) {
        String selectedDifficulty = (String) difficultiesBox.getSelectedItem();
        SudokuGenerator.Difficulty difficulty;

        switch (selectedDifficulty) {
            case "EASY":
                difficulty = SudokuGenerator.Difficulty.EASY;
                break;
            case "MEDIUM":
                difficulty = SudokuGenerator.Difficulty.MEDIUM;
                break;
            case "HARD":
                difficulty = SudokuGenerator.Difficulty.HARD;
                break;
            default:
                JOptionPane.showMessageDialog(this, "Please select a difficulty level.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        }

        // Create and show the Sudoku game frame
        new SudokuFrame(difficulty).setVisible(true);
        this.dispose();  // Close the main menu
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SudokuMainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SudokuMainMenu().setVisible(true);
            }
        });
    }
}
