package org.brjia.qs.model;

import java.util.Date;

public class FamilyRegDTO {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column family_reg.id
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column family_reg.name
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column family_reg.create_user
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    private Integer createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column family_reg.create_time
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column family_reg.id
     *
     * @return the value of family_reg.id
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column family_reg.id
     *
     * @param id the value for family_reg.id
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column family_reg.name
     *
     * @return the value of family_reg.name
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column family_reg.name
     *
     * @param name the value for family_reg.name
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column family_reg.create_user
     *
     * @return the value of family_reg.create_user
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column family_reg.create_user
     *
     * @param createUser the value for family_reg.create_user
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column family_reg.create_time
     *
     * @return the value of family_reg.create_time
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column family_reg.create_time
     *
     * @param createTime the value for family_reg.create_time
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}