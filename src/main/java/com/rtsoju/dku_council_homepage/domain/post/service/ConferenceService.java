package com.rtsoju.dku_council_homepage.domain.post.service;

import com.rtsoju.dku_council_homepage.domain.page.dto.PostSummary;
import com.rtsoju.dku_council_homepage.domain.post.entity.Post;
import com.rtsoju.dku_council_homepage.domain.post.entity.dto.ConferenceDto;
import com.rtsoju.dku_council_homepage.domain.post.entity.subentity.Conference;
import com.rtsoju.dku_council_homepage.domain.post.repository.ConferenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConferenceService {
    private final ConferenceRepository conferenceRepository;

    public Page<ConferenceDto> conferencePage(Pageable pageable) {
        Page<Conference> page = conferenceRepository.findAll(pageable);
        return page.map(ConferenceDto::new);
    }

    public List<PostSummary> postPage() {
        List<Conference> conferenceList = conferenceRepository.findTop5ByOrderByCreateDateDesc();
        return conferenceList.stream().map(Post::summarize).collect(Collectors.toList());
    }

}
