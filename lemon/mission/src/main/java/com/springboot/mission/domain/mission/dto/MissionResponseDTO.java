package com.springboot.mission.domain.mission.dto;

import com.springboot.mission.domain.review.dto.ReviewResponseDTO;
import com.springboot.mission.domain.store.entity.Store;
import lombok.Builder;

import java.util.List;

import com.springboot.mission.domain.mission.entity.Mission;
import lombok.Builder;
import java.util.List;

public class MissionResponseDTO {

    @Builder
    public record GetMissionInfo(
            Long mission_id,
            String store_name,
            String category,
            String mission_content,
            Boolean my_mission
    ){
        /**
         * Entity -> DTO 변환 (단일 카테고리 구조 반영)
         */
        public static GetMissionInfo from(Mission mission, Boolean isMyMission) {
            Store store = mission.getStore();

            // Store에서 바로 Category 엔티티에 접근하여 문자열 필드 'category'를 가져옴
            String categoryName = (store.getCategory() != null)
                    ? store.getCategory().getCategory()
                    : "미지정";

            return GetMissionInfo.builder()
                    .mission_id(mission.getId())
                    .store_name(store.getTitle())
                    .category(categoryName)
                    .mission_content(mission.getContent())
                    .my_mission(isMyMission)
                    .build();
        }
    }

    @Builder
    public record MissionListResponse(
            List<GetMissionInfo> content,
            Integer total_mission,
            Integer page_offset
    ) {
    }

    @Builder
    public record MyMissionInfo(
            Long mission_id,
            String store_name,
            String mission_content,
            Integer score,
            Boolean state // 도전 중(false) / 완료(true) 상태
    ) {
        /**
         * 마이페이지 '내 미션' 조회를 위한 매핑
         */
        public static MyMissionInfo from(Mission mission, Boolean isCleared) {
            return MyMissionInfo.builder()
                    .mission_id(mission.getId())
                    .store_name(mission.getStore().getTitle())
                    .mission_content(mission.getContent())
                    .score(mission.getScore())
                    .state(isCleared)
                    .build();
        }
    }

    @Builder
    public record MyMissionListResponse(
            List<MyMissionInfo> content,
            Integer total_mission,
            Integer page_offset
    ) {}
}
