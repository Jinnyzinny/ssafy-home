<script setup>
import { ref } from "vue";

const isJoinVisible = ref(false);
</script>
<template>
  <div>
    <!-- 모달을 여는 버튼 -->
    <button @click="isJoinVisible = true">회원가입</button>

    <!-- 모달 창 -->
    <div v-if="isJoinVisible" class="modal-overlay">
      <div class="modal-content">
        <h1>회원가입</h1>
        <h2>아이디</h2>
        <input type="text" maxlength="20" />
        <h2>비밀번호</h2>
        <input type="text" maxlength="60" /><!-- 이후에 type="password로 교체해야 한다"-->
        <h2>이름</h2>
        <input type="text" maxlength="60" />
        <h2>주소</h2>
        <input type="text" maxlength="60" /><!--여기는 주소 찾아볼 수 있는 서비스 탑재?-->
        <h2>연락처</h2>
        <input type="text" maxlength="60" />
        <input type="submit" value="회원가입" />
        <input type="button" @click="isJoinVisible = false" value="닫기" />
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
