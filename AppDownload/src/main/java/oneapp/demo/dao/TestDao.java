package oneapp.demo.dao;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import oneapp.demo.dto.TestDto;
import oneapp.demo.entity.TestDo;

@Repository("testDao")
@Transactional
public class TestDao extends BaseDao<TestDo, TestDto> {
	
	@Override
	protected TestDto exportDto(TestDo entity) {
		TestDto dto = new TestDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setPassword(entity.getPassword());
		return dto;
	}

	@Override
	protected TestDo importDto(TestDto fromDto) {
		TestDo entity = new TestDo();
		entity.setId(fromDto.getId());
		entity.setName(fromDto.getName());
		entity.setPassword(fromDto.getPassword());
		return entity;
	}
	
	public Integer addUser(TestDto dto) {
		return (Integer) this.getSession().save(this.importDto(dto));
	}
	
	public TestDto getUser(Integer id) {
		return this.exportDto(this.getSession().find(TestDo.class, id));
	}
}
