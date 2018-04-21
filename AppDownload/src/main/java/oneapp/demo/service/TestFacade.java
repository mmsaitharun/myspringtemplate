package oneapp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oneapp.demo.dao.TestDao;
import oneapp.demo.dto.TestDto;

@Service
public class TestFacade implements TestFacadeLocal {

	@Autowired
	TestDao dao;
	
	@Override
	public Integer createUser(TestDto dto) {
		return dao.addUser(dto);
	}
	
	@Override
	public TestDto getUser(Integer id) {
		return dao.getUser(id);
	}
	
}
