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
        setSize(600, 700);
        setLocation(600, 200);
        Font font = new Font("Courier", Font.PLAIN, 16);


        JTextArea textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setFont(font);

        JScrollPane scrollableTextArea = new JScrollPane(textArea);
        scrollableTextArea.setName("ScrollPane");

        JButton loadButton = new JButton(new ImageIcon("res/icons/folderIcon.png"));
        loadButton.setName("LoadButton");
        loadButton.setPreferredSize(new Dimension(38, 38));
        loadButton.addActionListener(event -> {
            /*textArea.setText(null);
            String fileName = textField.getText();
            try {
                String dataFromFile = new String(Files.readAllBytes(Paths.get(fileName)));
                textArea.setText(dataFromFile);
            } catch (IOException ioException) {
                System.out.println("Error: " + ioException.getMessage());
            }*/
        });

        JButton saveButton = new JButton(new ImageIcon("res/icons/saveIcon.png"));
        saveButton.setName("SaveButton");
        saveButton.setPreferredSize(new Dimension(38, 38));
        saveButton.addActionListener(event -> {
            /*String fileName = textField.getText();
            File targetFile = new File(fileName);
            String dataToFile = textArea.getText();
            try (FileWriter writer = new FileWriter(targetFile)) {
                writer.write(dataToFile);
            } catch (IOException ioException) {
                System.out.println("Error: " + ioException.getMessage());
            }*/
        });

        JTextField searchField = new JTextField();
        searchField.setName("SearchField");
        searchField.setFont(font.deriveFont(18f));

        JButton searchButton = new JButton(new ImageIcon("res/icons/searchIcon.png"));
        searchButton.setName("StartSearchButton");
        searchButton.setPreferredSize(new Dimension(38, 38));
        searchButton.addActionListener(event -> {
            // search operation
        });

        JButton prevMatchButton = new JButton(new ImageIcon("res/icons/prevMatchIcon.png"));
        prevMatchButton.setName("PreviousMatchButton");
        prevMatchButton.setPreferredSize(new Dimension(38, 38));
        prevMatchButton.addActionListener(event -> {
            // search operation
        });

        JButton nextMatchButton = new JButton(new ImageIcon("res/icons/nextMatchIcon.png"));
        nextMatchButton.setName("NextMatchButton");
        nextMatchButton.setPreferredSize(new Dimension(38, 38));
        nextMatchButton.addActionListener(event -> {
            // search operation
        });

        JCheckBox useRegexBox = new JCheckBox("Use regex");
        useRegexBox.setFont(font.deriveFont(18f));

        JPanel filePane = new JPanel();
        filePane.setLayout(new BoxLayout(filePane, BoxLayout.LINE_AXIS));
        filePane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        filePane.add(loadButton);
        filePane.add(Box.createRigidArea(new Dimension(10, 0)));
        filePane.add(saveButton);
        filePane.add(Box.createRigidArea(new Dimension(10, 0)));
        filePane.add(searchField);
        filePane.add(Box.createRigidArea(new Dimension(10, 0)));
        filePane.add(searchButton);
        filePane.add(Box.createRigidArea(new Dimension(10, 0)));
        filePane.add(prevMatchButton);
        filePane.add(Box.createRigidArea(new Dimension(10, 0)));
        filePane.add(nextMatchButton);
        filePane.add(Box.createRigidArea(new Dimension(10, 0)));
        filePane.add(useRegexBox);

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
            /*textArea.setText(null);
            String fileName = textField.getText();
            try {
                String dataFromFile = new String(Files.readAllBytes(Paths.get(fileName)));
                textArea.setText(dataFromFile);
            } catch (IOException ioException) {
                System.out.println("Error: " + ioException.getMessage());
            }*/
        });

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        saveMenuItem.addActionListener(event -> {
            /*String fileName = textField.getText();
            File targetFile = new File(fileName);
            String dataToFile = textArea.getText();
            try (FileWriter writer = new FileWriter(targetFile)) {
                writer.write(dataToFile);
            } catch (IOException ioException) {
                System.out.println("Error: " + ioException.getMessage());
            }*/
        });

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");
        exitMenuItem.addActionListener(event -> dispose());

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
