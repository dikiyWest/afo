package kz.atu.uit.afo.service.reportService;

import kz.atu.uit.afo.domain.Enrollee;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class EnrolleeExcelReporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Enrollee> listEnrolles;

    public EnrolleeExcelReporter(List<Enrollee> listEnrolles) {
        this.listEnrolles = listEnrolles;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("listEnrolles");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Id", style);
        createCell(row, 1, "ФИО", style);
        createCell(row, 2, "ИИН", style);
        createCell(row, 3, "Учебное заведение", style);
        createCell(row, 4, "Образовательная программа", style);
        createCell(row, 5, "Регион", style);
        createCell(row, 6, "Пользователь", style);
        createCell(row, 7, "Дата создания", style);
        createCell(row, 8, "Дата обновления", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        }else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else if (value instanceof LocalDateTime) {
            cell.setCellValue(String.valueOf((LocalDateTime) value));
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Enrollee enrollee : listEnrolles) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, enrollee.getId(), style);
            createCell(row, columnCount++, enrollee.getFio(), style);
            createCell(row, columnCount++, "`"+enrollee.getIin(), style);
            createCell(row, columnCount++, enrollee.getUniversity(), style);
            createCell(row, columnCount++, enrollee.getEducationProgramm().getNameEducation(), style);
            createCell(row, columnCount++, enrollee.getRegion().getNameRegion(), style);
            createCell(row, columnCount++, enrollee.getCareerСounselor().getFio(), style);
            createCell(row, columnCount++, enrollee.getCreatedAt(), style);
            createCell(row, columnCount++, enrollee.getUpdatedAt(), style);


        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
