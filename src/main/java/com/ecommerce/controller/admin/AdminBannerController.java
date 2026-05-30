package com.ecommerce.controller.admin;


import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.entity.Banner;
import com.ecommerce.service.interfaces.IBannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/banners")
@RequiredArgsConstructor
public class AdminBannerController {

    private final IBannerService bannerService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponseDto<Banner>>
            addBanner(@RequestBody Banner banner) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Banner added!",
                bannerService.addBanner(banner)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDto<Banner>>
            updateBanner(@PathVariable Long id,
                @RequestBody Banner banner) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Banner updated!",
                bannerService.updateBanner(
                    id, banner)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            deleteBanner(@PathVariable Long id) {
        bannerService.deleteBanner(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Banner deleted!", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<Banner>>>
            getAllBanners() {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                bannerService.getAllBanners()));
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            activate(@PathVariable Long id) {
        bannerService.activateBanner(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Banner activated!", null));
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            deactivate(@PathVariable Long id) {
        bannerService.deactivateBanner(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Banner deactivated!", null));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponseDto<List<Banner>>>
            getActiveBanners() {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                bannerService.getActiveBanners()));
    }
}