<script setup>
import { useApartmentStore } from "@/stores/useApartmentStore";
import { ref } from "vue";
const apartmentStore = useApartmentStore();

import { KakaoMap, KakaoMapMarker, KakaoMapInfoWindow } from "vue3-kakao-maps";
const coordinate = {
  lat: 33.450701,
  lng: 126.570667,
};
const map = ref();
const latRange = [0, 180];
const lngRange = [0, 100];

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
};

const changeLatLng = () => {
  lat.value = Math.random() * (latRange[1] - latRange[0]) + latRange[0];
  lng.value = Math.random() * (lngRange[1] - lngRange[0]) + lngRange[0];
};

const setCenter = () => {
  if (map.value) {
    map.value.setCenter(new KakaoMap.maps.LatLng());
  }
};

const panTo = () => {
  if (map.value) {
    map.value.panTo(new kakao.maps.LatLng());
  }
};

function viewMap(apt, dealAmount, address) {}
</script>

<template>
  <KakaoMap
    id="map"
    width="100%"
    height="100vh"
    :lat="coordinate.lat"
    :lng="coordinate.lng"
    @onLoadKakaoMap="onLoadKakaoMap"
    :draggable="true"
    z-index="0"
  >
    <KakaoMapMarker :lat="coordinate.lat" :lng="coordinate.lng"></KakaoMapMarker>
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
