package com.lpb.service.viettel.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.service.viettel.model.request.GetNameRequest;
import com.lpb.service.viettel.model.request.HH;
import com.lpb.service.viettel.service.ViettelService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping(value = "/api/v1/info",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ViettelController {
    @Autowired
    private ViettelService viettelService;

    @PostMapping("/encode")
    public ResponseModel getInfoCustomerBill(@RequestParam String type) throws Exception {
        viettelService.chainGetName(type);

        return null;
    }
    @PostMapping("/getName")
    public ResponseEntity getInfoCustomerBill(@RequestBody HH a) throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, IOException, InvalidKeyException {
        viettelService.getName(a);

        return null;
    }
}
