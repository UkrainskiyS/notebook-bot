package com.example.notebookbot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MVCBot {

    @GetMapping("/new")
    public String add(@RequestParam String chat, Model model) {
        model.addAttribute("chat", chat);
        return "new";
    }
}
