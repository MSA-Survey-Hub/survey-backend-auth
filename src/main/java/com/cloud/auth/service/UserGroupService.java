package com.cloud.auth.service;

public interface UserGroupService {
    void participateGroup(String userId, Integer groupId);

    void unparticipateGroup(String userId, Integer groupId);

    boolean isParticipated(String userId, Integer groupId);


}
