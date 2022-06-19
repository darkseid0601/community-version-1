package com.nowcoder.community;

import com.qcloud.cos.COSClient;

import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;

import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;

/**
 * @BelongsProject: community-version-1
 * @BelongsPackage: com.nowcoder.community
 * @CreateTime: 2022-06-19  22:47
 * @Description:
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityVersion1Application.class)
public class CosTest {
    // 1 初始化用户身份信息(secretId, secretKey)
    static COSCredentials cred = new BasicCOSCredentials("AKIDsXw4vVovlKcdNq2xbz6wVnoXzB5i49wS", "GFxQcYDbiY7PQAdpjMinb8ON5WgZBWaM");
    // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
    // clientConfig中包含了设置region, https(默认http), 超时, 代理等set方法, 使用可参见源码或者接口文档FAQ中说明
    static ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
    // 3 生成cos客户端
    static COSClient cosClient = new COSClient(cred, clientConfig);
    // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
    static String bucketName = "bucket-1306580544";
    // 指定要上传到 COS 上对象键
    // 对象键（Key）是对象在存储桶中的唯一标识。例如，在对象的访问域名 `bucket1-1250000000.cos.ap-chengdu.myqcloud.com/mydemo.jpg` 中，对象键为 mydemo.jpg, 详情参考 [对象键](https://cloud.tencent.com/document/product/436/13324)
    static  String key = "mydemo.jpg";

    public static String upload(){
        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20M以下的文件使用该接口
        // 大文件上传请参照 API 文档高级 API 上传
        //file里面填写本地图片的位置 我这里是相对项目的位置，在项目下有src/test/demo.jpg这张图片
        File localFile = new File("D:/Java_software/data/Windows 11.png");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        String etag = putObjectResult.getETag();  // 获取文件的 etag
        return etag;
    }

    public static void download(){
        // 设置要下载到的本地路径
        File downFile = new File("D:/Java_software/data/medemo.jpg");
        // 设置要下载的文件所在的 对象桶的名称 和对象键
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);
    }

    public static void del(){
        // 指定要删除的 bucket 和对象键
        cosClient.deleteObject(bucketName, key);
    }

    //main方法中测试
    public static void main(String[] args) {
//        System.out.println(upload());
//        download();
         del();
    }


}
