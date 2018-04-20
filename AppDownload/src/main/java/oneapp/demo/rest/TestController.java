package oneapp.demo.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/testing", produces="application/json", consumes="application/json")
public class TestController {
	
	@RequestMapping(value="/t", method=RequestMethod.GET)
	public String test(){
		return "Working!";
	}

}
