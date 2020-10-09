package com.graymatter.service;

import java.io.OutputStream;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class EventReportService extends ReportService {

	public Document generateReport(Event event, OutputStream outputStream) throws DocumentException {
		Document document = new Document();
		PdfWriter.getInstance(document, outputStream);
		document.open();
		addMetaData(document, event);
		addPageBody(document, event);
		document.close();
		return document;
	}

	public void addMetaData(Document document, Event event) {
		document.addTitle("Report - " + event.getName());
		document.addAuthor("Jeewa Education");
		document.addCreator("Jeewa Education");
	}

	public void addPageBody(Document document, Event event) throws DocumentException {
		Paragraph para = new Paragraph();
		// one empty line
		addEmptyLine(para, 1);
		// add title
		Paragraph titlePara = new Paragraph("Event Report - " + event.getName(), CAT_FONT);
		titlePara.setAlignment(Element.ALIGN_CENTER);
		para.add(titlePara);

		addEmptyLine(para, 1);

		para.add(new Paragraph("Event report generated by Jeewa Education at " + LocalDateTime.now(), SMALL_BOLD_FONT));
		// add university details
		addUniversity(para, event.getUniversity());

		// add event details
		addEvent(para, event);

		document.add(para);
	}

	private void addEvent(Paragraph para, Event event) {
		addEmptyLine(para, 3);

		para.add(new Paragraph("Event Details: ", SUB_FONT));
		addEmptyLine(para, 1);
		para.add(new Paragraph("ID: " + event.getId(), SMALL_BOLD_FONT));
		addEmptyLine(para, 1);
		para.add(new Paragraph("Name: " + event.getName(), SMALL_BOLD_FONT));
		addEmptyLine(para, 1);
		para.add(new Paragraph("Start Date: " + event.getStartDate(), SMALL_BOLD_FONT));
		addEmptyLine(para, 1);
		para.add(new Paragraph("Start Time: " + event.getStartTime(), SMALL_BOLD_FONT));
		addEmptyLine(para, 1);
		para.add(new Paragraph("End Date: " + event.getEndDate(), SMALL_BOLD_FONT));
		addEmptyLine(para, 1);
		para.add(new Paragraph("End Time: " + event.getEndTime(), SMALL_BOLD_FONT));
		addEmptyLine(para, 1);
		para.add(new Paragraph("Venue: " + event.getVenue(), SMALL_BOLD_FONT));
		addEmptyLine(para, 1);
		para.add(new Paragraph("Description: " + event.getDescription(), SMALL_BOLD_FONT));
	}
}
