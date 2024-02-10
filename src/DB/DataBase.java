package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import model.Company;
import model.Langue;
import model.Offer;
import model.Skill;

public class DataBase {
  private static Statement statement;
  
  public static Statement getStatement(){
    Connection conn;
    try {
      conn = DBConnection.getConnection();
      statement = conn.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return statement;
  }

  // Method to insert data into the database
  public static void insertDataIntoDatabase(List<Offer> dataList) throws SQLException {
      Connection con = DBConnection.getConnection();
      String insertOffreSql = "INSERT INTO Offre (Title, OffreLink, SiteName, PublicationDate, ApplyDate, OffreDescription, Region, City, Sectors, Occupation, ContractType, EducationLevel, Diploma, ExperienceLevel, SearchedProfile, PersonalityTraits, RecommandedCompetence, Salary, SocialAdvantages, RemoteWork, NumberOfPosts) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      String insertSkillSql = "INSERT INTO skill (id_offre, skillName, skillType) VALUES (?, ?, ?)";
      String insertCompanySql = "INSERT INTO Company (id_offre, CompanyAdresse, CompanyWebsite, CompanyName, CompanyDescription) VALUES (?, ?, ?, ?, ?)";
      String insertLangueSql = "INSERT INTO Langue (id_offre, langueName, level) VALUES (?, ?, ?)";

      try (PreparedStatement insertOffreStmt = con.prepareStatement(insertOffreSql, PreparedStatement.RETURN_GENERATED_KEYS);
            PreparedStatement insertSkillStmt = con.prepareStatement(insertSkillSql);
            PreparedStatement insertCompanyStmt = con.prepareStatement(insertCompanySql);
            PreparedStatement insertLangueStmt = con.prepareStatement(insertLangueSql)) {

          for (Offer offre : dataList) {
              // Inserting data into Offre table
              insertOffreStmt.setString(1, offre.getTitle());
              insertOffreStmt.setString(2, offre.getOffreLink());
              insertOffreStmt.setString(3, offre.getSiteName());
              insertOffreStmt.setString(4, offre.getPublicationDate());
              insertOffreStmt.setString(5, offre.getApplyDate());
              insertOffreStmt.setString(6, offre.getOffreDescription());
              insertOffreStmt.setString(7, offre.getRegion());
              insertOffreStmt.setString(8, offre.getCity());
              insertOffreStmt.setString(9, offre.getSectors());
              insertOffreStmt.setString(10, offre.getOccupation());
              insertOffreStmt.setString(11, offre.getContractType());
              insertOffreStmt.setString(12, offre.getEducationLevel());
              insertOffreStmt.setString(13, offre.getDiploma());
              insertOffreStmt.setString(14, offre.getExperienceLevel());
              insertOffreStmt.setString(15, offre.getSearchedProfile());
              insertOffreStmt.setString(16, offre.getPersonalityTraits());
              insertOffreStmt.setString(17, offre.getRecommandedCompetence());
              insertOffreStmt.setString(18, offre.getSalary());
              insertOffreStmt.setString(19, offre.getSocialAdvantages());
              insertOffreStmt.setString(20, offre.getRemoteWork());
              insertOffreStmt.setInt(21, offre.getNumberOfPosts());

              insertOffreStmt.executeUpdate();

              // Getting the generated id_offre
              int idOffre;
              try (var generatedKeys = insertOffreStmt.getGeneratedKeys()) {
                  if (generatedKeys.next()) {
                      idOffre = generatedKeys.getInt(1);
                  } else {
                      throw new SQLException("Inserting offre failed, no ID obtained.");
                  }
              }

              // Inserting data into Skill table
              if(offre.getSkills()!=null){
                  for (Skill skill : offre.getSkills()) {
                  insertSkillStmt.setInt(1, idOffre);
                  insertSkillStmt.setString(2, skill.getSkillName());
                  insertSkillStmt.setString(3, skill.getSkillType());
                  insertSkillStmt.executeUpdate();
                  }
              }else{
                  insertSkillStmt.setInt(1, idOffre);
                  insertSkillStmt.setString(2, "");
                  insertSkillStmt.setString(3, "");
                  insertSkillStmt.executeUpdate();
              }
          
              // Inserting data into Company table
              Company company = offre.getCompany();
              insertCompanyStmt.setInt(1, idOffre);
              insertCompanyStmt.setString(2, company.getCompanyAdresse());
              insertCompanyStmt.setString(3, company.getCompanyWebsite());
              insertCompanyStmt.setString(4, company.getCompanyName());
              insertCompanyStmt.setString(5, company.getCompanyDescription());
              insertCompanyStmt.executeUpdate();

              // Inserting data into Langue table
              Langue langue = offre.getLangue();
              insertLangueStmt.setInt(1, idOffre);
              insertLangueStmt.setString(2, langue.getLangueName());
              insertLangueStmt.setString(3, langue.getLevel());
              insertLangueStmt.executeUpdate();
          }
      }
  }
  
}
