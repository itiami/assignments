package co.wali;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Paths;

public class SelectCsvFromResourcesDir {

    public void run(){
        selectFile(0.7, 0.6);
    }


    private void selectFile(double widthPercent, double heightPercent){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * widthPercent);
        int height = (int) (screenSize.height * heightPercent);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Windows Look and Feel
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(()->{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(true);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle("Select CSV File(s)");
            fileChooser.setPreferredSize(new Dimension(width, height));

            String resourcesPath = Paths.get("src", "main", "resources").toAbsolutePath().toString();
            File resourcesDir = new File(resourcesPath);
            if (resourcesDir.exists() && resourcesDir.isDirectory()) {
                fileChooser.setCurrentDirectory(resourcesDir);
            }

            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION){
                File[] selectedFiles = fileChooser.getSelectedFiles();

                for(File file: selectedFiles){
                    System.out.println("Reading File: " + file.getAbsolutePath());
                    readFile(file);
                }
            }
        });
    }

    private void readFile(File file){
        try {
            Reader reader = new FileReader(file);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder().setSkipHeaderRecord(true).get().parse(reader);

            for (CSVRecord record: records){
                System.out.println(record);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
