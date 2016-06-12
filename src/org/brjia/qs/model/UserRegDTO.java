package org.brjia.qs.model;

import java.util.Date;

public class UserRegDTO {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_reg.id
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_reg.email
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_reg.password
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_reg.create_time
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_reg.member_id
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    private Integer memberId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_reg.area_id
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    private Integer areaId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_reg.id
     *
     * @return the value of user_reg.id
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_reg.id
     *
     * @param id the value for user_reg.id
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_reg.email
     *
     * @return the value of user_reg.email
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_reg.email
     *
     * @param email the value for user_reg.email
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_reg.password
     *
     * @return the value of user_reg.password
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_reg.password
     *
     * @param password the value for user_reg.password
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_reg.create_time
     *
     * @return the value of user_reg.create_time
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_reg.create_time
     *
     * @param createTime the value for user_reg.create_time
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_reg.member_id
     *
     * @return the value of user_reg.member_id
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public Integer getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_reg.member_id
     *
     * @param memberId the value for user_reg.member_id
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_reg.area_id
     *
     * @return the value of user_reg.area_id
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public Integer getAreaId() {
        return areaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_reg.area_id
     *
     * @param areaId the value for user_reg.area_id
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
}