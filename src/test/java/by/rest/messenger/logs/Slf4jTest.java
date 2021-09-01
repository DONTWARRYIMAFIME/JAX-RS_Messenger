package by.rest.messenger.logs;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Slf4jTest {

    public static void main(String[] args) {
        log.info("This is my log!");

        log.debug("This is debug log!");
        log.error("This is error log");
        log.warn("This is warning log");
    }

}
