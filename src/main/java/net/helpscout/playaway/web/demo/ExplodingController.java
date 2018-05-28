package net.helpscout.playaway.web.demo;

import net.helpscout.playaway.stereotype.ReadingController;
import org.springframework.web.bind.annotation.GetMapping;

@ReadingController
public class ExplodingController {

    @GetMapping("/internal-server-error")
    public void explode() {
        throw new RuntimeException("Boom!");
    }
}
