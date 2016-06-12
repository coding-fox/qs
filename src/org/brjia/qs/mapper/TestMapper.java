package org.brjia.qs.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TestMapper {

	@Select("select name from student where id=#{id}")
	public String selectName(@Param("id") Integer id);
}
