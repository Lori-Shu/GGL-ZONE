// import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
// import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
// import com.ggl.cloud.message_util.util;

// import org.junit.jupiter.api.Test;



// /*
// *
// *@Date:2022年5月05日
// *
// *@Author:Lori Shu
// *
// */
// public class TestMessage {
//     @Test
//     public void  registy() {
// 
//             // java.util.List<String> args = java.util.Arrays.asList(args_);
//             com.aliyun.dysmsapi20170525.Client client=null;
//             SendSmsRequest sendSmsRequest=null;
//             SendSmsResponse sendSmsResponse=null;
//             try{
//             client = util.createClient("LTAI5tFj9n6NHNVGYGLYBBcq", "ViDr00ZL9OPQXrSOBc81nPA1c8Uzu3");
//             sendSmsRequest = new SendSmsRequest()
//                     .setSignName("阿里云短信测试")
//                     .setTemplateCode("SMS_154950909")
//                     .setPhoneNumbers("18674530792")
//                     .setTemplateParam("{\"code\":\"1234\"}");
//             sendSmsResponse= client.sendSms(sendSmsRequest);
//             System.out.println(sendSmsResponse.getBody().getMessage());
//             }catch(Exception e){
//                 e.printStackTrace();
//                 // return "";
//             }
//             // 复制代码运行请自行打印 API 的返回值
//         // return "";
//     }
    
// }
