package com.example.excelReaderFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.EmailData;
import com.example.entity.SalaryFields;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelReader {

	public static List<com.example.entity.EmailData> readExcelData(MultipartFile eFile) throws IOException {
		List<com.example.entity.EmailData> emailDataList = new ArrayList<>();
		Workbook workbook = WorkbookFactory.create(eFile.getInputStream());
		Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				String name = row.getCell(0).getStringCellValue();
				String email = row.getCell(1).getStringCellValue();

				String location = row.getCell(2).getStringCellValue();

				emailDataList.add(new EmailData(name, email));
			}
		}
		workbook.close();
		return emailDataList;
	}

	public static List<SalaryFields> salaryFields(MultipartFile salaryFile)
			throws EncryptedDocumentException, IOException {

		List<SalaryFields> list = new ArrayList<>();
		Workbook workbook = WorkbookFactory.create(salaryFile.getInputStream());

		Sheet sheetAt = workbook.getSheetAt(0);

		for (int i = 1; i <=sheetAt.getLastRowNum(); i++) {

			log.info("In side loop ....!");
			Row row = sheetAt.getRow(i);

			if (row != null) {
                     log.info("getting data from excel sheet...!");
				String name = row.getCell(0).getStringCellValue();

				String email = row.getCell(1).getStringCellValue();
				String monthOfYear = row.getCell(2).getStringCellValue();
				double salary = row.getCell(3).getNumericCellValue();

				list.add(new SalaryFields(name, email, monthOfYear, salary));
			}

		}
		workbook.close();
         log.info("excel file reader output :{}" ,list);
		return list;
	}

}
