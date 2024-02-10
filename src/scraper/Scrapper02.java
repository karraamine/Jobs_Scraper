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
import java.util.stream.Collectors;
import java.io.FileOutputStream;
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

public class Scrapper02 {
    private static String[] columns = {"Postname","Apply","Website","Publication Date","Application Deadline","Company Adress","Company website","Company Name","Company Description","Offer Description","Region","City","Sector","Occupation","Contract Type","Educational Level","Diploma","Experience","Required Profile","Soft Skills","Hard Skills","Language","Salary","Recommanded Competence","Social Advantages","Remote Work","Post(s)"};
    private static List<Offer> Data = new ArrayList<Offer>();

	public static void main(String[] args) throws IOException {
        try {
			int j;
			int k = 1;
			
			String sitename="rekrute.com";		
			for (int i = 1; i < 2; i++) {  //4 pages = 200 offres


				Document doc = Jsoup.connect("https://www.rekrute.com/offres.html?s=3&p="+i+"&o=1").get();
				Elements newsHeadlines = doc.select(".post-id");
				for (Element newHeadline : newsHeadlines) {
					String postname="";
					String entreprisename="";
					String salary="";
					String metier="";
					String region="";
					String poste="";
					String teletravail="";
					String city="";
					int posteNumber=1;
					String langue = "";
					String niveau_langue="";

					Elements PostnameElements = newHeadline.select(".titreJob");
					for (Element PostnameElement : PostnameElements) {
						String PostnameText = PostnameElement.text();

						// Trouver la position du tiret (-)
						int dashIndex = PostnameText.indexOf("|");

						// Extraire les mots avant le tiret (-)
						if (dashIndex != -1) {
							postname = PostnameText.substring(0, dashIndex).trim();
						}
					}

					Elements imgElements = newHeadline.select("div.col-sm-2.col-xs-12 a img.photo");

					for (Element imgElement : imgElements) {
						// Obtenez la valeur de l'attribut title
						entreprisename = imgElement.attr("title");
					}

					Elements DateDePublication = newHeadline.select(".date span");
					Elements lien = newHeadline.select(".section h2 a.titreJob");
					String href = lien.attr("href").trim();
					if (!href.isEmpty() && href.startsWith("/offre-emploi")) {
						String fullUrl = "https://www.rekrute.com" + href;


						System.out.println("Connecting to: " + fullUrl);
						Document emploi = Jsoup.connect(fullUrl).get();
						Elements Type_de_contrat = emploi.select("span[title=Type de contrat]");
						Elements contratElements = emploi.select("span.tagContrat[title=Télétravail]");
						for (Element contratElement : contratElements) {
							String contratText = contratElement.text();
							
							// Trouver la position du ":"
							int colonIndex = contratText.indexOf(":");
							
							// Extraire le texte après ":"
							if (colonIndex != -1 && colonIndex + 1 < contratText.length()) {
								teletravail = contratText.substring(colonIndex + 1).trim();
							}
						}

						//Element h1Element = emploi.selectFirst(".col-md-10 h1");
						Element h1Element = emploi.selectFirst(".col-md-12.col-sm-12.col-xs-12 h1, .col-md-10.col-sm-12.col-xs-12 h1");
						
						city = h1Element.ownText().split("-")[1].trim();

						Elements experience = emploi.select("li[title=Expérience requise]");
						Elements regionElements = emploi.select("li[title=Région]");
						for (Element regionElement : regionElements) {
							String regionText = regionElement.text();
						
							// Trouver la position du mot "sur"
							int surIndex = regionText.indexOf("sur");
							int posteIndex = regionText.indexOf("poste");

							if (posteIndex != -1) {
								poste = regionText.substring(0, posteIndex).trim();
								try {
									posteNumber = Integer.parseInt(poste);
								} catch (NumberFormatException e) {
									System.out.println("Cannot parse to an integer: " + poste);
								}
							}

							// Extraire les mots après "sur"
							if (surIndex != -1 && surIndex + 3 < regionText.length()) {
								region = regionText.substring(surIndex + 3);
							}
						}
						//Elements Competence = emploi.select("div.col-md-12.blc h2:contains(Traits de personnalité souhaités :) ~ *");
						Elements competenceElements = emploi.select("div.col-md-12.blc h2:contains(Traits de personnalité souhaités :) + p span.tagSkills");
						String competences = competenceElements.stream().map(element -> element.text().trim()).collect(Collectors.joining(","));

						Elements Entreprise = emploi.select("#recruiterDescription p");
						String entrepriseDescription=Entreprise.text();
						Company company = new Company();
						company.setCompanyAddress("");
						company.setCompanyWebsite("");
						company.setCompanyDescription(entrepriseDescription);
						company.setCompanyName(entreprisename);
						Langue langueObject = new Langue();
						langueObject.setLangueName(langue);
						langueObject.setLevel(niveau_langue);
						Elements secteur = emploi.select(".h2italic");
						Elements niveau_etude = emploi.select("li[title=\"Niveau d'étude et formation\"]");
						Elements descriptionPoste = emploi.select("div.col-md-12.blc h2:contains(Poste :) ~ *");
						Elements profilRecherche = emploi.select("div.col-md-12.blc h2:contains(Profil recherché :) ~ *");
						Elements appdeadline=emploi.select(".newjob b");
						
						Data.add(new Offer(sitename, postname, descriptionPoste.text(), posteNumber, DateDePublication.first().text(), entreprisename,
							entrepriseDescription, region, city, competences,
							Type_de_contrat.text(), teletravail, secteur.text(), experience.text(), salary, niveau_etude.text(), "", langueObject,
							"https://www.rekrute.com" + lien.attr("href"), appdeadline.text(), profilRecherche.text(),metier ,"","",null, company, langueObject));

						System.out.println("Offer " + k+ " succefully collected !");


						k++;
					}else {
						System.out.println("Skipping malformed URL: " + href);
					}
					try {
						Thread.sleep(2500);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}  

				}
				j = i ;
				System.out.println(j+" page(s) succefully collected ");

			}			
        	// SAVE(); 
        }catch (Exception e){
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

        // try {
        //     // Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/web-scraper", "root", "");
				// 		Connection con = DBConnection.getConnection();

        //     if (con != null) {
        //         System.out.println("Database is connected");

        //         // Inserting data into the database
        //         // DatabaseInsertion.insertDataIntoDatabase(con, Data);


        //         // Closing the database connection
        //         con.close();
        //     }
        // } catch (SQLException e) {
        //     e.printStackTrace();
        //     System.out.println("Error connecting to the database");
        // }		
    }
	
	// public static void SAVE() throws IOException {
	// 	Workbook workbook = new XSSFWorkbook();
	// 	try {
	// 		Sheet sheet = workbook.createSheet("rekrute");

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

	// 		FileOutputStream fileOut = new FileOutputStream("rekrute.xlsx");
	// 		workbook.write(fileOut);
	// 		fileOut.close();
	// 	} finally {
	// 		if (workbook != null) {
	// 			workbook.close();
	// 		}
	// 	}
	// }

}