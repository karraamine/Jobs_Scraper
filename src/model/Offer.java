package model;

//import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.util.List;

public class Offer {
    private int idOffre;
    private String title;
    private String offreLink;
    private String siteName;
    private String publicationDate;
    private String applyDate;
    private String offreDescription;
    private String region;
    private String city;
    private String sectors;
    private String occupation;
    private String contractType;
    private String educationLevel;
    private String diploma;
    private String experienceLevel;
    private String searchedProfile;
    private String personalityTraits;
    private String recommandedCompetence;
    private String salary;
    private String socialAdvantages;
    private String remoteWork;
    private int numberOfPosts;
    private List<Skill> skills;
    private Company company;
    private Langue langue;

    public Offer(
        String siteName, String title, String offreDescription, int numberOfPosts, String publicationDate,
             String companyName, String companyDescription, String region, String city, String personalityTraits,
             String contractType, String remoteWork, String sectors, String experienceLevel, String salary,
             String educationLevel, String diploma, Langue langue, String offreLink, String applyDate, String searchedProfile,String occupation,
             String recommandedComp,String socialAdvantages,List<Skill> skills, Company company, Langue langueObject
    ) {
        this.title = title;
        this.offreLink = offreLink;
        this.siteName = siteName;
        this.publicationDate = publicationDate;
        this.applyDate = applyDate;
        this.offreDescription = offreDescription;
        this.region = region;
        this.city = city;
        this.sectors = sectors;
        this.occupation = occupation;
        this.contractType = contractType;
        this.educationLevel = educationLevel;
        this.diploma = diploma;
        this.experienceLevel = experienceLevel;
        this.searchedProfile = searchedProfile;
        this.personalityTraits = personalityTraits;
        this.recommandedCompetence = recommandedComp;
        this.salary = salary;
        this.socialAdvantages = socialAdvantages;
        this.remoteWork = remoteWork;
        this.numberOfPosts = numberOfPosts;
        this.skills = skills;
        this.company = company;
        this.langue = langue;
    }

    public int getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOffreLink() {
        return offreLink;
    }

    public void setOffreLink(String offreLink) {
        this.offreLink = offreLink;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getOffreDescription() {
        return offreDescription;
    }

    public void setOffreDescription(String offreDescription) {
        this.offreDescription = offreDescription;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSectors() {
        return sectors;
    }

    public void setSectors(String sectors) {
        this.sectors = sectors;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getSearchedProfile() {
        return searchedProfile;
    }

    public void setSearchedProfile(String searchedProfile) {
        this.searchedProfile = searchedProfile;
    }

    public String getPersonalityTraits() {
        return personalityTraits;
    }

    public void setPersonalityTraits(String personalityTraits) {
        this.personalityTraits = personalityTraits;
    }

    public String getRecommandedCompetence() {
        return recommandedCompetence;
    }

    public void setRecommandedCompetence(String recommandedCompetence) {
        this.recommandedCompetence = recommandedCompetence;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getSocialAdvantages() {
        return socialAdvantages;
    }

    public void setSocialAdvantages(String socialAdvantages) {
        this.socialAdvantages = socialAdvantages;
    }

    public String getRemoteWork() {
        return remoteWork;
    }

    public void setRemoteWork(String remoteWork) {
        this.remoteWork = remoteWork;
    }

    public int getNumberOfPosts() {
        return numberOfPosts;
    }

    public void setNumberOfPosts(int numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Langue getLangue() {
        return langue;
    }

    public void setLangue(Langue langue) {
        this.langue = langue;
    }
}

