<script setup>
const noticeVisible = defineProps("noticeVisible");

import { useRouter } from "vue-router";
const router = useRouter();
const emit = defineEmits("close");
const closeModal = () => {
  //   router.push("/"); // 모달 닫기 시 이전 경로로 이동
  emit("close");
};
</script>
<template>
  <div class="modal-overlay" v-if="noticeVisible">
    <div class="modal-content">
      자 여기에 이제 라우터를 붙일거야
      <button class="close-button" @click="closeModal">닫기</button>
      <!-- 게시판을 렌더링 -->
      <router-view />
    </div>
  </div>
</template>
<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 600px;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
}
</style>
