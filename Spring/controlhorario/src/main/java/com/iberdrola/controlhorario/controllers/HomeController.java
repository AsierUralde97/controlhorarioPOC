package com.iberdrola.controlhorario.controllers;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iberdrola.controlhorario.domain.Workday;
import com.iberdrola.controlhorario.services.MainServices;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

        @Autowired
        MainServices mainServices;

        String workdayListNameInView = "workdayList";
        String totalHoursNameInView = "totalHours";
        String homepageViewName = "workday";

        @GetMapping("/clockInAndOut")
        public String index(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal) {

                String employeeNumber = principal.getAttribute("employeeNumber").toString();

                mainServices.insertWorkdayInDatabase(employeeNumber);

                return "redirect:/home";
        }

        @GetMapping("/home")
        public String obtainCurrentWorkday(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal,
                        Model model) {
                List<Workday> currentWorkdayList = mainServices
                                .obtainWorkdayCurrentDate(principal.getAttribute("employeeNumber").toString());

                String totalHours = mainServices
                                .totalWorkingHoursCurrentDate(principal.getAttribute("employeeNumber").toString());

                model.addAttribute(workdayListNameInView, currentWorkdayList);
                model.addAttribute(totalHoursNameInView, totalHours);

                return homepageViewName;
        }

        @GetMapping(value = "/workdayByDate/{date}")
        public String obtainWorkdayByDate(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal,
                        @PathVariable(value = "date") String date, Model model) {

                List<Workday> workdayByDate = mainServices
                                .obtainWorkdayByDate(principal.getAttribute("employeeNumber").toString(), date);

                String totalHoursByDate = mainServices
                                .totalWorkingHoursByDate(principal.getAttribute("employeeNumber").toString(), date);

                model.addAttribute(workdayListNameInView, workdayByDate);
                model.addAttribute(totalHoursNameInView, totalHoursByDate);
                return homepageViewName;
        }

        @GetMapping(value = "/month")
        public String obtainWorkdayLastMonth(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal,
                        Model model) {

                List<Workday> workdayLastMonth = mainServices
                                .obtainWorkdayLastMonth(principal.getAttribute("employeeNumber").toString());

                String totalHoursLastMonth = mainServices
                                .totalWorkingHoursLastMonth(
                                                principal.getAttribute("employeeNumber").toString());
                model.addAttribute(workdayListNameInView, workdayLastMonth);
                model.addAttribute(totalHoursNameInView, totalHoursLastMonth);

                return homepageViewName;
        }

        @GetMapping(value = "/year")
        public String obtainWorkdayLastYear(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal,
                        Model model) {

                List<Workday> workdayLastYear = mainServices
                                .obtainWorkdayLastYear(principal.getAttribute("employeeNumber").toString());

                String totalHoursLastYear = mainServices
                                .totalWorkingHoursLastYear(
                                                principal.getAttribute("employeeNumber").toString());
                model.addAttribute(workdayListNameInView, workdayLastYear);
                model.addAttribute(totalHoursNameInView, totalHoursLastYear);

                return homepageViewName;
        }

}