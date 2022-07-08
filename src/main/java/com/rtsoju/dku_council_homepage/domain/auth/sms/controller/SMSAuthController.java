package com.rtsoju.dku_council_homepage.domain.auth.sms.controller;

import com.rtsoju.dku_council_homepage.common.Messages;
import com.rtsoju.dku_council_homepage.common.RequestResult;
import com.rtsoju.dku_council_homepage.domain.auth.sms.dto.SMSAuthToken;
import com.rtsoju.dku_council_homepage.domain.auth.sms.dto.request.VerifyCodeRequest;
import com.rtsoju.dku_council_homepage.domain.auth.sms.service.SMSAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth/sms")
@RequiredArgsConstructor
public class SMSAuthController {
    private final SMSAuthService service;

    @ExceptionHandler(IllegalArgumentException.class)
    public RequestResult handleError(IllegalArgumentException e) {
        return new RequestResult(e);
    }

    @GetMapping("/code")
    public SMSAuthToken sendCode(@RequestParam String phone) {
        log.debug("Request sending SMS auth code: {}", phone);
        return service.sendSMSCode(phone);
    }

    @PostMapping("/code")
    public RequestResult verifyCode(@RequestBody VerifyCodeRequest body) {
        log.debug("Let's verify {} -> {}", body.getIdentifier(), body.getCode());
        service.verifyCode(body.getIdentifier(), body.getCode());
        return new RequestResult(Messages.SUCCESS_SMS_AUTH.getMessage());
    }
}
