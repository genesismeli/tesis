package com.dh.apiDentalClinic.controller;

import com.dh.apiDentalClinic.DTO.ClinicalRecordDTO;
import com.dh.apiDentalClinic.DTO.DiagnosisDTO;
import com.dh.apiDentalClinic.DTO.MedicationDTO;
import com.dh.apiDentalClinic.DTO.PhysicalExamDTO;
import com.dh.apiDentalClinic.service.IClinicalRecordService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Tag(name = "Pdf", description = "Metodo para pdf")
@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    IClinicalRecordService iclinicalRecordService;

    @Autowired
    public PdfController(IClinicalRecordService clinicalRecordService) {
        this.iclinicalRecordService = clinicalRecordService;
    }

    @Operation(summary = "Find all clinical record")
    @GetMapping("/clinical-record/{recordId}")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Long recordId) {
        // Lógica para obtener los datos de la ficha clínica con el ID proporcionado
        // Puedes utilizar tu servicio de fichas clínicas para obtener los datos necesarios
        ClinicalRecordDTO clinicalRecord = iclinicalRecordService.findClinicalRecordById(recordId);

        // Obtener el contenido de la ficha clínica
        String clinicalRecordContent = generateClinicalRecordContent(clinicalRecord);

        // Generar el PDF
        byte[] pdfBytes = generatePdfBytes(clinicalRecordContent);

        // Configurar la respuesta HTTP para la descarga del PDF
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "ficha_clinica.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }


    private String generateClinicalRecordContent(ClinicalRecordDTO clinicalRecord) {
        StringBuilder contentBuilder = new StringBuilder();

        // Sección de Información General
        contentBuilder.append("Información General:\n");
        contentBuilder.append("Fecha: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(clinicalRecord.getDate())).append("\n");
        contentBuilder.append("Paciente: ").append(clinicalRecord.getPatient().getName()).append("\n\n");

        // Sección de Exámenes Físicos
        contentBuilder.append("Exámenes Físicos:\n");
        for (PhysicalExamDTO exam : clinicalRecord.getPhysicalExams()) {
            appendPhysicalExamData(contentBuilder, exam);
        }

        // Sección de Medicamentos
        contentBuilder.append("Medicamentos:\n");
        for (MedicationDTO medication : clinicalRecord.getMedications()) {
            appendMedicationData(contentBuilder, medication);
        }

        // Sección de Diagnósticos
        contentBuilder.append("Diagnósticos:\n");
        for (DiagnosisDTO diagnosis : clinicalRecord.getDiagnoses()) {
            appendDiagnosisData(contentBuilder, diagnosis);
        }

        return contentBuilder.toString();
    }

    private void appendPhysicalExamData(StringBuilder contentBuilder, PhysicalExamDTO exam) {
        contentBuilder.append("F. Cardíaca: ").append(exam.getHeartRate()).append("\n");
        contentBuilder.append("Saturación de Oxígeno: ").append(exam.getOxygenSaturation()).append("\n");
        contentBuilder.append("F. Respiratoria: ").append(exam.getRespiratoryRate()).append("\n");
        contentBuilder.append("P. Sistólica: ").append(exam.getSystolicPressure()).append("\n");
        contentBuilder.append("P. Diastólica: ").append(exam.getDiastolicPressure()).append("\n");
        contentBuilder.append("LPM: ").append(exam.getBeatsPerMinute()).append("\n");
        contentBuilder.append("Glucosa: ").append(exam.getGlucose()).append("\n\n");
    }

    private void appendMedicationData(StringBuilder contentBuilder, MedicationDTO medication) {
        contentBuilder.append("Nombre: ").append(medication.getMedicationName()).append("\n");
        contentBuilder.append("Concentración: ").append(medication.getConcentration()).append("\n");
        contentBuilder.append("Presentación: ").append(medication.getPresentation()).append("\n");
        contentBuilder.append("Nombre Comercial: ").append(medication.getTradeName()).append("\n\n");
    }

    private void appendDiagnosisData(StringBuilder contentBuilder, DiagnosisDTO diagnosis) {
        contentBuilder.append("Código: ").append(diagnosis.getCode()).append("\n");
        contentBuilder.append("Descripción: ").append(diagnosis.getDescription()).append("\n");
        contentBuilder.append("Estado: ").append(diagnosis.getStatus()).append("\n");
        contentBuilder.append("Notas: ").append(diagnosis.getNotes()).append("\n\n");
    }


    private byte[] generatePdfBytes(String content) {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(new Paragraph(content));
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return outputStream.toByteArray();
    }
    @Operation(summary = "recordId Pdf")
    @GetMapping("/clinical-record/{recordId}/pdf")
    public ResponseEntity<byte[]> generateClinicalRecordPdf(@PathVariable Long recordId) {
        ClinicalRecordDTO clinicalRecord = iclinicalRecordService.findClinicalRecordById(recordId);
        String clinicalRecordContent = generateClinicalRecordContent(clinicalRecord);
        byte[] pdfBytes = generatePdfBytes(clinicalRecordContent);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "ficha_clinica.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}
