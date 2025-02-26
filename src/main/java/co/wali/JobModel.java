package co.wali;

class JobModel {
    private Integer jobNumber;
    private String jobTitle;
    private String jobLink;
    private String publicationDate;
    private Integer department;

    public JobModel(Integer jobNumber, String jobTitle, String jobLink, String publicationDate, Integer department) {
        this.jobNumber = jobNumber;
        this.jobTitle = jobTitle;
        this.jobLink = jobLink;
        this.publicationDate = publicationDate;
        this.department = department;
    }

    public Integer getJobNumber() { return jobNumber; }
    public String getJobTitle() { return jobTitle; }
    public String getJobLink() { return jobLink; }
    public String getPublicationDate() { return publicationDate; }
    public Integer getDepartment() { return department; }
}