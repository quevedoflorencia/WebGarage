package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.model.Reserva;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class ControladorRestPdf {

    private ServicioReserva servicioReserva;

    public ControladorRestPdf(ServicioReserva servicioReserva) {
        this.servicioReserva = servicioReserva;
    }

    @GetMapping("/generate-pdf")
    public void generatePdf(@RequestParam Long reservaID, HttpServletResponse response) throws IOException {


        // Crear un nuevo documento PDF
         PDDocument documento = new PDDocument();
        PDPage pagina = new PDPage();
        documento.addPage(pagina);

        // Crear un flujo de contenido para la página
        PDPageContentStream flujoContenidoPagina = new PDPageContentStream(documento, pagina);

        // Escribir contenido en el flujo de contenido
        flujoContenidoPagina.beginText();
        flujoContenidoPagina.setFont(PDType1Font.HELVETICA, 12);
        flujoContenidoPagina.setLeading(18f);
        flujoContenidoPagina.newLineAtOffset(100, 700);
        crearContenidoPdf(flujoContenidoPagina, reservaID);
        flujoContenidoPagina.endText();
        flujoContenidoPagina.close();

        // Guardar el documento en memoria
        ByteArrayOutputStream flujoDeSalidaMemoria = new ByteArrayOutputStream();
        documento.save(flujoDeSalidaMemoria);
        documento.close();

        // Configurar la respuesta HTTP para descargar el PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=ReservaWebGarage.pdf");
        response.setContentLength(flujoDeSalidaMemoria.size());

        // Escribir el PDF en la respuesta
        flujoDeSalidaMemoria.writeTo(response.getOutputStream());
        response.getOutputStream().flush();
    }

    private void crearContenidoPdf(PDPageContentStream flujoContenidoPagina, Long reservaID) throws IOException {
        Reserva reserva = servicioReserva.buscarPorId(reservaID);
        if(reserva!=null){
            flujoContenidoPagina.showText("    Detalles de la reserva:     ");
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.showText("Numero de reserva:..........." +reserva.getId());
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.showText("Fecha:......................." + reserva.getDia());
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.showText("Horario de inicio:..........."+ reserva.getHorarioInicio());
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.showText("Horario de finalizacion:....."+ reserva.getHorarioFin());
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.showText("Garage:......................"+ reserva.getGarage().getNombre());
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.showText("Mail del usuario:............"+ reserva.getUsuario().getEmail() );
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.showText("Total de la reserva:.........$"+ reserva.getPrecio());
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.showText("                                 Gracias por reservar con nosotros");

        }else {
            flujoContenidoPagina.showText("Hubo un error al imprimir la reserva, comuniquese con asistencia tecnica");
        }
    }
}
