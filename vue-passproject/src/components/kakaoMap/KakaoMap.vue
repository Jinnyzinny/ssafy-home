<script setup>
import { ref, watch } from "vue";
import { useMapStore } from "@/stores/mapStore";

const mapStore = useMapStore();
const center = ref({ lat: 37.5665, lng: 126.978 }); // 초기 지도 중심 좌표
const markerPosition = ref(null); // 초기 마커 위치는 null

watch(
  () => mapStore.markerPosition, //mapStore에 marker를 띄울 주소로 이동
  (newPosition) => {
    if (newPosition) {
      map.value.panTo(new kakao.maps.LatLng(newPosition.lat, newPosition.lng));
      center.value = newPosition; // 지도 중심을 새로운 위치로 변경
      markerPosition.value = newPosition; // 마커 위치도 업데이트
      customOverlayVisible.value = true;
    }
  }
);

import { KakaoMap, KakaoMapMarker, KakaoMapCustomOverlay } from "vue3-kakao-maps";
const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
};

const customOverlayVisible = ref(false);

const onClickKakaoMapMarker = () => {
  customOverlayVisible.value = !customOverlayVisible.value;
};

import detailModal from "@/components/kakaoMap/detailModal.vue";

const detailModalVisible = ref(false);
const detailModalOpen = () => {
  detailModalVisible.value = true;
  console.log(detailModalVisible.value);
};
const detailModalClose = () => {
  detailModalVisible.value = false;
  console.log(detailModalVisible.value);
};
</script>

<template>
  <KakaoMap
    id="map"
    width="100%"
    height="100vh"
    :lat="center.lat"
    :lng="center.lng"
    @onLoadKakaoMap="onLoadKakaoMap"
    :draggable="true"
  >
    <!-- 동적으로 마커 렌더링 -->
    <KakaoMapMarker
      :lat="center.lat"
      :lng="center.lng"
      @on-click-kakao-map-marker="onClickKakaoMapMarker"
      :clickable="true"
    />
    <KakaoMapCustomOverlay
      :lat="center.lat"
      :lng="center.lng"
      :visible="customOverlayVisible"
      :yAnchor="1.4"
      ><div
        style="
          padding: 10px;
          background-color: white;
          border: 1px solid #ccc;
          border-radius: 5px;
          display: flex;
          flex-direction: column;
          align-items: flex-start;
        "
      >
        <div style="font-weight: bold; margin-bottom: 5px">
          아파트 이름 : {{ mapStore.aptName }} <br />매매 거래액 : {{ mapStore.dealAmount }}억<br />
          아파트 주소 : {{ mapStore.address }}<br />
          <button class="btn-primary" @click="detailModalOpen">매매가 변화 추이&세부 정보 보기</button>
        </div>
      </div>
    </KakaoMapCustomOverlay> </KakaoMap
  ><detailModal v-if="detailModalVisible" :is-visible="detailModalVisible" @close="detailModalClose">
  </detailModal>
</template>
<style scoped>
#map {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 0;
}
.btn-primary {
  background-color: #007bff;
  color: white;
}
</style>
