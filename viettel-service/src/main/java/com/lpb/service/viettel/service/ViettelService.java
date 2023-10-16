package com.lpb.service.viettel.service;


import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.service.viettel.model.request.GetNameRequest;
import com.lpb.service.viettel.model.request.HH;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;


public interface ViettelService {
    ResponseModel chainGetName(String Type) throws Exception;
    ResponseModel<Object> getName(HH getNameRequest) throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, IOException, InvalidKeyException;
}
