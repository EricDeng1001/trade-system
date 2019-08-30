package ai.techfin.tradesystem.web.rest;

import ai.techfin.tradesystem.security.AuthoritiesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/api")
public class PlacementController {

    private static final Logger log = LoggerFactory.getLogger(PlacementController.class);

    @PostMapping("/placement-list")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(AuthoritiesConstants.TRADER)
    public void createPlacement() {

    }
}
