package src;


public class ApplicationRunner {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TextEditor frame = new TextEditor();
                frame.setVisible(true);
            }
        });
    }
}
