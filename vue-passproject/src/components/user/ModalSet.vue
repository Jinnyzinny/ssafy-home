<script setup>
import { ref } from "vue";
import login from "./login.vue";
import join from "./join.vue";
const isJoinVisible = ref(false);
const isLoginVisible = ref(false);
</script>

<template>
  <div id="top-right-buttons">
    <!-- User정보가 있을 때 -->
    <login
      @click="isLoginVisible = true"
      isLoginVisible="isLoginVisible"
      >로그인</login
    >
    <join
      @click="isJoinVisible = true"
      isJoinVisible="isJoinVisible"
      >회원가입</join
    >
    <!-- User정보가 없을 때 -->
    <button>게시판</button>
    <button>로그아웃</button>
  </div>
</template>

<style scoped>
#top-right-buttons {
  position: absolute;
  top: 20px;
  right: 20px;
  z-index: 3;
  display: flex;
}
.btn-custom {
  background-color: rgba(0, 123, 255, 0.9);
  color: white;
  margin-left: 10px;
}

.btn-custom:first-child {
  margin-left: 0;
}

.btn-custom.info-btn {
  background-color: yellow;
  color: black;
}
#profile-btn {
  background-color: #6c757d;
  color: white;
  margin-right: 10px;
  margin-left: 10px;
}

#signup-btn {
  background-color: #28a745;
  color: white;
}

#logout-btn {
  background-color: #dc3545;
  color: white;
}
#signup-info-btn {
  background-color: #e0e0e0; /* Slightly darker than white */
  color: black;
  margin-left: 10px;
}
</style>
