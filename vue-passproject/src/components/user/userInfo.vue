<script setup>
import { ref } from "vue";
import axios from "axios";

// 상태 변수
const isModalVisible = ref(false); // 모달 상태
const userName = ref("");
const userId = ref(""); // 읽기 전용
const userPwd = ref("");
const pwdCheck = ref("");
const emailId = ref("");
const emailDomain = ref("");
const message = ref(""); // 오류 메시지

// 사용자 정보 가져오기
const fetchUserInfo = async () => {
  try {
    const response = await axios.get("http://localhost:5000/user/userinfo", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    }).then(response=>response.data);
    // 서버 응답으로 상태 업데이트
    userName.value = response.userName;
    userId.value = response.userId;
    emailId.value = response.emailId;
    emailDomain.value = response.emailDomain;
  } catch (error) {
    console.error("Error fetching user data:", error);
    alert("사용자 정보를 불러오는 중 문제가 발생했습니다.");
  }
};

// 회원 정보 수정
const updateProfile = async () => {
  message.value = ""; // 기존 메시지 초기화

  if (userPwd.value !== pwdCheck.value) {
    message.value = "비밀번호가 일치하지 않습니다.";
    return;
  }

  const data = {
    userName: userName.value,
    userPwd: userPwd.value,
    emailId: emailId.value,
    emailDomain: emailDomain.value,
  };

  try {
    const response = await axios.put("http://localhost:5000/user/edit", data, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });

    alert(response.data.message);
    closeModal(); // 수정 성공 후 모달 닫기
  } catch (error) {
    console.error("Error updating profile:", error);
    alert("회원정보 수정 중 문제가 발생했습니다.");
  }
};

// 회원 탈퇴
const deleteMember = async () => {
  if (!confirm("정말 탈퇴하시겠습니까?")) return;

  try {
    await axios.delete("http://localhost:5000/user/delete", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });

    alert("계정이 성공적으로 삭제되었습니다.");
    localStorage.removeItem("token"); // JWT 토큰 제거
    closeModal(); // 탈퇴 성공 후 모달 닫기
    window.location.href = "/"; // 홈으로 이동
  } catch (error) {
    console.error("탈퇴 오류:", error);
    alert("탈퇴 중 문제가 발생했습니다.");
  }
};

// 모달 열기/닫기
const openModal = () => {
  fetchUserInfo(); // 사용자 정보 가져오기
  isModalVisible.value = true;
};
const closeModal = () => {
  isModalVisible.value = false;
};
</script>

<template>
  <div>
    <!-- 회원 정보 수정 모달 열기 버튼 -->
    <button class="btn btn-primary" @click="openModal">회원 정보 수정</button>

    <!-- 회원 정보 수정 모달 -->
    <div v-if="isModalVisible" class="modal-overlay">
      <div class="modal-content">
        <div class="row justify-content-center">
          <div class="col-lg-8 col-md-10 col-sm-12">
            <h2 class="my-3 py-3 shadow-sm bg-light text-center">
              <b class="orange">회원 정보 수정</b>
            </h2>
          </div>
          <div class="col-lg-8 col-md-10 col-sm-12">
            <form @submit.prevent="updateProfile">
              <div class="mb-3">
                <label for="username" class="form-label">이름 :</label>
                <input
                  type="text"
                  class="form-control"
                  id="username"
                  v-model="userName"
                  placeholder="이름..."
                  required
                />
              </div>
              <div class="mb-3">
                <label for="userid" class="form-label">아이디 :</label>
                <input
                  type="text"
                  class="form-control"
                  id="userid"
                  v-model="userId"
                  placeholder="아이디..."
                  readonly
                />
              </div>
              <div class="mb-3">
                <label for="userpwd" class="form-label">새 비밀번호 :</label>
                <input
                  type="password"
                  class="form-control"
                  id="userpwd"
                  v-model="userPwd"
                  placeholder="새 비밀번호를 입력하세요."
                />
              </div>
              <div class="mb-3">
                <label for="pwdcheck" class="form-label">비밀번호 확인 :</label>
                <input
                  type="password"
                  class="form-control"
                  id="pwdcheck"
                  v-model="pwdCheck"
                  placeholder="비밀번호를 다시 입력하세요."
                />
              </div>
              <div class="mb-3">
                <label for="emailid" class="form-label">이메일 :</label>
                <div class="input-group">
                  <input
                    type="text"
                    class="form-control"
                    id="emailid"
                    v-model="emailId"
                    placeholder="이메일아이디"
                  />
                  <span class="input-group-text">@</span>
                  <select
                    class="form-select"
                    id="emaildomain"
                    v-model="emailDomain"
                    aria-label="이메일 도메인 선택"
                  >
                    <option value="">선택</option>
                    <option value="ssafy.com">싸피</option>
                    <option value="google.com">구글</option>
                    <option value="naver.com">네이버</option>
                    <option value="kakao.com">카카오</option>
                  </select>
                </div>
              </div>
              <div class="text-danger mb-2">{{ message }}</div>
              <div class="col-auto text-center">
                <button type="submit" class="btn btn-outline-primary mb-3">수정</button>
                <button type="button" class="btn btn-outline-danger mb-3" @click="deleteMember">
                  탈퇴
                </button>
                <button type="button" class="btn btn-outline-secondary mb-3" @click="closeModal">
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
</style>
