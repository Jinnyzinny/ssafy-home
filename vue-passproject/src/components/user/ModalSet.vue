<script setup>
import { ref, computed } from "vue";
import login from "./login.vue";
import join from "./join.vue";

import { useUserStore } from "@/stores/userStore"; // Pinia 스토어 사용
import UserInfo from "./userInfo.vue";
const userStore = useUserStore();

const isJoinVisible = ref(false); // 회원가입 모달 표시 여부
const isLoginVisible = ref(false); // 로그인 모달 표시 여부
const isUserInfoVisible = ref(false);

// JWT 토큰 확인을 통해 로그인 여부 계산
const isLogin = computed(() => {
  return userStore.getjwtToken() !== null; // JWT 토큰이 있으면 로그인 상태
});

// 게시판 이동 함수
const goToBoard = () => {
  window.location.href = "http://localhost:5000/board/list.html";
};
const userEdit = () => {
  window.location.href = "http://localhost:5000/user/edit.html";
};
const gotoLogin = () => {
  window.location.href = "http://localhost:5000/user/login.html";
};
</script>

<template>
  <div id="top-right-buttons">
    <template v-if="isLogin">
      <!-- 로그인 상태 -->
      <button class="btn btn-primary" @click="goToBoard">게시판</button>
      <button class="btn btn-danger" @click="userStore.logout">로그아웃</button>
      <button class="btn btn-primary" @click="userEdit">회원 정보</button>
    </template>
    <template v-else>
      <!-- 비로그인 상태 -->
      <!-- <login @click="isLoginVisible = true" isLoginVisible="isLoginVisible"
        >로그인</login
      > -->
      <button class="btn btn-primary" @click="gotoLogin">로그인</button>

      <join @click="isJoinVisible = true" isJoinVisible="isJoinVisible"
        >회원가입</join
      >
    </template>
  </div>
</template>

<style scoped>
#top-right-buttons {
  position: absolute;
  top: 20px;
  right: 20px;
  z-index: 3;
  display: flex;
  gap: 10px;
}

.btn-primary {
  background-color: #007bff;
  color: white;
}

.btn-success {
  background-color: #28a745;
  color: white;
}

.btn-danger {
  background-color: #dc3545;
  color: white;
}
</style>
