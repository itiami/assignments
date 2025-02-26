package co.wali;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WebScraperToExcel {

    private static final String BASE_URL = "http://www.informatis-ts.fr/offres_emploi.php";
    private static final int TOTAL_PAGES = 28;

    public void run() {
        List<JobModel> jobList = scrapeAllPages(TOTAL_PAGES);
        exportToExcel(jobList, "Job_Listings.xlsx");
    }

    // Method to scrape all pages
    private List<JobModel> scrapeAllPages(int totalPages) {
        List<JobModel> jobs = new ArrayList<>();

        for (int page = 1; page <= totalPages; page++) {
            String pageUrl = BASE_URL + "?page=" + page;
            System.out.println("Scraping: " + pageUrl);
            jobs.addAll(scrapeData(pageUrl));
        }
        return jobs;
    }


    private List<JobModel> scrapeData(String url) {
        List<JobModel> jobList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url)
                    .header("Accept-Charset", "UTF-8")  // Ensures UTF-8 encoding is requested
                    .get();

            Elements dtTable = doc.select("div#texte_page");

            if (dtTable.first() != null) {
                Elements table = dtTable.select("table");

                if (table.first() != null) {
                    Elements rows = table.select("tbody tr");

                    for (Element row : rows) {
                        Elements cols = row.select("td");

                        if (cols.size() >= 4) {
                            String jobNumberText = cols.get(0).text().trim();
                            String jobTitle = cols.get(1).text();
                            String jobLink = "http://www.informatis-ts.fr/" + cols.get(1).select("a").attr("href");
                            String publicationDate = cols.get(2).text();
                            String departmentText = cols.get(3).text().trim();

                            // Convert to integer safely
                            int jobNumber = safeParseInt(jobNumberText, -1);
                            int department = safeParseInt(departmentText, -1);

                            if (jobNumber == -1) {
                                System.err.println("Invalid job number: " + jobNumberText);
                            }
                            if (department == -1) {
                                System.err.println("Invalid department: " + departmentText);
                            }

                            // Add job data only if jobNumber is valid
                            if (jobNumber != -1) {
                                jobList.add(new JobModel(jobNumber, jobTitle, jobLink, publicationDate, department));
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error fetching data from: " + url);
            e.printStackTrace();
        }
        return jobList;
    }

    // Helper method to parse integers safely
    private int safeParseInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;  // Return default if parsing fails
        }
    }




    private void exportToExcel(List<JobModel> jobList, String fileName) {
        try {

            XSSFWorkbook workbook = new XSSFWorkbook();
            FileOutputStream fileOut = new FileOutputStream(fileName);

            XSSFSheet sheet = workbook.createSheet("Job Listings");
            CreationHelper createHelper = workbook.getCreationHelper();

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Job Number", "Title", "Link", "Publication Date", "Department"};

            for (int i=0; i <columns.length; i++){
                sheet.autoSizeColumn(i);
            }

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }

            // Write job data
            int rowNum = 1;
            for (JobModel job : jobList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(job.getJobNumber());
                row.createCell(1).setCellValue(job.getJobTitle());
//                row.createCell(2).setCellValue(job.getJobLink());
                // Create hyperlink for job link
                Cell linkCell = row.createCell(2);
                linkCell.setCellValue(job.getJobLink()); // Display the link text

                Hyperlink link = createHelper.createHyperlink(HyperlinkType.URL);
                link.setAddress(job.getJobLink());
                linkCell.setHyperlink(link);

                // Style for hyperlink
                CellStyle hlinkStyle = workbook.createCellStyle();
                Font hlinkFont = workbook.createFont();
                hlinkFont.setUnderline(Font.U_SINGLE);
                hlinkFont.setColor(IndexedColors.BLUE.getIndex());
                hlinkStyle.setFont(hlinkFont);
                linkCell.setCellStyle(hlinkStyle);

                row.createCell(3).setCellValue(job.getPublicationDate());
                row.createCell(4).setCellValue(job.getDepartment());
            }

            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }


//            sheet.createTable(jobList.size())


            workbook.write(fileOut);
            System.out.println("Excel file saved: " + fileName);

        } catch (IOException e) {
            System.err.println("Error writing to Excel file");
            e.printStackTrace();
        }
    }
}

// Job model class

