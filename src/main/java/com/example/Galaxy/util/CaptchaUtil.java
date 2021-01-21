package com.example.Galaxy.util;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.base.Captcha;

import java.awt.*;
import java.io.IOException;

public class CaptchaUtil {


    // 图片宽度
    private static final int WIDTH = 100;
    // 图片高度
    private static final int HEIGHT = 100;
    // 字体样式
    private static final int FONT_SIZE= Captcha.FONT_1;


    public static Captcha generateGifCaptcha() throws IOException, FontFormatException {
        GifCaptcha captcha = new GifCaptcha(WIDTH, HEIGHT);
        // 设置内置字体
        captcha.setFont(FONT_SIZE);
        captcha.setFont(new Font("楷体", Font.PLAIN, 28));
        return captcha;
    }

    public static Captcha generateChineseCaptcha() throws IOException, FontFormatException {
        ChineseCaptcha captcha = new ChineseCaptcha(WIDTH, HEIGHT);
        // 设置内置字体
        captcha.setFont(FONT_SIZE);
        captcha.setFont(new Font("楷体", Font.PLAIN, 28));
        return captcha;
    }

    public static Captcha generateArithmeticCaptcha() throws IOException, FontFormatException {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(WIDTH, HEIGHT);
        // 设置内置字体
        captcha.setFont(FONT_SIZE);
        captcha.setFont(new Font("楷体", Font.PLAIN, 28));
        return captcha;
    }
}
