package src;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.KeyEvent;
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

        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setName("FileChooser");
        jfc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT files", "txt");
        jfc.addChoosableFileFilter(filter);

        JButton openButton = new JButton(new ImageIcon("res/icons/openIcon.png"));
        openButton.setName("OpenButton");
        openButton.setPreferredSize(new Dimension(38, 38));
        openButton.addActionListener(event -> {
            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                try {
                    String dataFromFile = new String(Files.readAllBytes(Paths.get(jfc.getSelectedFile().getAbsolutePath())));
                    textArea.setText(dataFromFile);
                } catch (IOException ioException) {
                    System.out.println("Error: " + ioException.getMessage());
                }
            }
        });

        JButton saveButton = new JButton(new ImageIcon("res/icons/saveIcon.png"));
        saveButton.setName("SaveButton");
        saveButton.setPreferredSize(new Dimension(38, 38));
        saveButton.addActionListener(event -> {
            int returnValue = jfc.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String filePath = jfc.getSelectedFile().getAbsolutePath();
                if (!".txt".equals(filePath.substring(filePath.length() - 4))) {
                    filePath += ".txt";
                }
                File targetFile = new File(filePath);
                String dataToFile = textArea.getText();
                try (FileWriter writer = new FileWriter(targetFile)) {
                    writer.write(dataToFile);
                } catch (IOException ioException) {
                    System.out.println("Error: " + ioException.getMessage());
                }
            }
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
        filePane.add(openButton);
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
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setName("MenuOpen");
        openMenuItem.setMnemonic(KeyEvent.VK_O);
        openMenuItem.addActionListener(event -> {
            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                try {
                    String dataFromFile = new String(Files.readAllBytes(Paths.get(jfc.getSelectedFile().getAbsolutePath())));
                    textArea.setText(dataFromFile);
                } catch (IOException ioException) {
                    System.out.println("Error: " + ioException.getMessage());
                }
            }
        });

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        saveMenuItem.setMnemonic(KeyEvent.VK_S);
        saveMenuItem.addActionListener(event -> {
            int returnValue = jfc.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String filePath = jfc.getSelectedFile().getAbsolutePath();
                if (!".txt".equals(filePath.substring(filePath.length() - 4))) {
                    filePath += ".txt";
                }
                File targetFile = new File(filePath);
                String dataToFile = textArea.getText();
                try (FileWriter writer = new FileWriter(targetFile)) {
                    writer.write(dataToFile);
                } catch (IOException ioException) {
                    System.out.println("Error: " + ioException.getMessage());
                }
            }
        });

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");
        exitMenuItem.setMnemonic(KeyEvent.VK_X);
        exitMenuItem.addActionListener(event -> dispose());

        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");
        searchMenu.setMnemonic(KeyEvent.VK_S);

        JMenuItem startSearchMenuItem = new JMenuItem("Start search");
        startSearchMenuItem.setName("MenuStartSearch");
        startSearchMenuItem.addActionListener(event -> {
            //
        });

        JMenuItem prevMatchMenuItem = new JMenuItem("Previous match");
        prevMatchMenuItem.setName("MenuPreviousMatch");
        prevMatchMenuItem.addActionListener(event -> {
            //
        });

        JMenuItem nextMatchMenuItem = new JMenuItem("Next match");
        nextMatchMenuItem.setName("MenuNextMatch");
        nextMatchMenuItem.addActionListener(event -> {
            //
        });

        JMenuItem useRegexMenuItem = new JMenuItem("Use regular expressions");
        useRegexMenuItem.setName("MenuUseRegExp");
        useRegexMenuItem.addActionListener(event -> {
            //
        });

        searchMenu.add(startSearchMenuItem);
        searchMenu.add(prevMatchMenuItem);
        searchMenu.add(nextMatchMenuItem);
        searchMenu.add(useRegexMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(searchMenu);


        add(filePane, BorderLayout.PAGE_START);
        add(textPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
