package io.github.srivathsanthulasinathan.harmonia.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hearth")
public class HarmoniaController {

    // TODO: Extend Actuator
    @GetMapping("/flame")
    public String sendHealthResponse() {
        return "The flame is lit";
    }


}
