package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstanPropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = ConstanPropertiesUtils.END_POIND;
        String accessKeyId = ConstanPropertiesUtils.ACCESS_KEY_ID;
        String accessSecret = ConstanPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstanPropertiesUtils.BUCKET_NAME;

        try {
            //创建oss实例
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessSecret);

            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();

            String datePath = new DateTime().toString("yyyy/MM/dd");
            String UUID = java.util.UUID.randomUUID().toString().replaceAll("-", "");
            fileName = datePath + "/" + UUID + fileName;

            //调用oss方法实现上传
            //第一个名称bucket 名称
            //第二个参数 上传到oss文件路径和文件名称 /aa/bb/1.jpg
            //第三个参数 文件上传输入流
            ossClient.putObject(bucketName, fileName, inputStream);
            //关闭ossClient
            ossClient.shutdown();

            //把上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("OSS uploadFileAvatar error " + e.getMessage());
            return null;
        }
    }
}
