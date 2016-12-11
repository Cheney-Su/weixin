package com.doubles.service;

import com.doubles.entity.resp.TextMessage;
import com.doubles.utils.Constant;
import com.doubles.utils.MessageUtil;
import com.doubles.utils.StringUtils;
import com.doubles.utils.Utils;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/11 0011.
 */
@Service
public class CoreService {

    @Autowired
    private RestTemplate restTemplate;

    public String processRequest(HttpServletRequest request) {
        String respMessage = "";

        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";
            //解析微信请求参数
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            //获取发送方账号
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            //请求内容
            //回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);

            // 接收用户发送的文本消息内容
            String content = requestMap.get("Content");
            //引入图灵机器人自动回复
            String url = Constant.TULINGURL;
            JSONObject requestBody = new JSONObject();
            //请求参数
            requestBody.put("key", Constant.TULINGAPIKEY);
            requestBody.put("info", content);
//            HttpHeaders headers = new HttpHeaders();
//            MediaType type = MediaType.parseMediaType("application/json; charset=utf-8");
//            headers.setContentType(type);
//            HttpEntity<String> requestEntity = new HttpEntity<String>(requestBody, headers);

            String resultStr = restTemplate.postForObject(url, requestBody, String.class);
            JSONObject jsonObject = new Gson().fromJson(resultStr, JSONObject.class);
            if (100000 == jsonObject.getInt("code")) {
                respContent = jsonObject.getString("text");
                System.out.println(respContent);
            }
            textMessage.setContent(respContent);
            respMessage = MessageUtil.textMessageToXml(textMessage);

//            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
//                // 接收用户发送的文本消息内容
//                String content = requestMap.get("Content");
//                if ("1".equals(content)) {
//                    textMessage.setContent("1是很好的");
//                    // 将文本消息对象转换成xml字符串
//                    respMessage = MessageUtil.textMessageToXml(textMessage);
//                } else if ("2".equals(content)) {
//                    textMessage.setContent("我不是2货");
//                    // 将文本消息对象转换成xml字符串
//                    respMessage = MessageUtil.textMessageToXml(textMessage);
//                }
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMessage;
    }
}
