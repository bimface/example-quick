package com.bimface.example.quick;

import com.bimface.sdk.BimfaceClient;
import com.bimface.sdk.bean.response.IntegrateBean;
import com.bimface.sdk.exception.BimfaceException;

public class Main {
    public static void main(String[] args) throws BimfaceException {
        BimfaceClient bimfaceClient = new BimfaceClient("H9G9MqN8yW3V0YI1jCAVy3kxcVK5Betm", "Oza9JAQGurKWtC2b4yY0dCreDUTiRnFs");
        IntegrateBean integrateBean = bimfaceClient.getIntegrate(1146761842016480L);
        System.out.println(integrateBean);
    }
}
