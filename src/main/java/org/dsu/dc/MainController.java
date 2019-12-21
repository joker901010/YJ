package org.dsu.dc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.java.Log;

@Log
@Controller
public class MainController {
	 @GetMapping("/")
	    public String main() {
	        log.info("main() called ...");

	        return "/main";
	    }
}
