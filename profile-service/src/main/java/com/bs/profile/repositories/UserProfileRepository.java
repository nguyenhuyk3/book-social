package com.bs.profile.repositories;

import com.bs.profile.entities.UserProfile;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends Neo4jRepository<UserProfile, String> {
}
