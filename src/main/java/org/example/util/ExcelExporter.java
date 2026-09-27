package org.example.util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.model.TrainingApplication;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {

    private ExcelExporter() {
    }

    public static void export(
            List<TrainingApplication> applications,
            String fileName
    ) throws IOException {

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Заявки");

            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("ФИО");
            header.createCell(2).setCellValue("Email");
            header.createCell(3).setCellValue("Телефон");
            header.createCell(4).setCellValue("Курс");
            header.createCell(5).setCellValue("Дата");
            header.createCell(6).setCellValue("Статус");
            header.createCell(7).setCellValue("Тип обучения");
            header.createCell(8).setCellValue("Комментарий");

            int rowNumber = 1;

            for (TrainingApplication application : applications) {

                Row row = sheet.createRow(rowNumber++);

                row.createCell(0)
                        .setCellValue(application.getId());

                row.createCell(1)
                        .setCellValue(
                                application.getUser().getFullName()
                        );

                row.createCell(2)
                        .setCellValue(
                                application.getUser().getEmail()
                        );

                row.createCell(3)
                        .setCellValue(
                                application.getUser().getPhone()
                        );

                row.createCell(4)
                        .setCellValue(
                                application.getCourseName()
                        );

                row.createCell(5)
                        .setCellValue(
                                application.getApplicationDate().toString()
                        );

                row.createCell(6)
                        .setCellValue(
                                application.getStatus().name()
                        );

                row.createCell(7)
                        .setCellValue(
                                application.getEducationType().name()
                        );

                row.createCell(8)
                        .setCellValue(
                                application.getComment() == null
                                        ? ""
                                        : application.getComment()
                        );
            }

            for (int i = 0; i < 9; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream outputStream =
                         new FileOutputStream(fileName)) {

                workbook.write(outputStream);
            }
        }
    }
}