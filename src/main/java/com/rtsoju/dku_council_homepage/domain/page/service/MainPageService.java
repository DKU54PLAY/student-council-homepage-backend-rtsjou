package com.rtsoju.dku_council_homepage.domain.page.service;

import com.rtsoju.dku_council_homepage.common.Messages;
import com.rtsoju.dku_council_homepage.common.nhn.service.NHNAuthService;
import com.rtsoju.dku_council_homepage.common.nhn.service.ObjectStorageService;
import com.rtsoju.dku_council_homepage.domain.page.dto.PostSummary;
import com.rtsoju.dku_council_homepage.domain.page.entity.CarouselImage;
import com.rtsoju.dku_council_homepage.domain.page.repository.CarouselImageRepository;
import com.rtsoju.dku_council_homepage.domain.post.service.ConferenceService;
import com.rtsoju.dku_council_homepage.domain.post.service.NewsService;
import com.rtsoju.dku_council_homepage.domain.post.service.PetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageService {
    private final NHNAuthService nhnAuthService;
    private final ObjectStorageService s3service;
    private final ConferenceService conferenceService;
    private final NewsService newsService;
    private final PetitionService petitionService;
    private final CarouselImageRepository carouselImageRepository;

    public List<String> getCarouselImageURLs() {
        return carouselImageRepository.findAll().stream()
                .map((image) -> s3service.getObjectURL(image.getFileId()))
                .collect(Collectors.toList());
    }

    public void addCarouselImage(MultipartFile file) throws IOException {
        String fileId = "carousel-" + UUID.randomUUID();
        String token = nhnAuthService.requestToken();
        s3service.uploadObject(token, fileId, file.getInputStream());
        carouselImageRepository.save(new CarouselImage(fileId));
    }

    public void deleteCarouselImage(Long carouselId) {
        String token = nhnAuthService.requestToken();
        Optional<CarouselImage> image = carouselImageRepository.findById(carouselId);
        if (image.isEmpty()) {
            throw new IllegalArgumentException(Messages.ERROR_INVALID_ID.getMessage());
        }
        s3service.deleteObject(token, image.get().getFileId());
        carouselImageRepository.deleteById(carouselId);
    }

    /**
     * ?????? ????????? ????????? ????????????. (5???)
     */
    public List<PostSummary> getRecentConferences() {
        return conferenceService.postPage();
    }

    /**
     * ?????? ?????? ?????? ????????? ????????????. (5???)
     */
    public List<PostSummary> getRecentNews() {
        return newsService.postPage();
    }

    /**
     * ???????????? ???????????? ????????? ????????????. (n???) ?????? ???????????? ???????????? ??????
     * ?????? ?????? ????????? ???????????? (5???)
     */
    public List<PostSummary> getPopularPetitions() {
        return petitionService.postPage();
    }
}
