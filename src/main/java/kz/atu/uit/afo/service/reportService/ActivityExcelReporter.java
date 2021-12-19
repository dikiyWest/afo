package kz.atu.uit.afo.service.reportService;

import kz.atu.uit.afo.domain.Activity;
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

public class ActivityExcelReporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Activity> listActivity;

    public ActivityExcelReporter(List<Activity> listActivity) {
        this.listActivity = listActivity;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("listActivity");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Id", style);
        createCell(row, 1, "Дата и время мероприятия", style);
        createCell(row, 2, "Наименование мероприятия", style);
        createCell(row, 3, "Формат", style);
        createCell(row, 4, "Учебное заведение", style);
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
        for (Activity activity : listActivity) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, activity.getId(), style);
            createCell(row, columnCount++, activity.getDateActivity(), style);
            createCell(row, columnCount++, activity.getNameActivity(), style);
            createCell(row, columnCount++, activity.getFormatActivity(), style);
            createCell(row, columnCount++, activity.getPlaceActivity(), style);
            createCell(row, columnCount++, activity.getRegion().getNameRegion(), style);
            createCell(row, columnCount++, activity.getAuthor().getFio(), style);
            createCell(row, columnCount++, activity.getCreatedAt(), style);
            createCell(row, columnCount++, activity.getUpdatedAt(), style);


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
