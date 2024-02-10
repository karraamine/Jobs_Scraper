package scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import DB.DataBase;
import model.Company;
import model.Langue;
import model.Offer;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
// import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
// import org.apache.poi.ss.usermodel.Cell;
// import org.apache.poi.ss.usermodel.CellStyle;
// import org.apache.poi.ss.usermodel.Font;
// import org.apache.poi.ss.usermodel.IndexedColors;
// import org.apache.poi.ss.usermodel.Row;
// import org.apache.poi.ss.usermodel.Sheet;
// import org.apache.poi.ss.usermodel.Workbook;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Scrapper03 {
    // private static String[] columns = {"Postname","Apply","Website","Publication Date","Application Deadline","Company Adress","Company website","Company Name","Company Description","Offer Description","Region","City","Sector","Occupation","Contract Type","Educational Level","Diploma","Experience","Required Profile","Soft Skills","Hard Skills","Language","Salary","Recommanded Competence","Social Advantages","Remote Work","Post(s)"};
    private static List<Offer> Data = new ArrayList<Offer>();

    public static void main(String[] args) throws IOException {
        try {
            int j;
            int k = 1;

            for (int i = 1; i < 5; i++) {
				String region="";
				String teletravail="";
				String postname="";
				int posteNumber=1;
	            String sitename="m-job.ma";
	            String Competence = "";
                String appdeadline="";
                String entrepriseDescription ="";
                String descriptionPoste = "";
				String profilRecherche = "";
	            String secteur = "";
                String metier="";
	            String experience = "";
                String city="";
                String salary="";
                String Type_de_contrat="";
                String niveau_etude="";
                String langue="";
                String niveau_langue="Courant";

                Document doc = Jsoup.connect("https://www.m-job.ma/recherche?page="+i).get();
                Elements newsHeadlines = doc.select(".offer-box");
                for (Element newHeadline : newsHeadlines) {
					
					Elements PostnameElements = newHeadline.select(".offer-title");
					for (Element PostnameElement : PostnameElements) {
						postname = PostnameElement.text();
					}

					String entreprisename=null;
					Elements imgElements = newHeadline.select(".cat h5");

					for (Element imgElement : imgElements) {
						// Obtenez la valeur de l'attribut title
						entreprisename = imgElement.text();
					}


                    Element spanElement = newHeadline.select(".date-buttons span").first();
                    // Extract the text content of the span element
                    String textContent = spanElement.text();

                    //Extract the number and unit from the text (assuming the number is always at the beginning of the text)
                    String[] parts = textContent.split("\\s");
                    String number = parts[0];
                    String unit = parts.length > 1 ? parts[1] : "";
                    String DateDePublication="";
                    int numberofdays = 0;

                    try {
                        // Convert the string representation of the number to an integer
                        numberofdays = Integer.parseInt(number);

                        // Check the unit and adjust the date accordingly
                        LocalDate currentDate = LocalDate.now();
                        if (unit.equalsIgnoreCase("jours") || unit.equalsIgnoreCase("jour")) {
                            currentDate = currentDate.minusDays(numberofdays);
                        } else if (unit.equalsIgnoreCase("heures")) {
                            // Keep the current date (no change for hours)
                        } else {
                            System.out.println("Unknown time unit: " + unit);
                        }

                        // Format the dates if needed
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        // String formattedCurrentDate = currentDate.format(formatter);
                        DateDePublication = currentDate.format(formatter);

                        // Now you can use formattedCurrentDate and DateDePublication as needed
                    } catch (NumberFormatException e) {
                        System.out.println("Unable to parse the number as an integer");
                    }

                    Elements lien = newHeadline.select(".offer-title a");
                    String href = lien.attr("href").trim();
                    if (!href.isEmpty() && href.startsWith("https://www.m-job.ma/")) {
                        String fullUrl = href;

                        System.out.println("Connecting to: " + fullUrl);
	                    Document emploi = Jsoup.connect(fullUrl).get();

                        Element h3Element = emploi.select("li:has(span:contains(Type de contrat)) h3").first();
                        // Extract the text content of the h3 element
                        Type_de_contrat = h3Element.text().trim();

                        Element cityElement = emploi.select("div.location span").first();
                        // Extract the text content of the span element
                        city = cityElement.text().trim();

                        Element salaryElement = emploi.select("li:has(span:contains(Salaire:)) h3").first();
                        // Extract the text content of the h3 element
                        salary = salaryElement.text().trim();

                        // Extract content and store in variables
                        entrepriseDescription = emploi.select(".the-content > div:eq(1)").text().trim();
                        descriptionPoste = emploi.select(".the-content > div:eq(3)").text().trim();
						profilRecherche = emploi.select(".the-content > div:eq(5)").text().trim();
	                    secteur = emploi.select(".the-content > div:eq(7)").text().trim();
                        metier=emploi.select(".the-content > div:eq(9)").text().trim();
	                    experience = emploi.select(".the-content > div:eq(11)").text().trim();
						niveau_etude = emploi.select(".the-content > div:eq(13)").text().trim();
	                    langue = emploi.select(".the-content > div:eq(15)").text().trim();
                        
                        Langue langueObject = new Langue();
						langueObject.setLangueName(langue);
						langueObject.setLevel(niveau_langue);

                        Company company = new Company();
						company.setCompanyAddress("");
						company.setCompanyWebsite("");
						company.setCompanyDescription(entrepriseDescription);
						company.setCompanyName(entreprisename);

	                    Data.add(new Offer(sitename,postname,descriptionPoste, posteNumber,DateDePublication,entreprisename,
	                            entrepriseDescription, region, city,Competence,
	                            Type_de_contrat,teletravail,secteur,experience,salary,niveau_etude,"",langueObject,fullUrl,appdeadline,profilRecherche,metier,"","",null,company,langueObject));
	
	                    System.out.println("Emploi " + k+ " succefully collected !");
	
	
	                    k++;
                    }else {
                        System.out.println("Skipping malformed URL: " + href);
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }  

                }
                j = i ;
                System.out.println(j+" page(s) succefully collected ");

            }

            // SAVE();
        }
        catch (Exception e){
            e.printStackTrace();  // Print the stack trace for debugging
            System.out.println("Erreur de la connexion");
        }

        try {
            DataBase.insertDataIntoDatabase(Data);
            System.out.println("data inserted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error connecting to the database");
        }
    }

	// public static void SAVE() throws IOException {
	// 	Workbook workbook = new XSSFWorkbook();
	// 	try {
	// 		Sheet sheet = workbook.createSheet("m-job");

	// 		Font headerFont = workbook.createFont();
	// 		headerFont.setBold(true);
	// 		headerFont.setFontHeightInPoints((short) 14);
	// 		headerFont.setColor(IndexedColors.RED.getIndex());

	// 		CellStyle headerCellStyle = workbook.createCellStyle();
	// 		headerCellStyle.setFont(headerFont);

	// 		Row headerRow = sheet.createRow(0);

	// 		for (int i = 0; i < columns.length; i++) {
	// 			Cell cell = headerRow.createCell(i);
	// 			cell.setCellValue(columns[i]);
	// 			cell.setCellStyle(headerCellStyle);
	// 		}

	// 		int rowNum = 1;

	// 		for (Offer data : Data) {
	// 			Row row = sheet.createRow(rowNum++);
	// 			row.createCell(0).setCellValue(data.getTitle());
	// 			row.createCell(1).setCellValue(data.getOffreLink());
	// 			row.createCell(2).setCellValue(data.getSiteName());
	// 			row.createCell(3).setCellValue(data.getPublicationDate());
	// 			row.createCell(4).setCellValue(data.getApplyDate());
	// 			row.createCell(5).setCellValue(data.getCompany().getCompanyAddress()); // Assuming there's a method like getCompanyAddress()
	// 			row.createCell(6).setCellValue(data.getCompany().getCompanyWebsite()); // Assuming there's a method like getCompanyWebsite()
	// 			row.createCell(7).setCellValue(data.getCompany().getCompanyName());
	// 			row.createCell(8).setCellValue(data.getCompany().getCompanyDescription());
	// 			row.createCell(9).setCellValue(data.getOffreDescription());
	// 			row.createCell(10).setCellValue(data.getRegion());
	// 			row.createCell(11).setCellValue(data.getCity());
	// 			row.createCell(12).setCellValue(data.getSectors());
	// 			row.createCell(13).setCellValue(data.getOccupation());
	// 			row.createCell(14).setCellValue(data.getContractType());
	// 			row.createCell(15).setCellValue(data.getEducationLevel());
	// 			row.createCell(16).setCellValue(data.getDiploma());
	// 			row.createCell(17).setCellValue(data.getExperienceLevel());
	// 			row.createCell(18).setCellValue(data.getSearchedProfile());
	// 			row.createCell(19).setCellValue(data.getPersonalityTraits());
	// 			row.createCell(20).setCellValue(""); //for the hardskills
	// 			row.createCell(21).setCellValue(data.getLangue().getLangueName());
	// 			row.createCell(22).setCellValue(data.getSalary());
	// 			row.createCell(23).setCellValue(data.getRecommandedCompetence());
	// 			row.createCell(24).setCellValue(data.getSocialAdvantages());
	// 			row.createCell(25).setCellValue(data.getRemoteWork());
	// 			row.createCell(26).setCellValue(data.getNumberOfPosts());

	// 		}

	// 		FileOutputStream fileOut = new FileOutputStream("mjob.xlsx");
	// 		workbook.write(fileOut);
	// 		fileOut.close();
	// 	} finally {
	// 		if (workbook != null) {
	// 			workbook.close();
	// 		}
	// 	}
	// }
}
