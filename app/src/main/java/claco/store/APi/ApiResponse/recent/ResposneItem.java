package claco.store.APi.ApiResponse.recent;


import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResposneItem {

    @SerializedName("CustomerID")
    @Expose
    private String customerID;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("Gender")
    @Expose
    private Object gender;
    @SerializedName("DateofBirth")
    @Expose
    private Object dateofBirth;
    @SerializedName("Country")
    @Expose
    private Object country;
    @SerializedName("State")
    @Expose
    private Object state;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("PostalCode")
    @Expose
    private Object postalCode;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Phone1")
    @Expose
    private String phone1;
    @SerializedName("Address1")
    @Expose
    private Object address1;
    @SerializedName("Picture")
    @Expose
    private Object picture;
    @SerializedName("Response")
    @Expose
    private Boolean response;
    @SerializedName("Role")
    @Expose
    private String role;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("ReferCode")
    @Expose
    private Object referCode;

   
    public ResposneItem() {
    }


    public ResposneItem(String customerID, String firstName, Integer age, Object gender, Object dateofBirth, Object country, Object state, Object city, Object postalCode, String email, String phone1, Object address1, Object picture, Boolean response, String role, Boolean status, Object referCode) {
        super();
        this.customerID = customerID;
        this.firstName = firstName;
        this.age = age;
        this.gender = gender;
        this.dateofBirth = dateofBirth;
        this.country = country;
        this.state = state;
        this.city = city;
        this.postalCode = postalCode;
        this.email = email;
        this.phone1 = phone1;
        this.address1 = address1;
        this.picture = picture;
        this.response = response;
        this.role = role;
        this.status = status;
        this.referCode = referCode;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Object getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(Object dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Object postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public Object getAddress1() {
        return address1;
    }

    public void setAddress1(Object address1) {
        this.address1 = address1;
    }

    public Object getPicture() {
        return picture;
    }

    public void setPicture(Object picture) {
        this.picture = picture;
    }

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Object getReferCode() {
        return referCode;
    }

    public void setReferCode(Object referCode) {
        this.referCode = referCode;
    }

}
