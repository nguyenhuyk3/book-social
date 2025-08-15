package com.bs.file.repositories;

import com.bs.file.dto.responses.FileInfo;
import com.bs.file.entities.FileMgmt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Repository
public class FileRepository {
    @Value("${app.file.storageDir}")
    String STORAGE_DIR;

    @Value("${app.file.downloadPrefix}")
    String DOWNLOAD_PREFIX;

    public FileInfo store(MultipartFile file) throws IOException {
        Path folder = Paths.get(STORAGE_DIR);
        String fileExtension = StringUtils
                .getFilenameExtension(file.getOriginalFilename());
        String fileName = Objects.isNull(fileExtension)
                ? UUID.randomUUID().toString()
                : UUID.randomUUID() + "." + fileExtension;
        /*
            folder.resolve(fileName)
                - Mục đích: nối đường dẫn thư mục (folder) với tên file (fileName).
            .normalize()
                - Mục đích: chuẩn hóa đường dẫn, loại bỏ những phần dư thừa như:
                    + . (thư mục hiện tại)
                    + .. (thư mục cha)
                    + Các dấu / hoặc \ thừa.
                - Ví dụ:
                    + "/uploads/./image.png" → "/uploads/image.png"
                    + "/uploads/a/../image.png" → "/uploads/image.png"
        */
        Path filePath = folder.resolve(fileName).normalize().toAbsolutePath();

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return FileInfo
                .builder()
                .name(fileName)
                .size(file.getSize())
                .contentType(file.getContentType())
                .md5Checksum(DigestUtils.md5DigestAsHex(file.getInputStream()))
                .path(filePath.toString())
                .url(DOWNLOAD_PREFIX + fileName)
                .build();
    }

    public Resource read(FileMgmt fileMgmt) throws IOException {
        var data = Files.readAllBytes(Path.of(fileMgmt.getPath()));

        return new ByteArrayResource(data);
    }
}