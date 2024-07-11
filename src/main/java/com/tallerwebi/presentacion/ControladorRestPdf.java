package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.model.Reserva;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class ControladorRestPdf  {

    private  ServicioReserva servicioReserva;


    public ControladorRestPdf(ServicioReserva servicioReserva) {
        this.servicioReserva = servicioReserva;
    }

    @GetMapping("/generate-pdf")
    public void generatePdf(@RequestParam Long reservaID, HttpServletResponse response) throws IOException {

        PDDocument documento = new PDDocument();
        PDPage pagina = new PDPage();
        String nombrePdf = traerFechaComoNombre();
        documento.addPage(pagina);

        PDPageContentStream flujoContenidoPagina = new PDPageContentStream(documento, pagina);
        /*
        String rutaLogo = "E:/Taller 1/Sistema Web Garage/WebGarage/src/main/webapp/resources/core/img/navbar/logo.png";
        PDImageXObject logo = PDImageXObject.createFromFile(rutaLogo, documento);



        //PDImageXObject logo = PDImageXObject.createFromFile(rutaLogo, documento);
        flujoContenidoPagina.drawImage(logo, 20, 720, 50, 50);
*/
        flujoContenidoPagina.beginText();
        flujoContenidoPagina.setFont(PDType1Font.HELVETICA, 12);
        flujoContenidoPagina.setLeading(18f);
        flujoContenidoPagina.newLineAtOffset(100, 700);
        crearContenidoPdf(flujoContenidoPagina, reservaID, documento);
        flujoContenidoPagina.endText();
        flujoContenidoPagina.close();

        ByteArrayOutputStream flujoDeSalidaMemoria = new ByteArrayOutputStream();
        documento.save(flujoDeSalidaMemoria);
        documento.close();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=WebGarage_"+nombrePdf+".pdf");
        response.setContentLength(flujoDeSalidaMemoria.size());

        flujoDeSalidaMemoria.writeTo(response.getOutputStream());
        response.getOutputStream().flush();
    }

    private String traerFechaComoNombre() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return now.format(formatter);
    }

    private void crearContenidoPdf(PDPageContentStream flujoContenidoPagina, Long reservaID, PDDocument documento) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Reserva reserva = servicioReserva.buscarPorId(reservaID);
        if(reserva!=null){
            flujoContenidoPagina.showText("Detalles de la reserva N°: " +reserva.getId());
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.newLine();
           // flujoContenidoPagina.showText("Número de reserva: " +reserva.getId());
           // flujoContenidoPagina.newLine();
          //  flujoContenidoPagina.showText("Tipo de vehículo:..........." +reserva.getGarageTipoVehiculo().getTipoVehiculo().getIcono());
          //  flujoContenidoPagina.newLine();
            flujoContenidoPagina.showText("Estado: " +reserva.getEstado().getDescripcion());
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.showText("Fecha de transacción: " + now.format(formatter));
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.showText("Fecha de reserva: " + reserva.getDia());
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.showText("Horario de inicio: "+ reserva.getHorarioInicio());
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.showText("Horario de finalización: "+ reserva.getHorarioFin());
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.showText("Garage: "+ reserva.getGarage().getNombre());
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.showText("Mail del usuario: "+ reserva.getUsuario().getEmail() );
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.showText("Monto total: $ "+ reserva.getPrecio());
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.newLine();
            flujoContenidoPagina.showText("Gracias por reservar con nosotros.");
            /*
            PDImageXObject fotoGarage = PDImageXObject.createFromFile(new ClassPathResource("./core/img/garages" + reserva.getGarage().getRutaFoto()).getFile().getAbsolutePath(), documento);

           flujoContenidoPagina.drawImage(fotoGarage, 100, 400, 200, 100);
            String rutaFotoGarage = "E:/Taller 1/Sistema Web Garage/WebGarage/src/main/webapp/resources/core/img/garages/" + reserva.getGarage().getRutaFoto();
            PDImageXObject fotoGarage = PDImageXObject.createFromFile(rutaFotoGarage, documento);
            flujoContenidoPagina.drawImage(fotoGarage, 100, 400, 200, 100);*/

        } else {
            flujoContenidoPagina.showText("Hubo un error al imprimir la reserva, comuniquese con asistencia tecnica");
        }
    }


}
