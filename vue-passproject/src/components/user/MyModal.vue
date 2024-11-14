<template>
  <div>
    <!-- 모달을 여는 버튼 -->
    <button @click="isModalVisible = true">모달 열기</button>

    <!-- 모달 창 -->
    <div v-if="isModalVisible" class="modal-overlay">
      <div class="modal-content">
        <h2>모달 제목</h2>
        <p>모달 내용이 여기에 표시됩니다.</p>
        <h2>아이디</h2>
        <input type="text" maxlength="20" />
        <h2>비밀번호</h2>
        <input type="text" maxlength="60" /><!-- 이후에 type="password로 교체해야 한다"-->
        <button @click="isModalVisible = false">닫기</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const isModalVisible = ref(false);
</script>

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
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
</style>
