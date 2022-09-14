package ru.mmtr.dictionarySpring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static ru.mmtr.dictionarySpring.controllers.ActionControllerMvc.ID;

@Controller
@RequestMapping("/menu-controller")
public class ActionMenuControllerMvc {
    @GetMapping("/action-menu")
    public String workWithLibrary(@RequestParam(name = "dictionaryId") int dictionaryId,
                                  @RequestParam(name = "action") String action,
                                  Model model) {
        model.addAttribute(ID, dictionaryId);
        return "action_menu/" + action;
    }
}

