package com.lpb.service.viettel.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.service.viettel.config.RestTemplateConfig;
import com.lpb.service.viettel.mapper.RequestMapper;
import com.lpb.service.viettel.model.request.*;
import com.lpb.service.viettel.model.response.ChainTransferResponse;
import com.lpb.service.viettel.model.response.GetFeeResponse;
import com.lpb.service.viettel.model.response.GetNameResponse;
import com.lpb.service.viettel.model.response.QueryTransResponse;
import com.lpb.service.viettel.service.ViettelService;
import com.lpb.service.viettel.utils.Constants;
import com.lpb.service.viettel.utils.RSAUtils;
import com.lpb.service.viettel.utils.XmlUtils;
import net.logstash.logback.encoder.org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.lpb.service.viettel.utils.Constants.*;

@Service
public class ViettelServiceImpl implements ViettelService {
    private static final Logger logger = LoggerFactory.getLogger(ViettelServiceImpl.class);
    private final RequestMapper requestMapper;
    final RestTemplateConfig restTemplate;

    public ViettelServiceImpl(RequestMapper requestMapper, RestTemplateConfig restTemplate) {
        this.requestMapper = requestMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseModel<Object> chainGetName(String type) throws Exception {
        RSAUtils.encryptionPass();
        ResponseModel<Object> responseEntity = null;
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String signature;
        String requestGetName;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        switch (type){
            case MM_CHAIN_GET_NAME:
                GetNameRequest getNameRequest = requestMapper.requestBodyGetName();
                String jsonGetName = ow.writeValueAsString(getNameRequest).replaceAll("\\s+","");
                String getname =XmlUtils.requestBodyGetName(getNameRequest);
                logger.info("chainGetName : json body get name ----> {}",jsonGetName);
                signature = RSAUtils.generateSignature(getname);
                requestGetName = XmlUtils.buildRequestViettel(type,getname,signature);
                logger.info("chainGetName : request xml get Name ----> {}",requestGetName);
                try {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.valueOf("text/xml; charset=utf-8"));
                    HttpEntity<String> httpEntity = new HttpEntity<>(requestGetName,httpHeaders);
                    ResponseEntity<String> response = restTemplate.getRestTemplatePartner()
                        .exchange("https://sandbox.viettel.vn/test_api/PartnerService/PartnerAPI?wsdl",
                            HttpMethod.POST,httpEntity,String.class);
                    List<String> body = Arrays.asList(Arrays.asList(Objects.requireNonNull(response.getBody()).split("<return>")).get(1).split("</return>"));
                    String returnBody = body.get(0).replaceAll("&quot;","'");
                    JSONObject jsonObject = new JSONObject(returnBody);
                    GetNameResponse getNameResponse=objectMapper.readValue(jsonObject.toString(), GetNameResponse.class);
                    logger.info("response getName -----> {}",getNameResponse);
                }catch (RestClientResponseException e) {
                    logger.error("chainGetName : call api get name timeout: {}", ExceptionUtils.getStackTrace(e));
                }catch (Exception e) {
                    logger.error("chainGetName : call api get name error: {}", e.getMessage());
                }
                break;
            case MM_CHAIN_GET_FEE:
                GetFeeRequest requestBodyGetFee = requestMapper.requestBodyGetFee();
                String jsonGetFee = ow.writeValueAsString(requestBodyGetFee).replaceAll("\\s+","");
                String bodyGetfee = XmlUtils.requestGetFee(requestBodyGetFee);
                logger.info("chainGetName : json body get fee ----> {}",jsonGetFee);
                signature = RSAUtils.generateSignature(bodyGetfee);
                requestGetName = XmlUtils.buildRequestViettel(type,bodyGetfee,signature);
                logger.info("chainGetName : request xml get fee ----> {}",requestGetName);
                try {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.valueOf("text/xml; charset=utf-8"));
                    HttpEntity<String> httpEntity = new HttpEntity<>(requestGetName,httpHeaders);
                    ResponseEntity<String> response = restTemplate.getRestTemplatePartner()
                        .exchange("https://sandbox.viettel.vn/test_api/PartnerService/PartnerAPI?wsdl",
                            HttpMethod.POST,httpEntity,String.class);
                    List<String> body = Arrays.asList(Arrays.asList(Objects.requireNonNull(response.getBody()).split("<return>")).get(1).split("</return>"));
                    String returnBody = body.get(0).replaceAll("&quot;","'");
                    JSONObject jsonObject = new JSONObject(returnBody);
                    GetFeeResponse getFeeResponse=objectMapper.readValue(jsonObject.toString(), GetFeeResponse.class);
                    logger.info("response getName -----> {}",getFeeResponse);
                }catch (RestClientResponseException e) {
                    logger.error("chainGetName : call api get fee timeout: {}", ExceptionUtils.getStackTrace(e));
                }catch (Exception e) {
                    logger.error("chainGetName : call api get fee error: {}", e.getMessage());
                }
                break;
            case MM_CHAIN_QUERY_TRANS:
                QueryTransRequest queryTransRequest = requestMapper.requestBodyQueryTrans();
                String jsonDepositsRequest = XmlUtils.requestQueryTrans(queryTransRequest);
                logger.info("chainGetName : json body get chain deposits ----> {}",jsonDepositsRequest);
                signature = RSAUtils.generateSignature(jsonDepositsRequest);
                requestGetName = XmlUtils.buildRequestViettel(type,jsonDepositsRequest,signature);
                logger.info("chainGetName : request xml chain deposits ----> {}",requestGetName);
                try {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.valueOf("text/xml; charset=utf-8"));
                    HttpEntity<String> httpEntity = new HttpEntity<>(requestGetName,httpHeaders);
                    ResponseEntity<String> response = restTemplate.getRestTemplatePartner()
                        .exchange("https://sandbox.viettel.vn/test_api/PartnerService/PartnerAPI?wsdl",
                            HttpMethod.POST,httpEntity,String.class);
                    List<String> body = Arrays.asList(Arrays.asList(Objects.requireNonNull(response.getBody()).split("<return>")).get(1).split("</return>"));
                    String returnBody = body.get(0).replaceAll("&quot;","'");
                    JSONObject jsonObject = new JSONObject(returnBody);
                    QueryTransResponse getFeeResponse=objectMapper.readValue(jsonObject.toString(), QueryTransResponse.class);
                    logger.info("response getName -----> {}",getFeeResponse);
                }catch (RestClientResponseException e) {
                    logger.error("chainGetName : call api chain deposits timeout: {}", ExceptionUtils.getStackTrace(e));
                }catch (Exception e) {
                    logger.info("chainGetName : call api chain deposits error: {}", e.getMessage());
                }
                break;
            case MM_CHAIN_TRANSFER:
                ChainTransferRequest chainTransferRequest = requestMapper.requestBodyChainTransfer();
                String jsonChainTransfer = ow.writeValueAsString(chainTransferRequest).replaceAll("\\s+","");
                String requestChainTransfer = XmlUtils.requestChainTransfer(chainTransferRequest);
                logger.info("chainGetName : json body chain transfer ----> {}",jsonChainTransfer);
                signature = RSAUtils.generateSignature(requestChainTransfer);
                requestGetName = XmlUtils.buildRequestViettel(type,requestChainTransfer,signature);
                logger.info("chainGetName : request xml chain transfer ----> {}",requestGetName);
                try {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.valueOf("text/xml; charset=utf-8"));
                    HttpEntity<String> httpEntity = new HttpEntity<>(requestGetName,httpHeaders);
                    ResponseEntity<String> response = restTemplate.getRestTemplatePartner()
                        .exchange("https://sandbox.viettel.vn/test_api/PartnerService/PartnerAPI?wsdl",
                            HttpMethod.POST,httpEntity,String.class);
                    List<String> body = Arrays.asList(Arrays.asList(Objects.requireNonNull(response.getBody()).split("<return>")).get(1).split("</return>"));
                    String returnBody = body.get(0).replaceAll("&quot;","'");
                    JSONObject jsonObject = new JSONObject(returnBody);
                    ChainTransferResponse getFeeResponse=objectMapper.readValue(jsonObject.toString(), ChainTransferResponse.class);
                    logger.info("response getName -----> {}",getFeeResponse);
                }catch (RestClientResponseException e) {
                    logger.error("chainGetName : call api chain transfer timeout: {}", ExceptionUtils.getStackTrace(e));
                }catch (Exception e) {
                    logger.error("chainGetName : call api chain transfer error: {}", e.getMessage());
                }
                break;
            case GET_BILL:
                GetBillRequest getBillRequest = requestMapper.getBillRequest();
                String jsonGetBillRequestRequest = ow.writeValueAsString(getBillRequest).replaceAll("\\s+","");
                String data = Constants.data;
                logger.info("chainGetName : json body get chain deposits ----> {}",jsonGetBillRequestRequest);
                signature = RSAUtils.generateSignature(data);
                requestGetName = XmlUtils.buildRequestViettel(type,jsonGetBillRequestRequest,signature);
                logger.info("chainGetName : request xml chain deposits ----> {}",requestGetName);
                try {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.valueOf("text/xml; charset=utf-8"));
                    HttpEntity<String> httpEntity = new HttpEntity<>(requestGetName,httpHeaders);
                    ResponseEntity<String> response = restTemplate.getRestTemplatePartner()
                        .exchange("https://sandbox.viettel.vn/test_api/PartnerService/PartnerAPI?wsdl",
                            HttpMethod.POST,httpEntity,String.class);
                    List<String> body = Arrays.asList(Arrays.asList(Objects.requireNonNull(response.getBody()).split("<return>")).get(1).split("</return>"));
                    String returnBody = body.get(0).replaceAll("&quot;","'");
                    JSONObject jsonObject = new JSONObject(returnBody);
                }catch (RestClientResponseException e) {
                    logger.error("chainGetName : call api chain deposits timeout: {}", ExceptionUtils.getStackTrace(e));
                }catch (Exception e) {
                    logger.info("chainGetName : call api chain deposits error: {}", e.getMessage());
                }
                break;
            default:
                break;
        }
        return responseEntity;
    }

    @Override
    public ResponseModel<Object> getName(HH getNameRequest) throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, IOException, InvalidKeyException {
        ResponseModel<Object> responseEntity = null;
        String signature;
        String requestGetName;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String bodyGetName = null;
        logger.info("chainGetName : json body get name ----> {}",bodyGetName);
        signature = RSAUtils.generateSignature(bodyGetName);
        requestGetName = XmlUtils.buildRequestViettel(MM_CHAIN_TRANSFER,bodyGetName,signature);
        logger.info("chainGetName : request xml get Name ----> {}",requestGetName);
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.valueOf("text/xml; charset=utf-8"));
            HttpEntity<String> httpEntity = new HttpEntity<>(requestGetName,httpHeaders);
            ResponseEntity<String> response = restTemplate.getRestTemplatePartner()
                .exchange("https://sandbox.viettel.vn/test_api/PartnerService/PartnerAPI?wsdl",
                    HttpMethod.POST,httpEntity,String.class);
            List<String> body = Arrays.asList(Arrays.asList(Objects.requireNonNull(response.getBody()).split("<return>")).get(1).split("</return>"));
            String returnBody = body.get(0).replaceAll("&quot;","'");
            JSONObject jsonObject = new JSONObject(returnBody);
            GetNameResponse getNameResponse=objectMapper.readValue(jsonObject.toString(), GetNameResponse.class);
            logger.info("response getName -----> {}",getNameResponse);
        }catch (RestClientResponseException e) {
            logger.error("chainGetName : call api get name timeout: {}", ExceptionUtils.getStackTrace(e));
        }catch (Exception e) {
            logger.error("chainGetName : call api get name error: {}", e.getMessage());
        }
        return responseEntity;
    }
}
