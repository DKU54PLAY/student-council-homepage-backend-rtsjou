package com.rtsoju.dku_council_homepage.domain.post.service;

import com.rtsoju.dku_council_homepage.common.jwt.JwtProvider;
import com.rtsoju.dku_council_homepage.domain.page.dto.PostSummary;
import com.rtsoju.dku_council_homepage.domain.post.entity.Post;
import com.rtsoju.dku_council_homepage.domain.post.entity.dto.ConferenceDto;
import com.rtsoju.dku_council_homepage.domain.post.entity.dto.request.RequestAnnounceDto;
import com.rtsoju.dku_council_homepage.domain.post.entity.dto.request.RequestConferenceDto;
import com.rtsoju.dku_council_homepage.domain.post.entity.dto.response.IdResponseDto;
import com.rtsoju.dku_council_homepage.domain.post.entity.subentity.Conference;
import com.rtsoju.dku_council_homepage.domain.post.repository.ConferenceRepository;
import com.rtsoju.dku_council_homepage.domain.user.model.entity.User;
import com.rtsoju.dku_council_homepage.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ConferenceService {
    private final ConferenceRepository conferenceRepository;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    public Page<ConferenceDto> conferencePage(Pageable pageable){
        Page<Conference> page = conferenceRepository.findAll(pageable);
        return page.map(ConferenceDto::new);
    }

    //최근 5개 페이지 가져오기
    public List<PostSummary> postPage(){
        List<Conference> conferenceList = conferenceRepository.findTop5ByOrderByCreateDateDesc();
        return conferenceList.stream().map(Post::summarize).collect(Collectors.toList());
    }


//    public IdResponseDto createConference(long userId, RequestConferenceDto data) {
//        Optional<User> user = userRepository.findById(userId);
//        Conference conference = new Conference()
//    }
}