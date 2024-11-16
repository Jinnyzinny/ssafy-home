<script setup>
import { RouterLink, RouterView } from "vue-router";
import KakaoMap from "@/components/kakaoMap/KakaoMap.vue";
import modalSet from "@/components/user/ModalSet.vue";
import navTab from "./components/nav-tab/nav-tab.vue";
import bottom_right_buttons from "@/components/bottom-right-buttons/bottom-right-buttons.vue";
// import sideBar from "@/components/sidebar/sidebar.vue";
</script>

<template>
  <navTab />
  <!-- <sideBar /> -->
  <KakaoMap />
  <modalSet />
  <bottom_right_buttons /><!--이 부분이 하단 병원 지하철 버튼의 구현부-->
</template>

<style scoped></style>
