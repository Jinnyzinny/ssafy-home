<script setup>
import axios from "axios";
import { ref } from "vue";
import axiosInstance from "@/componenets/axios/axios.js";

const isLoginVisible = ref(false);

const loginCheck = axiosInstance.post("/user/login", {});
</script>
<template>
  <div class="row justify-content-center">
    <div class="col-lg-12 col-md-10 col-sm-12">
      <!-- 모달을 여는 버튼 -->
      <button @click="isLoginVisible = true">로그인</button>

      <!-- 모달 창 -->
      <div v-if="isLoginVisible" id="modal-overlay">
        <div id="modal-content">
          <h2 class="my-3 py-3 shadow-sm bg-light text-center">로그인</h2>

          <b class="orange">아이디</b>
          <input type="text" maxlength="20" />
          <br />
          <b class="orange">비밀번호</b>
          <input
            type="text"
            maxlength="60"
          /><!-- 이후에 type="password로 교체해야 한다"-->
          <br />
          <input type="submit" value="로그인" @click="" />
          <input type="button" @click="isLoginVisible = false" value="닫기" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
#modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 99;
}

#modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 99;
}
</style>
