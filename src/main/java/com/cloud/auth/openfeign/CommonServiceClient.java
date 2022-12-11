package com.cloud.auth.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@FeignClient(name = "common-service")
public interface CommonServiceClient {
    @PostMapping(value = "/v1/common/upload/user", headers = {"Content-Type=multipart/form-data"})
    String uploadFile(MultipartFile multipartFile);
}
