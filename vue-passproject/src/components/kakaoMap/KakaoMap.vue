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
      center.value = newPosition; // 지도 중심을 새로운 위치로 변경
      markerPosition.value = newPosition; // 마커 위치도 업데이트
    }
  }
);

import { KakaoMap, KakaoMapMarker, KakaoMapInfoWindow } from "vue3-kakao-maps";

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
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
    z-index="0"
  >
    <!-- 동적으로 마커 렌더링 -->
    <KakaoMapMarker
      v-if="markerPosition"
      :position="markerPosition"
    ></KakaoMapMarker>
  </KakaoMap>
</template>
<style scoped>
#map {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 1;
}
</style>
