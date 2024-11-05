package org.tyss.flatworld.genericutility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {

	/**
	 * This method retrieves a cell value based on a unique test case ID and a header value.
	 * 
	 * @param excelPath   - Path to the Excel file
	 * @param sheetName   - Name of the sheet
	 * @param uniqueData  - Unique identifier for the row (test case ID)
	 * @param header      - Header name to identify the column
	 * @return Value from the specified cell
	 * @throws EncryptedDocumentException, IOException
	 */
	public String getCellDataBasedOnTcIdAndHeader(String excelPath, String sheetName,
			String uniqueData, String header) throws EncryptedDocumentException, IOException {
		String value = "";
		FileInputStream fis = new FileInputStream(excelPath);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		int rowCount = sheet.getPhysicalNumberOfRows();

		for (int i = 1; i < rowCount; i++) {
			String actualUniqueData = sheet.getRow(i).getCell(0).getStringCellValue();
			if (actualUniqueData.equalsIgnoreCase(uniqueData)) {
				int columnCount = sheet.getRow(i).getPhysicalNumberOfCells();
				for (int j = 1; j < columnCount; j++) {
					String actualHeader = sheet.getRow(0).getCell(j).getStringCellValue();
					if (actualHeader.equalsIgnoreCase(header)) {
						value = sheet.getRow(i).getCell(j).getStringCellValue();
						break;
					}
				}
				if (!value.isEmpty()) {
					break;
				}
			}
		}
		workbook.close();
		fis.close();

		if (value.isEmpty()) {
			System.out.println("No matching data found for Test Case ID: " + uniqueData + " and Header: " + header);
		}

		return value;
	}

	/**
	 * This method retrieves the entire row of data based on a unique test case ID.
	 * 
	 * @param excelPath     - Path to the Excel file
	 * @param sheetName     - Name of the sheet
	 * @param testCaseName  - Unique identifier for the row (test case ID)
	 * @return A Map containing header-value pairs for the entire row
	 * @throws EncryptedDocumentException, IOException
	 */
	public Map<String, String> getEntireTcDataBasedOnTcId(String excelPath, String sheetName,
			String testCaseName) throws EncryptedDocumentException, IOException {
		HashMap<String, String> map = new HashMap<>();
		FileInputStream fis = new FileInputStream(excelPath);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		String value = "";
		String actualTestCaseName = "";
		String actualKey = "";

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			try {
				actualTestCaseName = sheet.getRow(i).getCell(0).getStringCellValue();
			} catch (Exception e) {
				System.out.println("Error reading test case name at row: " + i);
			}

			if (actualTestCaseName.equalsIgnoreCase(testCaseName)) {
				for (int j = 0; j <= sheet.getRow(0).getLastCellNum(); j++) {
					try {
						actualKey = sheet.getRow(0).getCell(j).toString();
						try {
							value = sheet.getRow(i).getCell(j).toString();
						} catch (Exception e) {
							System.out.println("No data found in cell (" + i + ", " + j + ")");
						}
						map.put(actualKey.trim(), value.trim());
					} catch (Exception e) {
						System.out.println("Error reading data for key: " + actualKey + " at column: " + j);
					}
				}
				break;
			}
		}
		workbook.close();
		fis.close();

		if (map.isEmpty()) {
			System.out.println("No data found for Test Case ID: " + testCaseName);
		} else {
			System.out.println("Data retrieved for Test Case ID: " + testCaseName + " - " + map.toString());
		}
		return map;
	}

	/**
	 * This method writes data to a specific cell based on a test case ID and a header.
	 * 
	 * @param excelPath     - Path to the Excel file
	 * @param sheetName     - Name of the sheet
	 * @param testCaseName  - Unique identifier for the row (test case ID)
	 * @param requiredKey   - Header name to identify the column
	 * @param data          - Data to be written
	 * @return The previous value in the cell before updating
	 * @throws EncryptedDocumentException, IOException
	 */
	public String writeDataToCellBasedOnTcIdAndHeader(String excelPath, String sheetName, String testCaseName,
			String requiredKey, String data) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(excelPath);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		String value = "";
		String actualTestCaseName = "";
		String actualKey = "";
		boolean flag = false;

		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			try {
				actualTestCaseName = sheet.getRow(i).getCell(0).getStringCellValue();
			} catch (Exception e) {
				System.out.println("Error reading test case name at row: " + i);
			}

			if (actualTestCaseName.equalsIgnoreCase(testCaseName)) {
				for (int j = 1; j <= sheet.getRow(0).getLastCellNum(); j++) {
					try {
						actualKey = sheet.getRow(0).getCell(j).getStringCellValue();
					} catch (Exception e) {
						System.out.println("Error reading header at column: " + j);
					}

					if (actualKey.equalsIgnoreCase(requiredKey)) {
						try {
							value = sheet.getRow(i).getCell(j).toString();
							sheet.getRow(i).createCell(j).setCellValue(data);
							System.out.println("Data written to cell (" + i + ", " + j + "): " + data);
						} catch (Exception e) {
							System.out.println("Failed to write data to cell (" + i + ", " + j + ")");
						}
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				break;
			}
		}

		FileOutputStream fos = new FileOutputStream(excelPath);
		workbook.write(fos);
		fos.close();
		workbook.close();

		if (flag) {
			System.out.println("Data successfully updated for Test Case ID: " + testCaseName + " and Header: " + requiredKey);
		} else {
			System.out.println("Failed to find matching Test Case ID: " + testCaseName + " or Header: " + requiredKey);
		}
		return value;
	}
	
	
	
	/**
	 * This method retrieves the entire Page verification data.
	 * 
	 * @param excelPath     - Path to the Excel file
	 * @param sheetName     - Name of the sheet
	 * @return A Map containing header-value pairs for the entire row
	 * @throws EncryptedDocumentException, IOException
	 */
	public Map<String, String> getPageVerificationData(String excelPath, String sheetName) throws EncryptedDocumentException, IOException {
		Map<String, String> map = new HashMap<>();
		FileInputStream fis = new FileInputStream(excelPath);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			map.put(sheet.getRow(i).getCell(0).toString(), sheet.getRow(i).getCell(1).toString());
			
		}
		workbook.close();
		fis.close();

		if (map.isEmpty()) {
			System.out.println("No data found.");
		} else {
			System.out.println("Page verification Data retrieved: - " + map.toString());
		}
		return map;
	}
	
    	public void setDataToExcel(String path,String sheetName, int row, int cell, String data) throws EncryptedDocumentException, IOException {
	
		FileInputStream fis = new FileInputStream(path);

		Workbook workbook = WorkbookFactory.create(fis);

		Sheet sheet = workbook.getSheet(sheetName);
		
		Row rowNum = sheet.getRow(row);
		if(rowNum==null) {
			sheet.createRow(row);
		}
		Cell cellNum =rowNum.getCell(cell);
		if(cellNum==null) {
			rowNum.createCell(cell);
		}
		cellNum.setCellValue(data);
		
		FileOutputStream fos=new FileOutputStream(path);
		workbook.write(fos);
		workbook.close();

	}
}
