package model;

// import java.util.Arrays;
// import java.util.List;

public class returnResult {
    public String Title;
    public String CompanyName;
    public String PublicationDate;
    public String SiteName;

    public returnResult(String Title, String CompanyName, String PublicationDate, String SiteName){
        this.CompanyName = CompanyName;
        this.PublicationDate = PublicationDate;
        this.SiteName = SiteName;
        this.Title = Title;
    }
}
