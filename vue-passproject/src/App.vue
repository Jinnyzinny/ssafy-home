<script setup>
import { RouterLink, RouterView } from "vue-router";
import KakaoMap from "@/components/kakaoMap/KakaoMap.vue";
import MyModal from "@/components/user/MyModal.vue";
import sideBar from "@/components/sidebar/sidebar.vue";
</script>

<template>
  <div class="outer">
    <h3>Vue Teleport Example</h3>
    <div>
      <MyModal />
    </div>
  </div>
  <sideBar/>
  <KakaoMap/>
</template>

<style scoped></style>
