package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by www on 2019/8/25.
 */
public interface IFileService {
    public String upload(MultipartFile file, String path);
}
