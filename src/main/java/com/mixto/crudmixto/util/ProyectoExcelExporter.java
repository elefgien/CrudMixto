package com.mixto.crudmixto.util;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mixto.crudmixto.entity.Proyecto;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class ProyectoExcelExporter {
    
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Proyecto> listProyectos;

    public ProyectoExcelExporter(List<Proyecto> listProyectos) {
        this.listProyectos = listProyectos;
        workbook = new XSSFWorkbook();
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else {
            cell.setCellValue(value.toString()); // Convertir ID a String
        }
        cell.setCellStyle(style);
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Proyectos");
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "Nombre", style);
        createCell(row, 2, "Descripción", style);
        createCell(row, 3, "ID Empleado Asignado", style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Proyecto proyecto : listProyectos) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            // Asegúrate de que los getters coincidan con tu entidad Proyecto.java
            createCell(row, columnCount++, proyecto.getId() != null ? proyecto.getId() : "N/A", style);
            createCell(row, columnCount++, proyecto.getNombre(), style);
            createCell(row, columnCount++, proyecto.getDescripcion(), style);
            createCell(row, columnCount++, proyecto.getEmpleadoId(), style);
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