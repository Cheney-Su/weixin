package com.doubles.entity.req;

/**
 * Created by Administrator on 2016/12/11 0011.
 * 文本消息
 */
public class TextMessage extends BaseMessage {
    // 消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
