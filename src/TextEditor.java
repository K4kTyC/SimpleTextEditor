package src;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextEditor extends JFrame {

    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Simple Text Editor");
        setSize(500, 700);
        Font font = new Font("Courier", Font.PLAIN, 16);


        JTextField textField = new JTextField();
        textField.setName("FilenameField");
        textField.setPreferredSize(new Dimension(100, 30));
        textField.setFont(font.deriveFont(14f));

        JTextArea textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setFont(font);

        JScrollPane scrollableTextArea = new JScrollPane(textArea);
        scrollableTextArea.setName("ScrollPane");

        JButton saveButton = new JButton("Save");
        saveButton.setName("SaveButton");
        saveButton.setPreferredSize(new Dimension(100, 30));
        saveButton.addActionListener(event -> {
            String fileName = textField.getText();
            File targetFile = new File(fileName);
            String dataToFile = textArea.getText();
            try (FileWriter writer = new FileWriter(targetFile)) {
                writer.write(dataToFile);
            } catch (IOException ioException) {
                System.out.println("Error: " + ioException.getMessage());
            }
        });

        JButton loadButton = new JButton("Load");
        loadButton.setName("LoadButton");
        loadButton.setPreferredSize(new Dimension(100, 30));
        loadButton.addActionListener(event -> {
            textArea.setText(null);
            String fileName = textField.getText();
            try {
                String dataFromFile = new String(Files.readAllBytes(Paths.get(fileName)));
                textArea.setText(dataFromFile);
            } catch (IOException ioException) {
                System.out.println("Error: " + ioException.getMessage());
            }
        });

        JPanel filePane = new JPanel();
        filePane.setLayout(new BoxLayout(filePane, BoxLayout.LINE_AXIS));
        filePane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        filePane.add(textField);
        filePane.add(Box.createRigidArea(new Dimension(10, 0)));
        filePane.add(saveButton);
        filePane.add(Box.createRigidArea(new Dimension(10, 0)));
        filePane.add(loadButton);

        JPanel textPane = new JPanel();
        textPane.setLayout(new BorderLayout());
        textPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        textPane.add(scrollableTextArea);


        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");

        JMenuItem loadMenuItem = new JMenuItem("Load");
        loadMenuItem.setName("MenuLoad");
        loadMenuItem.addActionListener(event -> {
            textArea.setText(null);
            String fileName = textField.getText();
            try {
                String dataFromFile = new String(Files.readAllBytes(Paths.get(fileName)));
                textArea.setText(dataFromFile);
            } catch (IOException ioException) {
                System.out.println("Error: " + ioException.getMessage());
            }
        });

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        saveMenuItem.addActionListener(event -> {
            String fileName = textField.getText();
            File targetFile = new File(fileName);
            String dataToFile = textArea.getText();
            try (FileWriter writer = new FileWriter(targetFile)) {
                writer.write(dataToFile);
            } catch (IOException ioException) {
                System.out.println("Error: " + ioException.getMessage());
            }
        });

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");
        exitMenuItem.addActionListener(event -> {
            dispose();
        });

        fileMenu.add(loadMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);


        add(filePane, BorderLayout.PAGE_START);
        add(textPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
