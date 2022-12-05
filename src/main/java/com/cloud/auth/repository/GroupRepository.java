package com.cloud.auth.repository;

import com.cloud.auth.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    // 그룹 상세 정보 조회
    Group findByGroupId(Integer groupId);

    // 존재하는 모든 그룹 정보 조회
    @Query("select g from Group g where g.delYn='N'")
    Page<Group> findExistGroup(Pageable pageable);

    // 그룹 삭제 처리
    @Modifying
    @Transactional
    @Query(value = "UPDATE Group g SET g.delYn = 'Y' WHERE g.groupId =:groupId")
    void updateGroupDelY(Integer groupId);

    // 해당 UserId의 그룹 정보 조회
    @Query(value = "SELECT g FROM Group g WHERE (g.user.userId = :userId) AND (g.delYn = 'N')")
    Group findByUserId(String userId);


    // 해당 groupName의 그룹 정보 조회
    @Query(value = "SELECT g FROM Group g WHERE (g.groupName = :groupName) AND (g.delYn = 'N')")
    Group findByGroupName(String groupName);

    // 그룹 참여자 추가
    @Modifying
    @Transactional
    @Query("UPDATE Group g SET g.groupCnt = g.groupCnt + 1 WHERE g.groupId =:groupId")
    void updateGroupCnt(Integer groupId);

}
