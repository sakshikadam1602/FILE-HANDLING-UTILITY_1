import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FileEditorGUI extends JFrame {
    // GUI Components
    private JTextArea textArea;
    private JButton openButton, saveButton, modifyButton, clearButton;
    private JFileChooser fileChooser;
    private File currentFile;

    public FileEditorGUI() {
        // Set up the main frame
        setTitle("Advanced File Editor");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a text area with a scroll pane
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Initialize buttons for file operations
        openButton = new JButton("Open File");
        saveButton = new JButton("Save File");
        modifyButton = new JButton("Modify File");
        clearButton = new JButton("Clear Text");
        
        fileChooser = new JFileChooser();

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(clearButton);
        
        // Add action listeners to buttons
        openButton.addActionListener(e -> openFile());
        saveButton.addActionListener(e -> saveFile());
        modifyButton.addActionListener(e -> modifyFile());
        clearButton.addActionListener(e -> textArea.setText(""));
        
        // Add components to frame
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Method to open a file and read its content
    private void openFile() {
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
                textArea.read(reader, null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error opening file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to save the content of the text area to a file
    private void saveFile() {
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
                textArea.write(writer);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to modify an already opened file
    private void modifyFile() {
        if (currentFile != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
                writer.write(textArea.getText());
                JOptionPane.showMessageDialog(this, "File modified successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error modifying file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No file opened!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FileEditorGUI().setVisible(true);
        });
    }
}
