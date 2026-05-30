package com.ecommerce.service.interfaces;

import com.ecommerce.entity.Banner;
import com.ecommerce.entity.Banner.BannerType;
import java.util.List;

public interface IBannerService {

    // Admin CRUD
    Banner addBanner(Banner banner);
    Banner updateBanner(Long id, Banner banner);
    void deleteBanner(Long id);
    Banner getBannerById(Long id);
    List<Banner> getAllBanners();

    // User
    List<Banner> getActiveBanners();
    List<Banner> getBannersByType(BannerType type);

    // Admin
    void activateBanner(Long id);
    void deactivateBanner(Long id);
    void updateDisplayOrder(Long id, Integer order);
}
