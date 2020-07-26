package src;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor extends JFrame {

    private Font font;
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private JButton openButton;
    private JButton saveButton;
    private JTextField searchField;
    private JButton searchButton;
    private JButton prevMatchButton;
    private JButton nextMatchButton;
    private JCheckBox useRegexBox;
    private JPanel searchPane;
    private JPanel textPane;

    private boolean useRegex = false;
    private boolean isTextChanged = false;
    private final List<Integer> searchResultIndexes = new ArrayList<>();
    private final List<Integer> searchResultLength = new ArrayList<>();
    private final CircleIterator iterator = new CircleIterator();

    public TextEditor() {
        super();
        createGUI();
    }

    private void createGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Simple Text Editor");
        setSize(600, 700);
        setLocation(600, 200);
        font = new Font("Courier", Font.PLAIN, 16);

        initWorkAreaComponents();
        initMenuBarComponents();

        add(searchPane, BorderLayout.PAGE_START);
        add(textPane, BorderLayout.CENTER);
    }

    private void initWorkAreaComponents() {
        textArea = new JTextArea();
        textArea.setFont(font);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.getDocument().addDocumentListener(new MyDocumentListener());

        JScrollPane scrollableTextArea = new JScrollPane(textArea);

        fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT files", "txt");
        fileChooser.addChoosableFileFilter(filter);

        openButton = new JButton(new ImageIcon("res/icons/openIcon.png"));
        openButton.setPreferredSize(new Dimension(38, 38));
        openButton.addActionListener(event -> fillTextAreaFromFile());

        saveButton = new JButton(new ImageIcon("res/icons/saveIcon.png"));
        saveButton.setPreferredSize(new Dimension(38, 38));
        saveButton.addActionListener(event -> saveTextAreaToFile());

        searchField = new JTextField();
        searchField.setFont(font.deriveFont(18f));
        searchField.getDocument().addDocumentListener(new MyDocumentListener());

        searchButton = new JButton(new ImageIcon("res/icons/searchIcon.png"));
        searchButton.setPreferredSize(new Dimension(38, 38));
        searchButton.addActionListener(event -> (new TextFinder()).execute());

        prevMatchButton = new JButton(new ImageIcon("res/icons/prevMatchIcon.png"));
        prevMatchButton.setPreferredSize(new Dimension(38, 38));
        prevMatchButton.addActionListener(event -> {
            if (!searchResultIndexes.isEmpty()) {
                iterator.previous();
                selectFoundText(textArea);
            }
        });

        nextMatchButton = new JButton(new ImageIcon("res/icons/nextMatchIcon.png"));
        nextMatchButton.setPreferredSize(new Dimension(38, 38));
        nextMatchButton.addActionListener(event -> {
            if (!searchResultIndexes.isEmpty()) {
                iterator.next();
                selectFoundText(textArea);
            }
        });

        useRegexBox = new JCheckBox("Use regex");
        useRegexBox.setFont(font.deriveFont(18f));
        useRegexBox.addItemListener(e -> updateCheckbox());

        searchPane = new JPanel();
        searchPane.setLayout(new BoxLayout(searchPane, BoxLayout.LINE_AXIS));
        searchPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchPane.add(openButton);
        searchPane.add(Box.createRigidArea(new Dimension(10, 0)));
        searchPane.add(saveButton);
        searchPane.add(Box.createRigidArea(new Dimension(10, 0)));
        searchPane.add(searchField);
        searchPane.add(Box.createRigidArea(new Dimension(10, 0)));
        searchPane.add(searchButton);
        searchPane.add(Box.createRigidArea(new Dimension(10, 0)));
        searchPane.add(prevMatchButton);
        searchPane.add(Box.createRigidArea(new Dimension(10, 0)));
        searchPane.add(nextMatchButton);
        searchPane.add(Box.createRigidArea(new Dimension(10, 0)));
        searchPane.add(useRegexBox);

        textPane = new JPanel();
        textPane.setLayout(new BorderLayout());
        textPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        textPane.add(scrollableTextArea);
    }

    private void initMenuBarComponents() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setMnemonic(KeyEvent.VK_O);
        openMenuItem.addActionListener(event -> fillTextAreaFromFile());

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setMnemonic(KeyEvent.VK_S);
        saveMenuItem.addActionListener(event -> saveTextAreaToFile());

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setMnemonic(KeyEvent.VK_X);
        exitMenuItem.addActionListener(event -> dispose());

        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        JMenu searchMenu = new JMenu("Search");
        searchMenu.setMnemonic(KeyEvent.VK_S);

        JMenuItem startSearchMenuItem = new JMenuItem("Start search");
        startSearchMenuItem.addActionListener(event -> (new TextFinder()).execute());

        JMenuItem prevMatchMenuItem = new JMenuItem("Previous match");
        prevMatchMenuItem.addActionListener(event -> {
            if (!searchResultIndexes.isEmpty()) {
                iterator.previous();
                selectFoundText(textArea);
            }
        });

        JMenuItem nextMatchMenuItem = new JMenuItem("Next match");
        nextMatchMenuItem.addActionListener(event -> {
            if (!searchResultIndexes.isEmpty()) {
                iterator.next();
                selectFoundText(textArea);
            }
        });

        JMenuItem useRegexMenuItem = new JMenuItem("Use regular expressions");
        useRegexMenuItem.addActionListener(event -> {
            useRegexBox.doClick();
            useRegexBox.requestFocusInWindow();
        });

        searchMenu.add(startSearchMenuItem);
        searchMenu.add(prevMatchMenuItem);
        searchMenu.add(nextMatchMenuItem);
        searchMenu.add(useRegexMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(searchMenu);
    }

    private void fillTextAreaFromFile () {
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                Path chosenFile = Paths.get(fileChooser.getSelectedFile().getAbsolutePath());
                String dataFromFile = new String(Files.readAllBytes(chosenFile));
                textArea.setText(dataFromFile);
            } catch (IOException ioException) {
                System.out.println("Error: " + ioException.getMessage());
            }
        }
    }

    private void saveTextAreaToFile() {
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
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
    }

    private void updateCheckbox() {
        useRegex = !useRegex;
        textChanged();
    }

    private void textChanged() {
        isTextChanged = true;
        searchResultIndexes.clear();
        searchResultLength.clear();
    }

    private void selectFoundText(JTextArea textArea) {
        int startIndex = iterator.getIndex();
        int selectionLength = iterator.getLength();

        textArea.setCaretPosition(startIndex + selectionLength);
        textArea.select(startIndex, startIndex + selectionLength);
        textArea.grabFocus();
    }

    private class TextFinder extends SwingWorker<Object, Object> {

        @Override
        protected Object doInBackground() {
            String searchText = searchField.getText();
            if (!"".equals(searchText)) {
                if (isTextChanged) {
                    String text = textArea.getText();
                    if (useRegex) {
                        Pattern pattern = Pattern.compile(searchText);
                        Matcher matcher = pattern.matcher(text);
                        while (matcher.find()) {
                            searchResultIndexes.add(matcher.start());
                            searchResultLength.add(matcher.end() - matcher.start());
                        }
                    } else {
                        int occurrenceIndex = text.indexOf(searchText);
                        int indexForSubString = occurrenceIndex;
                        while (indexForSubString != -1) {
                            searchResultIndexes.add(occurrenceIndex);
                            searchResultLength.add(searchText.length());
                            String searchSubString = text.substring(occurrenceIndex + searchText.length());
                            indexForSubString = searchSubString.indexOf(searchText);
                            occurrenceIndex = indexForSubString + text.length() - searchSubString.length();
                        }
                    }
                    isTextChanged = false;
                }
                if (!searchResultIndexes.isEmpty()) {
                    iterator.restore();
                    selectFoundText(textArea);
                }
            }
            return null;
        }
    }

    private class CircleIterator {

        private int index;

        public int getIndex() {
            if (!searchResultIndexes.isEmpty()) {
                return searchResultIndexes.get(index);
            }
            throw new NoSuchElementException("List with indexes is empty");
        }

        public int getLength() {
            if (!searchResultLength.isEmpty()) {
                return searchResultLength.get(index);
            }
            throw new NoSuchElementException("List with lengths is empty");
        }

        public void restore() {
            index = 0;
        }

        public void previous() {
            if (index - 1 < 0) {
                index = searchResultIndexes.size();
            }
            index--;
        }

        public void next() {
            if (index + 1 >= searchResultIndexes.size()) {
                index = -1;
            }
            index++;
        }
    }

    private class MyDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent documentEvent) {
            textChanged();
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            textChanged();
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
        }
    }
}
