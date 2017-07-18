package hr.omega.imenik.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class WebController {

    @RequestMapping({"/", "/dodaj", "detalji/{id}"})
    String index() {
        return "forward:/index.html";
    }

}
