<script setup>
import { RouterLink, RouterView } from "vue-router";
import KakaoMap from "@/components/kakaoMap/KakaoMap.vue";
import modalSet from "@/components/user/ModalSet.vue";
import navTab from "./components/nav-tab/nav-tab.vue";
// import sideBar from "@/components/sidebar/sidebar.vue";
</script>

<template>
  <navTab/>
  <!-- <sideBar /> -->
  <KakaoMap />
  <modalSet />
</template>

<style scoped></style>
