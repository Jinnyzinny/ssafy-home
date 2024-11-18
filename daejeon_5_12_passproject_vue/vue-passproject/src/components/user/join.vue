<script setup>
import { ref } from "vue";
import axios from "axios";

// 상태 관리
const isJoinVisible = ref(false); // 모달 상태
const userId = ref("");
const userPwd = ref("");
const userName = ref("");
const userAddress = ref("");
const userPhone = ref("");
const errorMessage = ref("");
const successMessage = ref("");

// 회원가입 처리 함수
const join = async () => {
  errorMessage.value = ""; // 에러 초기화
  successMessage.value = ""; // 성공 메시지 초기화

  if (!userId.value || !userPwd.value || !userName.value) {
    errorMessage.value = "모든 필수 항목을 입력해주세요.";
    return;
  }

  try {
    const response = await axios.post("http://localhost:5000/user/join", {
      userId: userId.value,
      userPwd: userPwd.value,
      userName: userName.value,
      userAddress: userAddress.value,
      userPhone: userPhone.value,
    });

    successMessage.value = response.data.message || "회원가입 성공!";
    isJoinVisible.value = false; // 모달 닫기
  } catch (error) {
    errorMessage.value =
      error.response?.data?.message || "회원가입 중 오류가 발생했습니다.";
  }
};
</script>

<template>
  <div>
    <!-- 모달을 여는 버튼 -->
    <button class="btn btn-primary" @click="isJoinVisible = true">
      회원가입
    </button>

    <!-- 모달 창 -->
    <div
      v-if="isJoinVisible"
      class="modal-overlay"
      tabindex="-1"
      aria-hidden="true"
    >
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">회원가입</h5>
          <button
            type="button"
            class="btn-close"
            aria-label="Close"
            @click="isJoinVisible = false"
          ></button>
        </div>
        <div class="modal-body">
          <!-- 회원가입 폼 -->
          <div class="mb-3">
            <label for="userId" class="form-label">아이디</label>
            <input
              type="text"
              id="userId"
              class="form-control"
              v-model="userId"
              maxlength="20"
            />
          </div>
          <div class="mb-3">
            <label for="userPwd" class="form-label">비밀번호</label>
            <input
              type="password"
              id="userPwd"
              class="form-control"
              v-model="userPwd"
              maxlength="60"
            />
          </div>
          <div class="mb-3">
            <label for="userName" class="form-label">이름</label>
            <input
              type="text"
              id="userName"
              class="form-control"
              v-model="userName"
              maxlength="60"
            />
          </div>
          <div class="mb-3">
            <label for="userAddress" class="form-label">주소</label>
            <input
              type="text"
              id="userAddress"
              class="form-control"
              v-model="userAddress"
              maxlength="100"
            />
          </div>
          <div class="mb-3">
            <label for="userPhone" class="form-label">연락처</label>
            <input
              type="text"
              id="userPhone"
              class="form-control"
              v-model="userPhone"
              maxlength="15"
            />
          </div>
          <div class="text-danger">{{ errorMessage }}</div>
          <div class="text-success">{{ successMessage }}</div>
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-secondary"
            @click="isJoinVisible = false"
          >
            닫기
          </button>
          <button
            type="button"
            class="btn btn-primary"
            @click="join"
          >
            회원가입
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 모달 스타일 */
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
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  max-width: 600px;
  width: 100%;
}

.text-danger {
  color: red;
}

.text-success {
  color: green;
}
</style>
