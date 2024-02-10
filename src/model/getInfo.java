package model;

// import java.io.FileOutputStream;
// import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// import java.util.Scanner;


// import org.apache.poi.ss.usermodel.*;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import DB.DBConnection;
// import DB.DataBase;

public class getInfo {
    public String condSector = "informatique";
    public String condCity = "tanger";
    public String condContractType = "cdi";
    public List<String> sitesSelected = Arrays.asList(
            "Emploi.ma"
        );
    
    public List<returnResult> resultSet = new ArrayList<returnResult>();
    
    public List<returnResult>  findOffres(String condSector, String condCity, String condContractType, List<String> sitesSelected) throws SQLException {
        // String sql;
        // ResultSet returnResult = null;
        ResultSet rs;
        // int number = 0;
        String condCompanyName = "";
        String condRemoteWork = "";
        // String sqlRequest;

        // Workbook workbook = new XSSFWorkbook();
        // Sheet sheet = workbook.createSheet("Offre Data");
        // int rowNum = 0;
        // Row row = sheet.createRow(rowNum++);
        // row.createCell(0).setCellValue("Title");
        // row.createCell(1).setCellValue("OffreLink");
        // row.createCell(2).setCellValue("SiteName");
        // row.createCell(3).setCellValue("DatePublication");
        // row.createCell(4).setCellValue("ApplyDate"); 
        // row.createCell(5).setCellValue("CompanyAdresse"); 
        // row.createCell(6).setCellValue("WebSiteCompany");
        // row.createCell(7).setCellValue("CompanyName");
        // row.createCell(8).setCellValue("ComapanyDescription");
        // row.createCell(9).setCellValue("OffreDescription"); 
        // row.createCell(10).setCellValue("Region");
        // row.createCell(11).setCellValue("City");
        // row.createCell(12).setCellValue("Sectors");
        // row.createCell(13).setCellValue("Occupation");
        // row.createCell(14).setCellValue("ContractType");
        // row.createCell(15).setCellValue("EducationLevel");
        // row.createCell(16).setCellValue("Diploma"); 
        // row.createCell(17).setCellValue("ExperienceLevel");
        // row.createCell(18).setCellValue("SearchedProfile");
        // row.createCell(19).setCellValue("PersonalityTraits"); 
        // row.createCell(20).setCellValue("RecommandedCompetence"); 
        // row.createCell(21).setCellValue("Salary"); 
        // row.createCell(22).setCellValue("SocialAdvantages"); 
        // row.createCell(23).setCellValue("RemoteWork"); 
        // row.createCell(24).setCellValue("NumberOfPosts");

        // Workbook workbookSkill = new XSSFWorkbook();
        // Sheet sheetSkill = workbookSkill.createSheet("Skill Data");
        // int rowNumSkill = 0;
        // Row rowSkill = sheetSkill.createRow(rowNumSkill++);
        // rowSkill.createCell(0).setCellValue("skillName");
        // rowSkill.createCell(1).setCellValue("skillType");
        // rowSkill.createCell(2).setCellValue("Repeated");

        // Workbook workbookLangue = new XSSFWorkbook();
        // Sheet sheetLangue = workbookLangue.createSheet("Langue Data");
        // int rowNumLangue = 0;
        // Row rowLangue = sheetLangue.createRow(rowNumLangue++);
        // rowLangue.createCell(0).setCellValue("langueName");
        // rowLangue.createCell(1).setCellValue("level");
        // rowLangue.createCell(2).setCellValue("Repeated");

        // List<String> sitesSelected = Arrays.asList(
        //     "Emploi.ma"
        // );
        try (Connection con = DBConnection.getConnection();
        Statement smt = con.createStatement()) {
            List<String> id_offres = new ArrayList<String>();
            StringBuilder sqlRequestAll = new StringBuilder();
            sqlRequestAll.append("SELECT * FROM Offre o JOIN Company c ON c.id_offre = o.id_offre ");
            sqlRequestAll.append("WHERE o.id_offre IN (SELECT id_offre FROM Offre WHERE LOWER(Region) LIKE ? OR LOWER(City) LIKE ?) ");
            sqlRequestAll.append("AND o.id_offre IN (SELECT id_offre FROM Offre WHERE LOWER(Sectors) LIKE ? OR LOWER(Occupation) LIKE ?) ");
            sqlRequestAll.append("AND o.id_offre IN (SELECT id_offre FROM Offre WHERE LOWER(ContractType) LIKE ?) ");
            sqlRequestAll.append("AND LOWER(RemoteWork) LIKE ? AND o.SiteName IN (");
        
            for (int i = 0; i < sitesSelected.size(); i++) {
                sqlRequestAll.append("?");
                if (i < sitesSelected.size() - 1) {
                    sqlRequestAll.append(", ");
                }
            }
        
            sqlRequestAll.append(") AND c.CompanyName LIKE ?");
        
            try (PreparedStatement pstmt = con.prepareStatement(sqlRequestAll.toString())) {
                pstmt.setString(1, "%" + condCity + "%");
                pstmt.setString(2, "%" + condCity + "%");
                pstmt.setString(3, "%" + condSector + "%");
                pstmt.setString(4, "%" + condSector + "%");
                pstmt.setString(5, "%" + condContractType + "%");
                pstmt.setString(6, "%" + condRemoteWork + "%");
        
                int parameterIndex = 7;
                for (String site : sitesSelected) {
                    pstmt.setString(parameterIndex++, site);
                }
        
                pstmt.setString(parameterIndex, "%" + condCompanyName + "%");
        rs = pstmt.executeQuery();
        // returnResult = pstmt.executeQuery();
                
        while (rs.next()) {

            id_offres.add(rs.getString("id_offre"));
            String Title = rs.getString("Title");
            String CompanyName = rs.getString("CompanyName");
            String PublicationDate = rs.getString("PublicationDate");
            String SiteName = rs.getString("SiteName");
            // ----------
            resultSet.add(new returnResult(Title, CompanyName, PublicationDate, SiteName));
            // ----------
            // String OffreLink = rs.getString("OffreLink");
            // String ApplyDate = rs.getString("ApplyDate");
            // String OffreDescription = rs.getString("OffreDescription");
            // String Region = rs.getString("Region");
            // String City = rs.getString("City");
            // String Sectors = rs.getString("Sectors");
            // String Occupation = rs.getString("Occupation");
            // String ContractType = rs.getString("ContractType");
            // String EducationLevel = rs.getString("EducationLevel");
            // String Diploma = rs.getString("Diploma");
            // String ExperienceLevel = rs.getString("ExperienceLevel");
            // String SearchedProfile = rs.getString("SearchedProfile");
            // String PersonalityTraits = rs.getString("PersonalityTraits");
            // String RecommandedCompetence = rs.getString("RecommandedCompetence");
            // String Salary = rs.getString("Salary");
            // String SocialAdvantages = rs.getString("SocialAdvantages");
            // String RemoteWork = rs.getString("RemoteWork");
            // String NumberOfPosts = rs.getString("NumberOfPosts");
            // String CompanyAdresse = rs.getString("ExperienceLevel");
            // String CompanyWebsite = rs.getString("CompanyWebsite");
            // String CompanyDescription = rs.getString("CompanyDescription");

        //     row = sheet.createRow(rowNum++);
        //     row.createCell(0).setCellValue(Title);
        //     row.createCell(1).setCellValue(OffreLink);
        //     row.createCell(2).setCellValue(SiteName);
        //     row.createCell(3).setCellValue(PublicationDate);
        //     row.createCell(4).setCellValue(ApplyDate); 
        //     row.createCell(5).setCellValue(CompanyAdresse); 
        //     row.createCell(6).setCellValue(CompanyWebsite);
        //     row.createCell(7).setCellValue(CompanyName);
        //     row.createCell(8).setCellValue(CompanyDescription);
        //     row.createCell(9).setCellValue(OffreDescription); 
        //     row.createCell(10).setCellValue(Region);
        //     row.createCell(11).setCellValue(City);
        //     row.createCell(12).setCellValue(Sectors);
        //     row.createCell(13).setCellValue(Occupation);
        //     row.createCell(14).setCellValue(ContractType);
        //     row.createCell(15).setCellValue(EducationLevel);
        //     row.createCell(16).setCellValue(Diploma); 
        //     row.createCell(17).setCellValue(ExperienceLevel);
        //     row.createCell(18).setCellValue(SearchedProfile);
        //     row.createCell(19).setCellValue(PersonalityTraits); 
        //     row.createCell(20).setCellValue(RecommandedCompetence); 
        //     row.createCell(21).setCellValue(Salary); 
        //     row.createCell(22).setCellValue(SocialAdvantages); 
        //     row.createCell(23).setCellValue(RemoteWork); 
        //     row.createCell(24).setCellValue(NumberOfPosts);
        // }
        

        // try (FileOutputStream fileOut = new FileOutputStream("offre.xlsx")) {
        //     workbook.write(fileOut);
        //     System.out.println("Excel file created successfully!");
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
    }

    // StringBuilder sqlRequest2 = new StringBuilder();
    // sqlRequest2.append("SELECT skillName, skillType, COUNT(*) AS count ");
    // sqlRequest2.append("FROM skill ");
    // sqlRequest2.append("WHERE id_offre IN (");

    // for (int i = 0; i < id_offres.size(); i++) {
    //     sqlRequest2.append("?");
    //     if (i < id_offres.size() - 1) {
    //         sqlRequest2.append(", ");
    //     }
    // }

    // sqlRequest2.append(") ");
    // sqlRequest2.append("GROUP BY skillName, skillType;");

    // try (PreparedStatement pstmt2 = con.prepareStatement(sqlRequest2.toString())) {
    //     for (int i = 0; i < id_offres.size(); i++) {
    //         pstmt2.setString(i + 1, id_offres.get(i));
    //     }

    //     ResultSet rs2 = pstmt2.executeQuery();

    //     while (rs2.next()) {
    //         String skillName = rs2.getString("skillName");
    //         String skillType = rs2.getString("skillType");
    //         int repeated = rs2.getInt("count");

    //         // rowSkill = sheetSkill.createRow(rowNumSkill++);

    //         // rowSkill.createCell(0).setCellValue(skillName);
    //         // rowSkill.createCell(1).setCellValue(skillType);
    //         // rowSkill.createCell(2).setCellValue(repeated);
    //     }

        // try (FileOutputStream fileOut = new FileOutputStream("skill.xlsx")) {
        //     workbookSkill.write(fileOut);
        //     System.out.println("Excel file of Skills created successfully!");
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
    // }

    // System.out.println("\n---------------------------------------\nNecessary Languages for these Offers:");

    // sqlRequest2 = new StringBuilder();
    // sqlRequest2.append("SELECT langueName, level, COUNT(*) AS count ");
    // sqlRequest2.append("FROM Langue ");
    // sqlRequest2.append("WHERE id_offre IN (");

    // for (int i = 0; i < id_offres.size(); i++) {
    //     sqlRequest2.append("?");
    //     if (i < id_offres.size() - 1) {
    //         sqlRequest2.append(", ");
    //     }
    // }

    // sqlRequest2.append(") ");
    // sqlRequest2.append("GROUP BY langueName, level;");

    // try (PreparedStatement pstmt2 = con.prepareStatement(sqlRequest2.toString())) {
    //     for (int i = 0; i < id_offres.size(); i++) {
    //         pstmt2.setString(i + 1, id_offres.get(i));
    //     }

    //     ResultSet rs2 = pstmt2.executeQuery();

    //     while (rs2.next()) {
    //         String langueName = rs2.getString("langueName");
    //         String level = rs2.getString("level");
    //         int repeated = rs2.getInt("count");

    //         // rowLangue = sheetLangue.createRow(rowNumLangue++);

    //         // rowLangue.createCell(0).setCellValue(langueName);
    //         // rowLangue.createCell(1).setCellValue(level);
    //         // rowLangue.createCell(2).setCellValue(repeated);
    //     }

    //     // try (FileOutputStream fileOut = new FileOutputStream("Langue.xlsx")) {
    //     //     workbookLangue.write(fileOut);
    //     //     System.out.println("Excel file of Langues created successfully!");
    //     // } catch (Exception e) {
    //     // e.printStackTrace();
    //     // }
    // }


} catch (SQLException e) {
    e.printStackTrace();
} 
// finally {
        // try {
        //     // Close workbooks in a finally block
        //     if (workbook != null) {
        //         workbook.close();
        //     }
        //     if (workbookSkill != null) {
        //         workbookSkill.close();
        //     }
        //     if (workbookLangue != null) {
        //         workbookLangue.close();
        //     }
        // } catch (IOException e) {
        //     e.printStackTrace(); // Handle the exception appropriately
        // }
    }
    return resultSet;
    
    }

    public void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    
}
