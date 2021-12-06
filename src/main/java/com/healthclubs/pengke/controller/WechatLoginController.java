package com.healthclubs.pengke.controller;


import com.healthclubs.pengke.exception.PengkeException;
import com.healthclubs.pengke.pojo.ResponseCode;
import com.healthclubs.pengke.pojo.Result;
import com.healthclubs.pengke.pojo.dto.WiXiLoginReturnDto;
import com.healthclubs.pengke.utils.HttpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@Api("WechatLogin")
@RequestMapping("/api/wechatLogin")
public class WechatLoginController extends BaseController {

    /**
     * 微信开放平台二维码连接
     */
    private final static String OPEN_QRCODE_URL =
            "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";

    /**
     * 开放平台回调url
     * 注意：test16web.tunnel.qydev.com 域名地址要和在微信端 回调域名配置 地址一直，否则会报回调地址参数错误
     */
    private final static String OPEN_REDIRECT_URL = "http:///api/wechatLogin/callback1";


    /**
     * 微信审核通过后的appid
     */
    private final static String OPEN_APPID = "wx07c8de022f1d7e42";

    private final static String OPEN_SECRET = "478e4942f87d095f96464215be11d3c8";


    private final static String OPEN_CHECK_URL =
            "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";


    /**
     * 拼装微信扫一扫登录url
     */
    @GetMapping("login_url")
    @ResponseBody
    public Result loginUrl(@RequestParam(value = "access_page", required = true) String accessPage)
            throws UnsupportedEncodingException {

        //官方文档说明需要进行编码
        String callbackUrl = URLEncoder.encode(OPEN_REDIRECT_URL, "GBK"); //进行编码

        //格式化，返回拼接后的url，去调微信的二维码
        String qrcodeUrl = String.format(OPEN_QRCODE_URL, OPEN_APPID, callbackUrl, accessPage);


        return getResult(ResponseCode.SUCCESS_PROCESSED, qrcodeUrl);


    }


    //注册
    @ApiOperation(value = "/coachLogin", notes = "根据教练id得到学员id")
    @PostMapping(value = "/coachLogin")
    public Result coachLogin(String coachId) {
        try {
            return getResult(ResponseCode.SUCCESS_PROCESSED, 1
            );
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }


    //注册
    @ApiOperation(value = "/coachWeixiLogin", notes = "教练微信端登录")
    @PostMapping(value = "/coachWeixiLogin")
    public Result coachWeixiLogin(String code) {
        try {

            String questUrl = String.format(OPEN_CHECK_URL, OPEN_APPID, OPEN_SECRET, code);

            WiXiLoginReturnDto result = HttpService.getCurrentService().GetUrl(questUrl, WiXiLoginReturnDto.class);

            if(result.getErrcode() == "0")
            {

                return getResult(ResponseCode.SUCCESS_PROCESSED, result
                );
            }
            else
            {
                return getResult(ResponseCode.GENERIC_FAILURE);

            }

        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }


}
