/**
 * 
 */
package com.graymatter.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.text.DocumentException;

/**
 * @author
 *
 */

@Controller
@RequestMapping(value = "/reports")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReportsController {

	private DegreeService degreeService;
	private EventService eventService;
	private DegreeReportService degreeReportService;
	private EventReportService eventReportService;

	public ReportsController(DegreeService degreeService, DegreeReportService degreeReportService,
			EventService eventService, EventReportService eventReportService) {
		this.degreeService = degreeService;
		this.degreeReportService = degreeReportService;
		this.eventService = eventService;
		this.eventReportService = eventReportService;
	}

	@GetMapping(value = "/degrees/{id}")
	public void getDegreeById(@PathVariable("id") int id, HttpServletResponse response) throws DocumentException {
		System.out.println("id: " + id);

		Optional<Degree> optionalDegree = this.degreeService.findById(id);
		if (optionalDegree.isPresent()) {
			response.setContentType("application/pdf");
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			try {
				response.addHeader("Content-Disposition",
						"attachment; filename=" + optionalDegree.get().getName() + ".pdf");

				this.degreeReportService.generateReport(optionalDegree.get(), byteArrayOutputStream);
				IOUtils.copy(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), response.getOutputStream());
				response.getOutputStream().flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	@GetMapping(value = "/events/{id}")
	public void getEventById(@PathVariable("id") int id, HttpServletResponse response) throws DocumentException {
		System.out.println("id: " + id);

		Optional<Event> optionalEvent = this.eventService.findById(id);
		if (optionalEvent.isPresent()) {
			response.setContentType("application/pdf");
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			try {
				response.addHeader("Content-Disposition",
						"attachment; filename=" + optionalEvent.get().getName() + ".pdf");

				this.eventReportService.generateReport(optionalEvent.get(), byteArrayOutputStream);
				IOUtils.copy(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), response.getOutputStream());
				response.getOutputStream().flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

}
