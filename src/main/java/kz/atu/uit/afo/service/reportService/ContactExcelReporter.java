package kz.atu.uit.afo.service.reportService;

import kz.atu.uit.afo.domain.Activity;
import kz.atu.uit.afo.domain.Contact;
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

public class ContactExcelReporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Contact> listContact;

    public ContactExcelReporter(List<Contact> listContact) {
        this.listContact = listContact;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("listContact");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Id", style);
        createCell(row, 1, "Фио", style);
        createCell(row, 2, "Статус", style);
        createCell(row, 3, "Пользователь", style);
        createCell(row, 4, "Дата создания", style);
        createCell(row, 5, "Дата обновления", style);

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
        for (Contact contact : listContact) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, contact.getId(), style);
            createCell(row, columnCount++, contact.getFio(), style);
            createCell(row, columnCount++, contact.isActve(), style);
            createCell(row, columnCount++, contact.getCareerСounselor().getFio(), style);
            createCell(row, columnCount++, contact.getCreatedAt(), style);
            createCell(row, columnCount++, contact.getUpdatedAt(), style);


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
