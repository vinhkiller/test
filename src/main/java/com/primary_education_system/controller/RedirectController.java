package com.primary_education_system.controller;

import com.primary_education_system.service.RedirectService;
import com.primary_education_system.config.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RedirectController {

    @Autowired
    private RedirectService redirectService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RedirectController.class);

    @RequestMapping(value = "/redirectHandler", method = RequestMethod.GET)
    public String redirectHandler(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        String uri = redirectService.getDefaultRedirectUri(userDetails);
        LOGGER.info("Redirect user {} to url {}", userDetails.getUsername(), uri);
        return "redirect:" + uri;

    }

    @GetMapping("/")
    public String redirectRoot(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login";
        } else {
            String uri = redirectService.getDefaultRedirectUri(userDetails);
            LOGGER.info("Redirect user {} to url {}", userDetails.getUsername(), uri);
            return "redirect:" + uri;
        }
    }
}
