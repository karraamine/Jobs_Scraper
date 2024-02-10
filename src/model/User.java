package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import DB.DataBase;

public class User {
  private String email;
  private String password;

  
  public User(){
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUser(String email,String password) throws SQLException{
    this.email = email;
    this.password = password;
    DataBase.getStatement().executeUpdate("insert into user (email,password) values('"+email+"','"+password+"')");
  }

  public boolean checkUser(String email) throws SQLException{
    String query = "select email from user where email='"+email+"'";
    ResultSet resultSet = DataBase.getStatement().executeQuery(query);
    boolean isFound = false;

    while (resultSet.next()) {
        isFound = true;
    }
    return isFound;
  }

  public boolean checkUser(String email,String password) throws SQLException{
    System.out.println("email:"+email+",pass"+password);
    String query = "select email from user where email='"+email+"' and password='"+password+"'";
    ResultSet resultSet = DataBase.getStatement().executeQuery(query);
    boolean isFound = false;

    while (resultSet.next()) {
        isFound = true;
    }
    return isFound;
  }


}