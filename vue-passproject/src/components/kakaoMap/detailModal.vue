<template>
  <div v-if="isVisible" class="modal-overlay" @click="closeModal">
    <div class="modal-content" @click.stop>
      <h2>아파트 세부 정보</h2>
      <button class="close-button" @click="closeModal">닫기</button>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";

// 부모로부터 받을 props 정의
defineProps({
  isVisible: {
    type: Boolean,
    required: true,
  },
});

// 부모에게 이벤트를 전달
const emit = defineEmits(["close"]);

const closeModal = () => {
  emit("close");
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  min-width: 300px;
}

.close-button {
  margin-top: 10px;
  padding: 5px 10px;
  border: none;
  background-color: #ff5f5f;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}
</style>
