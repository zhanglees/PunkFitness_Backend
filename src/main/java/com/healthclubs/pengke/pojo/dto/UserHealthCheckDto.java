package com.healthclubs.pengke.pojo.dto;

import com.healthclubs.pengke.entity.UserhealthcheckReport;
import com.healthclubs.pengke.entity.UserhealthcheckResource;

public class UserHealthCheckDto {

    public UserhealthcheckReport getUserhealthcheckReport() {
        return userhealthcheckReport;
    }

    public void setUserhealthcheckReport(UserhealthcheckReport userhealthcheckReport) {
        this.userhealthcheckReport = userhealthcheckReport;
    }

    public UserhealthcheckResource getUserhealthcheckResource() {
        return userhealthcheckResource;
    }

    public void setUserhealthcheckResource(UserhealthcheckResource userhealthcheckResource) {
        this.userhealthcheckResource = userhealthcheckResource;
    }

    private UserhealthcheckReport userhealthcheckReport;

    private UserhealthcheckResource userhealthcheckResource;

}
