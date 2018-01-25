package com.app.sportzfever.models.dbmodels;

public class User {
    private   int id;
    private String  passwordRequestHash;
    private String  fbId;
    private String  activeStatus;
    private String  twitterAuthTokenSecret;
    private String  friendPrivacy;
    private String  dateOfBirth;
    private String  fbAuthToken;
    private String  deviceType;
    private String  emailConfirmationHash;
    private String  fbAuthTokenSecret;
    private String  email;
    private String  deviceToken;
    private String  twitterAuthToken;
    private String  invite;
    private String  deviceId;
    private String  password;
    private String  tokenExpires;
    private String  passwordRequestExpire;
    private String  firstName;
    private String  twitterId;
    private String  lastName;
    private String  imagesPrivacy;
    private String  passwordRequestedAt;
    private String  alias;
    private String  token;
    private String  lastLogin;
    private String  gender;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPasswordRequestHash() {
        return passwordRequestHash;
    }

    public void setPasswordRequestHash(String passwordRequestHash) {
        this.passwordRequestHash = passwordRequestHash;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getTwitterAuthTokenSecret() {
        return twitterAuthTokenSecret;
    }

    public void setTwitterAuthTokenSecret(String twitterAuthTokenSecret) {
        this.twitterAuthTokenSecret = twitterAuthTokenSecret;
    }

    public String getFriendPrivacy() {
        return friendPrivacy;
    }

    public void setFriendPrivacy(String friendPrivacy) {
        this.friendPrivacy = friendPrivacy;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFbAuthToken() {
        return fbAuthToken;
    }

    public void setFbAuthToken(String fbAuthToken) {
        this.fbAuthToken = fbAuthToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getEmailConfirmationHash() {
        return emailConfirmationHash;
    }

    public void setEmailConfirmationHash(String emailConfirmationHash) {
        this.emailConfirmationHash = emailConfirmationHash;
    }

    public String getFbAuthTokenSecret() {
        return fbAuthTokenSecret;
    }

    public void setFbAuthTokenSecret(String fbAuthTokenSecret) {
        this.fbAuthTokenSecret = fbAuthTokenSecret;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getTwitterAuthToken() {
        return twitterAuthToken;
    }

    public void setTwitterAuthToken(String twitterAuthToken) {
        this.twitterAuthToken = twitterAuthToken;
    }

    public String getInvite() {
        return invite;
    }

    public void setInvite(String invite) {
        this.invite = invite;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTokenExpires() {
        return tokenExpires;
    }

    public void setTokenExpires(String tokenExpires) {
        this.tokenExpires = tokenExpires;
    }

    public String getPasswordRequestExpire() {
        return passwordRequestExpire;
    }

    public void setPasswordRequestExpire(String passwordRequestExpire) {
        this.passwordRequestExpire = passwordRequestExpire;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImagesPrivacy() {
        return imagesPrivacy;
    }

    public void setImagesPrivacy(String imagesPrivacy) {
        this.imagesPrivacy = imagesPrivacy;
    }

    public String getPasswordRequestedAt() {
        return passwordRequestedAt;
    }

    public void setPasswordRequestedAt(String passwordRequestedAt) {
        this.passwordRequestedAt = passwordRequestedAt;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(String emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    private String  emailConfirmed;


    public User(String  passwordRequestHash,String  fbId,String  activeStatus,String  twitterAuthTokenSecret,String  friendPrivacy,String  dateOfBirth,String  fbAuthToken,String  deviceType,String  emailConfirmationHash,String  fbAuthTokenSecret,String  email,String  deviceToken,String  twitterAuthToken,String  invite,String  deviceId,String  password,String  tokenExpires,String  passwordRequestExpire,String  firstName,String  twitterId,String  lastName,String  imagesPrivacy,String  passwordRequestedAt,String  alias,String  token,String  lastLogin,String  gender,String  emailConfirmed)
    {
        this.passwordRequestHash=passwordRequestHash;
        this.fbId=fbId;
        this.activeStatus=activeStatus;
        this.twitterAuthTokenSecret=twitterAuthTokenSecret;
        this.friendPrivacy=friendPrivacy;
        this.dateOfBirth=dateOfBirth;
        this.fbAuthToken=fbAuthToken;
        this.deviceType=deviceType;
        this.emailConfirmationHash=emailConfirmationHash;
        this.fbAuthTokenSecret=fbAuthTokenSecret;
        this.email=email;
        this.deviceToken=deviceToken;
        this.twitterAuthToken=twitterAuthToken;
        this.invite=invite;
        this.deviceId=deviceId;
        this.password=password;
        this.tokenExpires=tokenExpires;
        this.passwordRequestExpire=passwordRequestExpire;
        this.firstName=firstName;
        this.twitterId=twitterId;
        this.lastName=lastName;
        this.imagesPrivacy=imagesPrivacy;
        this.passwordRequestedAt=passwordRequestedAt;
        this.alias=alias;
        this.token=token;
        this.lastLogin=lastLogin;
        this.gender=gender;
        this.emailConfirmed=emailConfirmed;

    }


}
