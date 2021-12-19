package kz.atu.uit.afo.service.reportService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import kz.atu.uit.afo.domain.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class UserExcelReporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<User> listUsers;

    public UserExcelReporter(List<User> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "User ID", style);
        createCell(row, 1, "Username", style);
        createCell(row, 2, "E-mail", style);
        createCell(row, 3, "Full Name", style);
        createCell(row, 4, "IIN", style);
        createCell(row, 5, "phone", style);
        createCell(row, 6, "Place of work", style);
        createCell(row, 7, "Roles", style);
        createCell(row, 8, "Region", style);
        createCell(row, 9, "Enabled", style);
        createCell(row, 10, "CreatedAt", style);
        createCell(row, 11, "LastVisit", style);

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
        for (User user : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getId(), style);
            createCell(row, columnCount++, user.getUsername(), style);
            createCell(row, columnCount++, user.getEmail(), style);
            createCell(row, columnCount++, user.getFio(), style);
            createCell(row, columnCount++, "`"+user.getIin(), style);
            createCell(row, columnCount++, user.getPhone(), style);
            createCell(row, columnCount++, user.getPlaceOfWork(), style);
            createCell(row, columnCount++, user.getRoles().toString(), style);
            createCell(row, columnCount++, user.getRegion().getNameRegion(), style);
            createCell(row, columnCount++, user.isActive(), style);
            createCell(row, columnCount++, user.getCreatedAt(), style);
            createCell(row, columnCount++, user.getUpdatedAt(), style);


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
