<script setup>
import axios from "axios";
import { ref } from "vue";

const isLoginVisible = ref(false);

const loginData = {
  userId: "",
  userPwd: "",
};

const loginCheck = axios
  .post("https://localhost:5000/user/login", {
    Headers: {
      "content-Type": "application/json",
    },
    userId: "",
    userPwd: "",
  })
  .then((response) => {
    console.log(response.data.message);
    console.log(response.data.token);
  })
  .catch((error) => console.log(error));
</script>
<template>
  <div>
    <!-- 모달을 여는 버튼 -->
    <button @click="isLoginVisible = true">로그인</button>

    <!-- 모달 창 -->
    <div v-if="isLoginVisible" class="modal-overlay">
      <div class="modal-content">
        <h1>로그인</h1>
        <h2>아이디</h2>
        <input type="text" maxlength="20" name="user_Id" />
        <h2>비밀번호</h2>
        <input
          type="text"
          maxlength="60"
          name="user_password"
        /><!-- 이후에 type="password로 교체해야 한다"-->
        <input type="submit" @click="loginCheck" value="로그인" />
        <input type="button" @click="isLoginVisible = false" value="닫기" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal-overlay {
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

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 99;
}
</style>
