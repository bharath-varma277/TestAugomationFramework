package springframework.maven.utility;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class ReadExcel {


    public String[][] getData(String sheetName) throws IOException {
        Logger logger = Logger.getLogger(getClass().getName());
        File file = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\testdata.xlsx");
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheet(sheetName);
        int totalRows = sheet.getLastRowNum();

        System.out.println(totalRows);
        XSSFRow rowCells = sheet.getRow(0);
        int totalCols = rowCells.getLastCellNum();
        logger.info("Total Columns:{} " + totalCols);


        String[][] testData = new String[totalRows][totalCols];
        DataFormatter dataFormatter = new DataFormatter();
        for (int i = 1; i <= totalRows; i++) {
            for (int j = 0; j < totalCols; j++) {
                testData[i - 1][j] = dataFormatter.formatCellValue(sheet.getRow(i).getCell(j));
            }
        }
        return testData;
    }
}
