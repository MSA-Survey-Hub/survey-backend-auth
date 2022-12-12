package com.cloud.auth.openfeign;

import com.cloud.auth.dto.FileDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "common-service")
public interface CommonServiceClient {
    @PostMapping(value = "/v1/common/upload/user")
    String uploadFile(FileDTO fileDTO);
}
