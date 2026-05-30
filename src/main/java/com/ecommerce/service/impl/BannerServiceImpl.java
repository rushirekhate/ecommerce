package com.ecommerce.service.impl;

import com.ecommerce.entity.Banner;
import com.ecommerce.entity.Banner.BannerType;
import com.ecommerce.repository.BannerRepository;
import com.ecommerce.service.interfaces.IBannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl 
    implements IBannerService {

    private final BannerRepository bannerRepository;

    @Override
    public Banner addBanner(Banner banner) {
        return bannerRepository.save(banner);
    }

    @Override
    public Banner updateBanner(
            Long id, Banner updated) {
        Banner banner = getBannerById(id);
        banner.setTitle(updated.getTitle());
        banner.setImageUrl(updated.getImageUrl());
        banner.setLink(updated.getLink());
        banner.setDescription(updated.getDescription());
        banner.setType(updated.getType());
        banner.setDisplayOrder(
            updated.getDisplayOrder());
        banner.setStartDate(updated.getStartDate());
        banner.setEndDate(updated.getEndDate());
        return bannerRepository.save(banner);
    }

    @Override
    public void deleteBanner(Long id) {
        bannerRepository.deleteById(id);
    }

    @Override
    public Banner getBannerById(Long id) {
        return bannerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                "Banner not found!"));
    }

    @Override
    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

    @Override
    public List<Banner> getActiveBanners() {
        return bannerRepository
            .findByIsActiveTrueOrderByDisplayOrderAsc();
    }

    @Override
    public List<Banner> getBannersByType(
            BannerType type) {
        return bannerRepository
            .findByTypeAndIsActiveTrue(type);
    }

    @Override
    public void activateBanner(Long id) {
        Banner banner = getBannerById(id);
        banner.setActive(true);
        bannerRepository.save(banner);
    }

    @Override
    public void deactivateBanner(Long id) {
        Banner banner = getBannerById(id);
        banner.setActive(false);
        bannerRepository.save(banner);
    }

    @Override
    public void updateDisplayOrder(
            Long id, Integer order) {
        Banner banner = getBannerById(id);
        banner.setDisplayOrder(order);
        bannerRepository.save(banner);
    }
}
