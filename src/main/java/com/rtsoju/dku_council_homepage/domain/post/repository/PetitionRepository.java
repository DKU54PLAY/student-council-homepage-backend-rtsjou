package com.rtsoju.dku_council_homepage.domain.post.repository;

import com.rtsoju.dku_council_homepage.domain.base.PetitionStatus;
import com.rtsoju.dku_council_homepage.domain.post.entity.subentity.News;
import com.rtsoju.dku_council_homepage.domain.post.entity.subentity.Petition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PetitionRepository extends JpaRepository<Petition, Long> {
    // 상속 받으면 타입 캐스팅이 되는가?

    //pagination이랑 fetchjoin은 같이 쓰면 안됨..
    Page<Petition> findAllByStatus(PetitionStatus status, Pageable pageable);


    List<Petition> findTop5ByOrderByCreateDateDesc();

    Optional<Petition> findByTitle(String title);

    @Modifying
    @Query("update Petition p set p.status = (:after) where p.createDate <= :now and p.status =(:before)")
    int bulkStatusChange(@Param("after")PetitionStatus after, @Param("now")LocalDateTime now, @Param("before")PetitionStatus before);

}