package SecretSanta.Santa.Controller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
public class MultipartFileController {

    private String fileLocation;
    private Map<String, String> excelMap = new HashMap<>();

    @PostMapping("/uploadExcel")
    public String uploadFile(Model model, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        System.out.println("read: " + fileName);
        try {
            InputStream inputStream = file.getInputStream();
            File currentDir = new File("../src/main/resources/files");
            String path = currentDir.getAbsolutePath();
            fileLocation = path.replace("\\..", "") + "\\" + fileName;
            FileOutputStream fos = new FileOutputStream(fileLocation);
            int ch = 0;
            while ((ch = inputStream.read()) != -1) {
                fos.write(ch);
            }
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fillExcelMap(fileName);

        System.out.println("excel map: " + excelMap);

        model.addAttribute("message", "File: " + file.getOriginalFilename() + " has been uploaded successfully");

        return "index";
    }

    public void fillExcelMap(String fileName){
        try {
            FileInputStream fis = new FileInputStream(new File(fileName));
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            int lastRow = sheet.getLastRowNum();

            for (int i = 0; i <= lastRow; i++){
                Row row = sheet.getRow(i);

                Cell keyCell = row.getCell(0);
                Cell valueCell = row.getCell(1);

                String key = keyCell.getStringCellValue().trim();
                String value = valueCell.getStringCellValue().trim();

                excelMap.put(key, value);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
