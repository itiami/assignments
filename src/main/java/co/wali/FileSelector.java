package co.wali;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import java.io.File;

public class FileSelector {
    public static File selectDirectory() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }

    public void run() {
        File selectedDirectory = selectDirectory();
        if (selectedDirectory != null) {
            System.out.println("Selected directory: " + selectedDirectory.getAbsolutePath());
        } else {
            System.out.println("No directory selected");
        }
    }
}
