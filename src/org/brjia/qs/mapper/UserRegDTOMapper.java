package org.brjia.qs.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.brjia.qs.model.UserRegDTO;
import org.brjia.qs.model.UserRegDTOExample;

public interface UserRegDTOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_reg
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    int countByExample(UserRegDTOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_reg
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    int deleteByExample(UserRegDTOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_reg
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_reg
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    int insert(UserRegDTO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_reg
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    int insertSelective(UserRegDTO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_reg
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    List<UserRegDTO> selectByExample(UserRegDTOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_reg
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    UserRegDTO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_reg
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    int updateByExampleSelective(@Param("record") UserRegDTO record, @Param("example") UserRegDTOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_reg
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    int updateByExample(@Param("record") UserRegDTO record, @Param("example") UserRegDTOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_reg
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    int updateByPrimaryKeySelective(UserRegDTO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_reg
     *
     * @mbggenerated Mon Apr 18 22:46:19 CST 2016
     */
    int updateByPrimaryKey(UserRegDTO record);
}