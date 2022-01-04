package com.healthclubs.pengke.controller;


import com.healthclubs.pengke.entity.UserInfo;
import com.healthclubs.pengke.exception.PengkeException;
import com.healthclubs.pengke.pojo.ResponseCode;
import com.healthclubs.pengke.pojo.Result;
import com.healthclubs.pengke.pojo.dto.*;
import com.healthclubs.pengke.service.IUserService;
import com.healthclubs.pengke.utils.HttpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Provider;
import java.util.UUID;

@RestController
@Api("WechatLogin")
@RequestMapping("/wechatLogin")
public class WechatLoginController extends BaseController {


    @Autowired
    private IUserService userService;

    /** 版本key */
    @Value("${appsetings.domain}")
    private String dominUrl;

    /**
     * 微信开放平台二维码连接
     */
    private final static String OPEN_QRCODE_URL =
            "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";

    private final static String OPEN_QRCODE_URL2 =
            "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";


    /**
     * 开放平台回调url
     * 注意：域名地址要和在微信端 回调域名配置 地址一直，否则会报回调地址参数错误
     */
    private final static String OPEN_REDIRECT_URL = "https://%s/api/wechatLogin/callback";

    /**
     * 微信审核通过后的appid
     */
    private final static String OPEN_APPID = "wx07c8de022f1d7e42";

    private final static String OPEN_SECRET = "478e4942f87d095f96464215be11d3c8";

    private final static String OPEN_CHECK_URL =
            "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";


    private final static String WIXI_USERINFO_URL =
            "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    //身份请求地址
    private  final static  String OPEN_USERINFO_ACCESS = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    /**
     * 拼装微信扫一扫登录url
     */
    @GetMapping("getQRCodeByCoachid")
    @ResponseBody
    public Result getQRCodeByCoachid()
            throws UnsupportedEncodingException {

        String baseDomain = String.format(OPEN_REDIRECT_URL,dominUrl);
        //官方文档说明需要进行编码
        String callbackUrl = URLEncoder.encode(baseDomain, "GBK"); //进行编码
        //格式化，返回拼接后的url，去调微信的二维码
        String qrcodeUrl = String.format(OPEN_QRCODE_URL, OPEN_APPID, callbackUrl, "State");

        return getResult(ResponseCode.SUCCESS_PROCESSED, qrcodeUrl);
    }

    //注册
    @ApiOperation(value = "/coachLogin", notes = "教练后台登录")
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

            String retrurnSuccess = "0";
            if(result.getErrcode().equals(retrurnSuccess)&& result.getOpenid()!=null)
            {

               // String questUserInfoUrl = String.format(WIXI_USERINFO_URL,result.getSession_key(),result.getOpenid());
              //  WiXinUserInfoDto wiXinUserInfoDto = HttpService.getCurrentService().GetUrl(questUserInfoUrl,WiXinUserInfoDto.class);
                //设置头像性别等
              //  if(wiXinUserInfoDto!=null){
              //      result.setWiXinUserInfoDto(wiXinUserInfoDto);
              //  }

                WiXiFormViewDto wiXiFormViewDto = new WiXiFormViewDto();
                wiXiFormViewDto = userService.wixiLogin(result);
                return getResult(ResponseCode.SUCCESS_PROCESSED, wiXiFormViewDto);

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



    /**
     * 微信回调
     */
    @GetMapping("callback")
    @ResponseBody
    public Result callback(String code,String state) throws IOException {

        String questUrl = String.format(OPEN_CHECK_URL, OPEN_APPID, OPEN_SECRET, code);

        WiXiLoginReturnDto result = HttpService.getCurrentService().GetUrl(questUrl, WiXiLoginReturnDto.class);

        //绑定处理。

        return getResult(ResponseCode.GENERIC_FAILURE);
    }

    //注册
    @ApiOperation(value = "/callUserList", notes = "教练微信端登录")
    @PostMapping(value = "/callUserList")
    public Result callUserList(@RequestHeader  RegisterDto dto) {
        return null;
    }




}
