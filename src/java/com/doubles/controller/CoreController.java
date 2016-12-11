package com.doubles.controller;

import com.doubles.service.CoreService;
import com.doubles.utils.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/12/11 0011.
 */
@RestController
@RequestMapping("")
public class CoreController {

    @Autowired
    private CoreService coreService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
    public String checkSignature(@RequestParam(name = "signature", required = false) String signature,
                                 @RequestParam(name = "timestamp", required = false) String timestamp,
                                 @RequestParam(name = "nonce", required = false) String nonce,
                                 @RequestParam(name = "echostr", required = false) String echostr) {
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }

        return "";
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
    public String post(HttpServletRequest request) {
        String respMessage = coreService.processRequest(request);
        return respMessage;
    }
}
