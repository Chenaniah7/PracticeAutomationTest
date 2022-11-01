package com.gcw.testautomation.support.dataproviders;

import org.testng.annotations.DataProvider;

public class PracticeDataProvider {

    @DataProvider(name = "radioIdProvider")
    public Object[] createRadioId(){
        return new Object[]{
                1,2,3
        };
    }

    @DataProvider(name = "optionIndexProvider")
    public Object[] createOptionIndex(){
        return new Object[]{
                1,2,3,4
        };
    }

}
