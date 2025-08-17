package com.bs.file.dto.responses;

import org.springframework.core.io.Resource;

/*
    - Record là một kiểu dữ liệu bất biến (immutable) dùng để gom các giá trị với nhau (kiểu như DTO).
    - Khi bạn viết record, Java sẽ tự động sinh ra:
        + Constructor với tất cả tham số
        + Getter (không phải getX() mà tên đúng bằng component, vd: contentType())
        + equals() / hashCode() / toString()
        👉 Giúp bạn tiết kiệm code so với class thông thường.
    - Lưu ý:
        + Các field trong record là final, không thay đổi được sau khi tạo.
        + Record phù hợp cho DTO, value object, response model.
 */
public record FileData(String contentType, Resource resource) {}