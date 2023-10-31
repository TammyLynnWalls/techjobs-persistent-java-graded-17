package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("skills")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("title", "All Skills");
        //TODO for Tam: should this be here?  Am I using the correct names?
        model.addAttribute("skills", skillRepository.findAll());

        return "skills/index";
    }

    @GetMapping("add")
    public String displayAddSkillForm(Model model) {
        model.addAttribute(new Skill());
        return "skills/add";
    }

    @PostMapping("add")
    public String processAddSkillForm(@ModelAttribute @Valid Skill newSkill, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "skills/add";
        }
        skillRepository.save(newSkill);

        //TODO for Tam: is this correct? book says: return "redirect:";
        return "redirect:/skills";
        //return "skills/index";
//        return "redirect:/view";
    }

    @GetMapping("view/{skillId}")
    public String displayViewSkill(Model model, @PathVariable int skillId) {
        Skill skill = null;
        Optional optSkill = skillRepository.findById(skillId);
        if (optSkill.isPresent()) {
            skill = (Skill) optSkill.get();
            return "skills/view";
        } else {


            //TODO for Tam: is this correct?
            return "redirect:../";
        }
    }

}
