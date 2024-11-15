<script setup>
import { RouterLink, RouterView } from "vue-router";
import KakaoMap from "@/components/kakaoMap/KakaoMap.vue";
<<<<<<< Updated upstream
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
=======
import modalSet from "@/components/user/ModalSet.vue";
import navTab from "./components/nav-tab/nav-tab.vue";
// import sideBar from "@/components/sidebar/sidebar.vue";
</script>

<template>
  <navTab/>
  <!-- <sideBar /> -->
  <KakaoMap />
  <modalSet />
>>>>>>> Stashed changes
</template>

<style scoped></style>
