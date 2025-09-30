package com.mixto.crudmixto.util;

import java.awt.Color;
import java.io.IOException;      
import java.util.List; // <-- ¡ESTA ES LA LÍNEA CLAVE!

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mixto.crudmixto.entity.Proyecto;

import jakarta.servlet.http.HttpServletResponse;

public class ProyectoPDFExporter {

    private List<Proyecto> listProyectos;

    public ProyectoPDFExporter(List<Proyecto> listProyectos) {
        this.listProyectos = listProyectos;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        
        // Uso de java.awt.Color (ahora importado)
        cell.setBackgroundColor(Color.BLUE); 
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE); 

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Nombre", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Descripción", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("ID Empleado Asignado", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Proyecto proyecto : listProyectos) {
            
            // Conversiones de Long a String.
            table.addCell(proyecto.getId() != null ? String.valueOf(proyecto.getId()) : "N/A"); 
            table.addCell(proyecto.getNombre());
            table.addCell(proyecto.getDescripcion());
            table.addCell(proyecto.getEmpleadoId() != null ? String.valueOf(proyecto.getEmpleadoId()) : "N/A");
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        
        // Uso de java.awt.Color
        font.setColor(Color.BLUE); 

        Paragraph p = new Paragraph("Lista de Proyectos", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(4); 
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 4.0f, 3.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);
        document.close();
    }
}