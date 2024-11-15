<script setup>
import bottom_right_buttons from "@/components/bottom-right-buttons/bottom-right-buttons.vue";
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";
const coordinate = {
  lat: 33.450701,
  lng: 126.570667,
};
</script>

<template>
  <KakaoMap
    id="map"
    width="100%"
    height="100vh"
    :lat="coordinate.lat"
    :lng="coordinate.lng"
    :draggable="true"
    z-index="0"
  >
    <KakaoMapMarker :lat="coordinate.lat" :lng="coordinate.lng"></KakaoMapMarker>
  </KakaoMap>

  <bottom_right_buttons /><!--이 부분이 하단 병원 지하철 버튼의 구현부-->
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
