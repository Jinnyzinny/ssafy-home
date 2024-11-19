<script setup>
import { ref } from "vue";
import { RouterLink, RouterView } from "vue-router";
import KakaoMap from "@/components/kakaoMap/KakaoMap.vue";
import modalSet from "@/components/user/ModalSet.vue";
import navTab from "./components/nav-tab/nav-tab.vue";
import bottom_right_buttons from "@/components/bottom-right-buttons/bottom-right-buttons.vue";
import Chatbot from "./components/bottom-right-buttons/chatbot.vue";
import Favorites from "./components/bottom-right-buttons/favorites.vue";
import Message from "./components/bottom-right-buttons/message.vue";
// import sideBar from "@/components/sidebar/sidebar.vue";
const chatbotVisible = ref(false);
const messageVisible = ref(false);
const favoritesVisible = ref(false);
const sideBarVisible = ref(false);
const sideBarToggle = () => {
  sideBarVisible.value = !sideBarVisible.value;
};
</script>

<template>
  <navTab />
  <!-- <button class="btn-primary" @click="sideBarToggle" id="sideBarToggle">사이드바</button> -->
  <!-- <sideBar /> -->
  <!-- <KakaoMap /> -->
  <modalSet />
  <Chatbot v-if="chatbotVisible" id="chatbot"></Chatbot>
  <Message v-if="messageVisible"></Message>
  <Favorites v-if="favoritesVisible"></Favorites>
  <bottom_right_buttons
    v-model:chatbotVisible="chatbotVisible"
    v-model:messageVisible="messageVisible"
    v-model:favoritesVisible="favoritesVisible"
  ></bottom_right_buttons>
  <!-- 이 부분이 하단 병원 지하철 버튼의 구현부 -->
</template>

<style scoped>
#sideBarToggle {
  z-index: 99;
}
</style>
