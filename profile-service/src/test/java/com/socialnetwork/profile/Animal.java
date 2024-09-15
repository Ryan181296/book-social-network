package com.socialnetwork.profile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Animal implements IAnimal {
    public void fly() {
        log.info("Fly........");
    }
}
