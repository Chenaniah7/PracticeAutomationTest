package com.gcw.apiautomation.core.api.rest;

import com.gcw.apiautomation.core.domain.entity.Entity;

public abstract class AbstractRestJob {

    public abstract void perform(Entity entity);

}
