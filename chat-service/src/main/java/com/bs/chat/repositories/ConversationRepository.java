package com.bs.chat.repositories;

import com.bs.chat.entities.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String> {
    /*
        1. Optional<T>
        - Optional là một wrapper trong Java, dùng để bao bọc một giá trị có thể có hoặc không tồn tại.
        - Thay vì trả về null khi không tìm thấy dữ liệu, method sẽ trả về Optional.empty().
        - Lý do dùng Optional:
            + Tránh lỗi NullPointerException.
            + Buộc người gọi phải xử lý tình huống dữ liệu không tồn tại.
        2. @Query("{'participants.userId' : ?0}")
        - Đây là annotation của Spring Data MongoDB, cho phép viết truy vấn MongoDB dạng JSON ngay trong repository.
        - Cú pháp:
            + { 'fieldName': value } → tương tự như filter trong MongoDB shell.
            + ?0 là tham số đầu tiên của method (index bắt đầu từ 0).
            + participants.userId nghĩa là tìm trong mảng participants những phần tử có userId khớp.
    */

    Optional<Conversation> findByParticipantsHash(String hash);

    @Query("{'participants.userId' : ?0}")
    List<Conversation> findAllByParticipantIdsContains(String userId);
}
