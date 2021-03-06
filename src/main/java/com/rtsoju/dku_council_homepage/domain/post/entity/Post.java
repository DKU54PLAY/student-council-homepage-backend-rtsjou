package com.rtsoju.dku_council_homepage.domain.post.entity;

import com.rtsoju.dku_council_homepage.domain.base.BaseEntity;
import com.rtsoju.dku_council_homepage.domain.page.dto.PostSummary;
import com.rtsoju.dku_council_homepage.domain.user.model.entity.User;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Getter
@DynamicUpdate //게시글 수정시..
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String title;

    @Column
    @Lob
    private String text;

    private String fileUrl;

    @OneToMany(mappedBy = "post")
    List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    List<PostHit> postHits = new ArrayList<>();

    public Post(String title, String text){
        this.title = title;
        this.text = text;
    }

    public PostSummary summarize(){
        return new PostSummary(id, title);
    }
}

