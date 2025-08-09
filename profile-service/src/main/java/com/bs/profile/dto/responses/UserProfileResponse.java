package com.bs.profile.dto.responses;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileResponse {
    String id;
    String userId;
    String username;
    String avatar;
    String email;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    String city;
}