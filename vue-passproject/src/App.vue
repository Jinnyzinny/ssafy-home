<script setup>
import { RouterLink, RouterView } from "vue-router";
import KakaoMap from "@/components/kakaoMap/KakaoMap.vue";
import modalSet from "@/components/user/ModalSet.vue";
import sideBar from "@/components/sidebar/sidebar.vue";
</script>

<template>
  <div class="outer">
    <h3>Vue Teleport Example</h3>
    <div></div>
  </div>
  <sideBar />
  <KakaoMap />
  <modalSet />
</template>

<style scoped></style>
