package cc.mrbird.febs.common.utils;

import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import okhttp3.*;

public class WxMessage {
    final static String GET_OPENID_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=";
    final static String GET_ACCESS_CODE_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    final static String SEND_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?";
    final static String APP_ID = "wx12b49a61dbf0176e";
    final static String SECRET = "85fca9afa69bf6fe7c6e1627e158900f";

    private String accessCode;
    private Double expiresIn = 7200.0;
    private Date createTime;

    public boolean valid(){
        if (accessCode == null){
            return false;
        }

        Date now = new Date();

        if ((now.getTime() - createTime.getTime()) /1000 >= 7200){
            return false;
        }

        return true;
    }

    public String gentWxId(String code) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(GET_OPENID_URL + APP_ID + "&secret=" + SECRET + "&js_code=" + code + "&grant_type=authorization_code")
                .get()
                .build();
        try {
            Response res = client.newCall(request).execute();
            String str = res.body().string();
            Map result = new GsonBuilder().create().fromJson(str, Map.class);
            System.out.println(str);
            return (String) result.get("openid");

        } catch (Exception e) {
            System.out.println("bind failed, error:" + e.getMessage());
            return "";
        }
    }

    public boolean getAccessCode(){
        System.out.println("getAccessCode");
        if (valid()){
            return true;
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(GET_ACCESS_CODE_URL + "&appid=" + APP_ID + "&secret=" + SECRET)
                .get()
                .build();
        try {
            Response res = client.newCall(request).execute();
            String str = res.body().string();
            Map result = new GsonBuilder().create().fromJson(str, Map.class);
            System.out.println(str);
            accessCode = (String) result.get("access_token");
            expiresIn = (Double) result.get("expires_in");
            createTime = new Date();

        } catch (Exception e) {
            System.out.println("get access code failed, error:" + e.getMessage());
            return false;
        }

        return true;
    }


    /**
     * 发送微信订阅号消息
     *
     * @openid 微信用户的id
     * @action  消息类型: 比如资质审核，采购订单，平台公告等
     * @message 消息内容： 比如XXXX供应商更新了资质
     * @querystr 携带数据： 比如供应商的id，格式为 name=value&name=value
     */
    public boolean send(String openid, String type, String message, String querystr){
        System.out.println("send: " + openid +", " + type + ", " + message + ", "+ querystr);

        if (!getAccessCode()){
            return false;
        }
        System.out.println("11111111111111111111111111111");
        Map<String, Object> data = new HashMap<>();
        Map<String, String> thing1 = new HashMap<>();
        Map<String, String> date2 = new HashMap<>();
        Map<String, String> thing3 = new HashMap<>();
        thing1.put("value", message);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss");
        String date = sdf.format(new Date());
        date2.put("value", date);
        thing3.put("value", type);

        data.put("thing1", thing1);
        data.put("date2", date2);
        data.put("thing3", thing3);

        Map<String, Object> parms = new HashMap<>();
        // String openidS=wm.gentWxId(openid);
        parms.put("touser",openid);
        parms.put("template_id", "FhcLfNNK95dnXR_jqdNyR3MK2ctS_FYtxJGlxZQ8vWI");
        parms.put("page", "pages/login/login?" + querystr);
        parms.put("miniprogram_state", "trial");
        parms.put("lang", "zh_CN");
        parms.put("data", data);

        GsonBuilder builder = new GsonBuilder();

        String json = builder.create().toJson(parms);
        RequestBody body = RequestBody.create(MediaType.parse("text/plain; charset=utf-8"), json);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()//创建Request 对象。
                .url(SEND_MESSAGE_URL + "access_token=" + accessCode)
                .post(body)
                .build();
        System.out.println("222222222222222222222222222222");
        try {
            Response result = client.newCall(request).execute();
            String responseContent = result.body().string();
            System.out.println(responseContent);
        } catch (IOException e) {
            System.out.println("send message failed, error:" + e.getMessage());
            return false;
        }

        return true;
    }
}

