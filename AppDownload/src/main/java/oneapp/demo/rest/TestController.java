package oneapp.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import oneapp.demo.dto.TestDto;
import oneapp.demo.service.TestFacadeLocal;

@RestController
@RequestMapping(value="/testing")
public class TestController {
	
	@Autowired
	private TestFacadeLocal local;
	
	@RequestMapping(value="/t", method=RequestMethod.GET, produces="application/json")
	public String test(){
		return "Working!";
	}
	
	@RequestMapping(value="/createUser",  method=RequestMethod.POST, produces="application/json", consumes="application/json")
	public Integer createUser(@RequestBody TestDto dto) {
		return local.createUser(dto);
	}
	
	@RequestMapping(value="/getUser",  method=RequestMethod.GET, produces="application/json")
	public TestDto getUser(@RequestParam("id") Integer id) {
		return local.getUser(id);
	}

}
