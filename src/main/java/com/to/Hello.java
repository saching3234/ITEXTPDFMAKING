package com.to;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.attribute.DosFileAttributes;
import java.text.DecimalFormat;

import javax.print.attribute.standard.PrinterMakeAndModel;

import com.itextpdf.io.IOException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

public class Hello implements IEventHandler { 

	Document document;
	PdfDocument pdfDocument;
	int eventCounter;
	DecimalFormat decimalFormat;
	void generatePDF() {
		//document path
		String path="D:\\Invoice.pdf";
		try {
		PdfWriter pdfWriter;
		pdfWriter = new PdfWriter(path);
		
		pdfDocument=new PdfDocument(pdfWriter);
		document=new Document(pdfDocument);
		pdfDocument.addEventHandler(PdfDocumentEvent.START_PAGE,  this);
		document.setMargins(100f, 30f, 30f, 30f);
		
		pdfDocument.addNewPage();

		 float itemInfoColWith[]= {140,120,170,50,50};
		 Table itemInfoTable=new Table(itemInfoColWith);
         //add the table headers here table headers will repeat on each new page
		 itemInfoTable.addHeaderCell(new Cell().add("Transaction Date").setBold().setBackgroundColor(Color.BLUE).setFontColor(Color.WHITE));
		 itemInfoTable.addHeaderCell(new Cell().add("Particulars").setBold().setBackgroundColor(Color.BLUE).setFontColor(Color.WHITE));
		 itemInfoTable.addHeaderCell(new Cell().add("Description").setBold().setBackgroundColor(Color.BLUE).setFontColor(Color.WHITE));
		 itemInfoTable.addHeaderCell(new Cell().add("Debit").setBold().setBackgroundColor(Color.BLUE).setFontColor(Color.WHITE));
		 itemInfoTable.addHeaderCell(new Cell().add("Credit").setBold().setBackgroundColor(Color.BLUE).setFontColor(Color.WHITE));
		 //formatter for fomat the transaction values
		 decimalFormat=new DecimalFormat("#,##0.00");
		 //add the transactio details
		 Double double1=0.0;
		 for(int i=0;i<100;i++) {
			 itemInfoTable.addCell(new Cell().add("03/03/2001"));
			 itemInfoTable.addCell(new Cell().add("Cash withdrawal"));
			 itemInfoTable.addCell(new Cell().add("Cahs withdrawn from ATM"));
			 itemInfoTable.addCell(new Cell().add(decimalFormat.format(484714)).setTextAlignment(TextAlignment.RIGHT));
			 itemInfoTable.addCell(new Cell().add(decimalFormat.format(double1)).setTextAlignment(TextAlignment.RIGHT));
			 
		 }
		
		 float colmWidth[]= {300,300};
         Table globalTable =new Table(colmWidth);
         
         float colmWidthForPersonalInfo[]= {50,50};
         Table personalInfoTable =new Table(colmWidthForPersonalInfo);
         personalInfoTable.addHeaderCell(new Cell().add("Field Name").setBold().setBackgroundColor(Color.PINK).setFontColor(Color.WHITE));
         personalInfoTable.addHeaderCell(new Cell().add("Field Value").setBold().setBackgroundColor(Color.PINK).setFontColor(Color.WHITE));
         personalInfoTable.addCell(new Cell().add("Customer Name"));
         personalInfoTable.addCell(new Cell().add("Sachin Gawade"));
         personalInfoTable.addCell(new Cell().add("Customer Address"));
         personalInfoTable.addCell(new Cell().add("Patas Daund-412219"));
         globalTable.addCell(personalInfoTable);
         
         float colmWidthForFinanceInfo[]= {50,50};
         Table FinanceInFoTable =new Table(colmWidthForFinanceInfo);
         FinanceInFoTable.addHeaderCell(new Cell().add("Finance Field Name").setBold().setBackgroundColor(Color.PINK).setFontColor(Color.WHITE));
         FinanceInFoTable.addHeaderCell(new Cell().add("Finance Field Value").setBold().setBackgroundColor(Color.PINK).setFontColor(Color.WHITE));
         for(int i=0;i<5;i++) {
         FinanceInFoTable.addCell(new Cell().add("Loan No"));
         FinanceInFoTable.addCell(new Cell().add("123456"));
         FinanceInFoTable.addCell(new Cell().add("Loan Type"));
         FinanceInFoTable.addCell(new Cell().add("Personal Loan"));
         }
         globalTable.addCell(FinanceInFoTable.setHorizontalAlignment(HorizontalAlignment.RIGHT));    
         document.add(globalTable);
         eventCounter=1;
         document.add(new AreaBreak());
         
		//add the table in the document
		document.add(itemInfoTable);
		//make the counter to 2 so that table header will not print for other pages
		eventCounter=2;
		document.close();
		pdfDocument.close();
		pdfWriter.close();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception occured because :");
			e.printStackTrace();
		}
		
		
	}

	//event hadler method for handling the page events like star page , end page etc
	public void handleEvent(Event event) {
		System.out.println("event occured :"+event.getType());
		System.out.println("total pages :"+pdfDocument.getNumberOfPages());
		//String imgPath;
		try {
		//imgPath = "src/main/resources/header.png";
		InputStream iStream=Hello.class.getClassLoader().getResourceAsStream("header.png");
		ImageData imageData=ImageDataFactory.create(iStream.readAllBytes());
		//ImageData imageData=ImageDataFactory.create(imgPath);
		Image image=new Image(imageData);
		image.setAutoScale(true);
		image.setHorizontalAlignment(HorizontalAlignment.CENTER);
		image.setFixedPosition(25, 770);
		document.add(image);
		
		
		if(eventCounter==1) {
			final String tableDetails="Loan Transaction Details between 12/05/2000 TO 23/05/2023";
		    document.add(new Paragraph(tableDetails).setFontColor(new DeviceRgb(29, 123, 178)).setPaddingTop(2).setPaddingBottom(3) .setBold().setBackgroundColor(new DeviceRgb(232, 234, 241)).setTextAlignment(TextAlignment.CENTER).setFixedPosition(27, 745, 535));
		}
		
		document.add(new Paragraph("________________________________________________________________________________________________").setHorizontalAlignment(HorizontalAlignment.CENTER) .setFixedPosition(28, 10, 700).setFontColor(Color.GRAY) .setFontSize(10));;
		
		} catch (Exception e) {
			System.out.println("The exception occured in the hadle event method reason: "+e.getMessage());
		} 

	}
	
	public static void main(String[] args) {

		Hello hello=new Hello();
		hello.generatePDF();
		System.out.println("Pdf generated successfully check the d drive");
	}
	
	
}