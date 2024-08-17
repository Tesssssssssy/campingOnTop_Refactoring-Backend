package com.example.campingontop.domain.mysql.orders.service;

import com.example.campingontop.domain.mysql.cart.model.Cart;
import com.example.campingontop.domain.mysql.cart.repository.CartRepository;
import com.example.campingontop.common.BaseResponse;
import com.example.campingontop.domain.mysql.house.repository.HouseRepository;
import com.example.campingontop.domain.mysql.orders.model.dto.response.GetPortOneRes;
import com.google.gson.*;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    private final HouseRepository houseRepository;
    private final CartRepository cartRepository;
    private final IamportClient iamportClient;

    @Value("${imp.apiKey}")
    private String apiKey;

    @Value("${imp.secretKey}")
    private String secretKey;

    //PortOne 토큰 발급
    private String getToken() throws IOException {
        HttpsURLConnection conn = null;

        URL url = new URL("https://api.iamport.kr/users/getToken");

        //HTTP 프로토콜 만들어주기
        conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        //데이터를 담아서 보내겠다
        conn.setDoOutput(true);

        JsonObject json = new JsonObject();
        json.addProperty("imp_key", apiKey);
        json.addProperty("imp_secret", secretKey);

        //보내기
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(json.toString());
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        Gson gson = new Gson();
        String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();
        String token = gson.fromJson(response, Map.class).get("token").toString();

        br.close();
        conn.disconnect();

        return token;
    }

    //결제 정보를 가져옴
    public IamportResponse<Payment> getPaymentInfo(String impUid) throws IamportResponseException, IOException {
        return iamportClient.paymentByImpUid(impUid);
    }

    private Integer getTotalPrice(List<GetPortOneRes> datas){
        List<Long> cartIds = new ArrayList<>();
        for (GetPortOneRes house: datas) {
            cartIds.add(house.getId());
        }

        List<Cart> carts = cartRepository.findAllById(cartIds);

        Integer totalPrice = 0;
        for (GetPortOneRes house: datas) {
            totalPrice += house.getPrice();
        }

        return  totalPrice;
    }

    //Portone에서 결제 정보 가져와서 검증 처리
    public Boolean paymentValidation(String impUid, Integer finalAmount) throws IamportResponseException, IOException {
        IamportResponse<Payment> response = getPaymentInfo(impUid);
        Integer expectedAmount = response.getResponse().getAmount().intValue();

        if (finalAmount.equals(expectedAmount)) {
            return true;
        } else {
            log.error("Mismatch in amounts. Expected: {}, Received: {}", expectedAmount, finalAmount);
            return false;
        }
    }

    /*
    public Boolean paymentValidation(String impUid) throws IamportResponseException, IOException {
        IamportResponse<Payment> response = getPaymentInfo(impUid);
        Integer amount = response.getResponse().getAmount().intValue();

        String customDataString = response.getResponse().getCustomData();
        customDataString = "{\"carts\":" + customDataString + "}";
        System.out.println("Received custom data string: " + customDataString);

        // customDataString이 JSON 형태의 문자열이므로, 이를 객체로 파싱
        Gson gson = new Gson();
        PaymentHouses paymentHouses = gson.fromJson(customDataString, PaymentHouses.class);

        Integer totalPrice = getTotalPrice(paymentHouses.getCarts());
        if (amount.equals(totalPrice)) {
            return true;
        }
        return false;
    }
    */


    public BaseResponse<String> paymentCancel(String impUid) throws IOException {
        String token = getToken();

        URL url = new URL("https://api.iamport.kr/payments/cancel");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("POST");

        // 요청의 Content-Type, Accept, Authorization 헤더 설정
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", token);

        // 해당 연결을 출력 스트림(요청)으로 사용
        conn.setDoOutput(true);

        // JSON 객체에 해당 API가 필요로하는 데이터 추가.
        JsonObject json = new JsonObject();
        json.addProperty("imp_uid", impUid);
        json.addProperty("reason", "공동 구매 인원 부족으로 인한 주문 취소");

        // 출력 스트림으로 해당 conn에 요청
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(json.toString());
        bw.flush();
        bw.close();

        // 입력 스트림으로 conn 요청에 대한 응답 반환
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        Gson gson = new Gson();
        String response = gson.fromJson(br.readLine(), Map.class).toString();

        System.out.println(response);

        br.close();
        conn.disconnect();


        return BaseResponse.successResponse("결제 취소 완료","결제 취소 완료: 주문번호 " + impUid);

    }
}
