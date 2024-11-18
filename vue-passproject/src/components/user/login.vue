<script setup>
import axios from "axios";
import { ref } from "vue";

import { useUserStore } from "@/stores/userStore";
const userStore = useUserStore();

// 상태 변수
const isModalVisible = ref(false); // 모달 상태
const userId = ref(""); // 아이디 입력값
const userPwd = ref(""); // 비밀번호 입력값
const saveId = ref(false); // 아이디 저장 체크 상태
const error = ref(""); // 에러 메시지
const successMessage = ref(""); // 성공 메시지

// 로그인 함수
const login = async () => {
  error.value = ""; // 이전 에러 초기화
  successMessage.value = ""; // 이전 성공 메시지 초기화

  if (!userId.value || !userPwd.value) {
    error.value = "아이디와 비밀번호를 입력하세요.";
    return;
  }

  try {
    const response = await axios.post("http://localhost:5000/user/login", {
      userId: userId.value,
      userPwd: userPwd.value,
    });

    // 요청 성공 시 처리
    successMessage.value = response.data.message || "로그인 성공!";
    console.log("로그인 성공:", response.data);
    userStore.setjwtToken(response.data.token);

    if (saveId.value) {
      localStorage.setItem("savedUserId", userId.value); // 로컬 스토리지에 아이디 저장
    } else {
      localStorage.removeItem("savedUserId");
    }

    isModalVisible.value = false; // 모달 닫기
  } catch (err) {
    // 요청 실패 시 처리
    error.value =
      err.response?.data?.message || "로그인 실패. 다시 시도하세요.";
    console.error("로그인 실패:", err);
  }
};
</script>

<template>
  <div>
    <!-- 모달을 여는 버튼 -->
    <button class="btn btn-primary" @click="isModalVisible = true">
      로그인
    </button>

    <!-- 모달 -->
    <div v-if="isModalVisible" class="modal-overlay">
      <div class="modal-content">
        <div class="row justify-content-center">
          <div class="col-lg-8 col-md-10 col-sm-12">
            <h2 class="my-3 py-3 shadow-sm bg-light text-center">
              <b class="orange">로그인</b>
            </h2>
          </div>
          <div class="col-lg-8 col-md-10 col-sm-12">
            <form id="form-login" @submit.prevent="login">
              <div class="form-check mb-3 float-end">
                <input
                  class="form-check-input"
                  type="checkbox"
                  id="saveid"
                  name="saveid"
                  v-model="saveId"
                />
                <label class="form-check-label" for="saveid">
                  아이디 저장
                </label>
              </div>
              <div class="mb-3">
                <label for="userid" class="form-label">아이디 :</label>
                <input
                  type="text"
                  class="form-control"
                  id="userid"
                  name="userid"
                  placeholder="아이디..."
                  v-model="userId"
                  required
                />
              </div>
              <div class="mb-3">
                <label for="userpwd" class="form-label">비밀번호 :</label>
                <input
                  type="password"
                  class="form-control"
                  id="userpwd"
                  name="userpwd"
                  placeholder="비밀번호..."
                  v-model="userPwd"
                  required
                />
              </div>
              <div class="text-danger mb-2" id="error">{{ error }}</div>
              <div class="text-success mb-2">{{ successMessage }}</div>
              <div class="col-auto text-center">
                <button type="submit" class="btn btn-outline-primary mb-3">
                  로그인
                </button>
                <button
                  type="button"
                  class="btn btn-outline-secondary mb-3"
                  @click="isModalVisible = false"
                >
                  닫기
                </button>
              </div>
            </form>
          </div>
        </div>
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
  z-index: 999;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  max-width: 500px;
  width: 100%;
  z-index: 1000;
}

.orange {
  color: orange;
}

.text-danger {
  color: red;
}

.text-success {
  color: green;
}
</style>
