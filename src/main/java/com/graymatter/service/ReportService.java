/**
 * 
 */
package com.graymatter.service;

import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;

/**
 * @author
 *
 */
public class ReportService {
	public static final Font CAT_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	public static final Font SUB_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	public static final Font SMALL_BOLD_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	public void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	public void addUniversity(Paragraph para, University university) {
		addEmptyLine(para, 3);

		para.add(new Paragraph("University Details: ", SUB_FONT));
		addEmptyLine(para, 1);
		para.add(new Paragraph("Name: " + university.getName(), SMALL_BOLD_FONT));
		addEmptyLine(para, 1);
		para.add(new Paragraph("University ID: " + university.getId(), SMALL_BOLD_FONT));
		addEmptyLine(para, 1);
		para.add(new Paragraph("Country: " + university.getCountry(), SMALL_BOLD_FONT));
		addEmptyLine(para, 1);
		para.add(new Paragraph("University Description: " + university.getDescription(), SMALL_BOLD_FONT));
	}

}
