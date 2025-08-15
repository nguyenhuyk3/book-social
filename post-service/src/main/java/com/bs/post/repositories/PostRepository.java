package com.bs.post.repositories;

import com.bs.post.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
    /*
        Pageable — yêu cầu phân trang
        Là một interface trong Spring Data, dùng để mô tả:
            - Trang số mấy (page number)
            - Mỗi trang bao nhiêu phần tử (page size)
            - Sắp xếp như thế nào (sort order)
        VD: Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
    */
    Page<Post> findAllByUserId(String userId, Pageable pageable);
}