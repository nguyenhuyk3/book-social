package com.bs.profile.entities;

import java.time.LocalDate;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Node("user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    String id;

    /*
            @Property("userId") → Dùng để ánh xạ tên trường Java (userId) với tên cột/trường trong database
        (hoặc document DB) là "userId".
    */
    @Property("userId")
    String userId;

    String avatar;

    String username;
    String email;

    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    String city;
}
