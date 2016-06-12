package org.brjia.qs.service;

import org.brjia.qs.mapper.TestMapper;

public class TestService {

	private TestMapper testMapper;
	public String queryName(Integer id){
		return testMapper.selectName(id);
	}
	public TestMapper getTestMapper() {
		return testMapper;
	}
	public void setTestMapper(TestMapper testMapper) {
		this.testMapper = testMapper;
	}
	
}
