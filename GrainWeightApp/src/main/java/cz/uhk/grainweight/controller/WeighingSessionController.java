package cz.uhk.grainweight.controller;

import cz.uhk.grainweight.model.WeighingSession;
import cz.uhk.grainweight.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/sessions")
@SessionAttributes("currentSession")
public class WeighingSessionController {

    private final FieldService fieldService;
    private final DriverService driverService;
    private final WeighingSessionService sessionService;

    public WeighingSessionController(FieldService fieldService, DriverService driverService, UserService userService, WeighingSessionService sessionService) {
        this.fieldService = fieldService;
        this.driverService = driverService;
        this.sessionService = sessionService;
    }

    @ModelAttribute("currentSession")
    public WeighingSession setupSession() {
        return new WeighingSession();
    }

    @GetMapping("/new")
    public String newSession(Model m, @ModelAttribute("currentSession") WeighingSession s) {
        m.addAttribute("fields", fieldService.getAllFields());
        m.addAttribute("drivers", driverService.getAllDrivers());
        // připrav prázdný objekt – startTime se naplní při uložení
        return "session_form";
    }

    @PostMapping("/new")
    public String createSession(@ModelAttribute("currentSession") WeighingSession s,
                                @RequestParam String cropType,
                                @RequestParam String destination,
                                SessionStatus status) {
        s.setStartTime(LocalDateTime.now());
        s.setCropType(cropType);
        s.setDestination(destination);
        // drivers a field už jsou navázané přes th:field v HTML
        sessionService.startSession(s);
        status.setComplete(); // vyčistí currentSession ze session
        return "redirect:/weightrecords/new";
    }

    // volitelně GET /sessions pro výpis všech session
}
