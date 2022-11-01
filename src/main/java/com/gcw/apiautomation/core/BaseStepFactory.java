package com.gcw.apiautomation.core;

import com.gcw.apiautomation.core.domain.entity.EntityBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseStepFactory {

    public static BaseStep getBaseStep(){
        BaseStep baseStep = new BaseStep();
        log.info("init a baseStep...");
        baseStep.setEntity(EntityBuilder.buildEntity());
        return baseStep;
    }


}
