package oneapp.demo.service;

import oneapp.demo.dto.TestDto;

public interface TestFacadeLocal {

	Integer createUser(TestDto dto);

	TestDto getUser(Integer id);

}
