package com.socialnetwork.profile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Qualifier("Chicken")
@Service
public class Chicken extends Animal {
    @Override
    public void fly() {
        log.info("Run........");
        super.fly();
    }
}
