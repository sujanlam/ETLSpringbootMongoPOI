package com.etl.service;

import com.etl.entity.Employee;
import com.etl.repository.EmployeeRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository empRepository;

    public void saveEmployeesFromExcel(MultipartFile file) throws IOException {
        List<Employee> empList = new ArrayList<>();
        Workbook workBook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workBook.getSheetAt(0);

        Iterator<Row> rows = sheet.iterator();

        while (rows.hasNext()) {
            Row currentRow = rows.next();
            if (currentRow.getRowNum() == 0) {
                continue;
            }
            Employee employee = new Employee();
            employee.setId((long) currentRow.getCell(0).getNumericCellValue());
            employee.setName(currentRow.getCell(1).getStringCellValue());
            employee.setAddress(currentRow.getCell(2).getStringCellValue());
            employee.setSalary(currentRow.getCell(3).getNumericCellValue());
            employee.setDepartment(currentRow.getCell(4).getStringCellValue());
            Cell ageData = currentRow.getCell(5);
            if (ageData != null && ageData.getCellType() == CellType.NUMERIC) {
                employee.setAge((int) currentRow.getCell(5).getNumericCellValue());
            } else {
                continue;
            }
            empList.add(employee);
        }
        workBook.close();
        empRepository.saveAll(empList);
    }
}