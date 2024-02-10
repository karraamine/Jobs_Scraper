package model;

public class Company {
    private int idOffre;
    private String companyAdresse;
    private String companyWebsite;
    private String companyName;
    private String companyDescription;

    // Getter and Setter methods for all attributes
    public Company(){}
    public Company(String CompanyAdresse, String CompanyDescription, String CompanyWebsite, String CompanyName){
        this.companyAdresse = CompanyAdresse;
        this.companyDescription = CompanyDescription;
        this.companyWebsite = CompanyWebsite;
        this.companyName = CompanyName;
        
    }
    public int getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public String getCompanyAdresse() {
        return companyAdresse;
    }

    public void setCompanyAdresse(String companyAdresse) {
        this.companyAdresse = companyAdresse;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAdresse;
    }

    public void setCompanyAddress(String companyAdd) {
        this.companyAdresse = companyAdd;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    // Other methods as needed
}


