<script setup>
const chatbotModel = defineModel("chatbotVisible");
const messageModel = defineModel("messageVisible");
const favoritesModel = defineModel("favoritesVisible");
const chatbotOpen = () => {
  chatbotModel.value = !chatbotModel.value;
  messageModel.value = false;
  favoritesModel.value = false;
};
const messageOpen = () => {
  chatbotModel.value = false;
  messageModel.value = !messageModel.value;
  favoritesModel.value = false;
};
const favoritesOpen = () => {
  chatbotModel.value = false;
  messageModel.value = false;
  favoritesModel.value = !favoritesModel.value;
};
</script>

<template>
  <div id="bottom-right-buttons">
    <button class="btn btn-custom chatbot-btn" @click="chatbotOpen">
      챗봇
    </button>
    <button class="btn btn-custom message-btn" @click="messageOpen">
      메시지
    </button>
    <button class="btn btn-custom favorites-btn" @click="favoritesOpen">
      관심지역 열람
    </button>
  </div>
</template>

<style scoped>
#bottom-right-buttons {
  position: absolute;
  bottom: 20px;
  right: 20px;
  z-index: 3;
  display: flex;
  flex-direction: row; /* 한 줄에 배치되도록 수정 */
}
.chatbot {
  width: 300px;
  margin: 0 auto;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  font-family: Arial, sans-serif;
  overflow: hidden;
}

#bottom-right-buttons .chatbot-btn {
  background-color: #14d04a; /* 민트색 */
  color: white;
  margin-right: 10px; /* 버튼 사이에 간격 추가 */
}

#bottom-right-buttons .message-btn {
  background-color: #0f4c81; /* 보라색 */
  color: white;
  margin-right: 10px; /* 버튼 사이에 간격 추가 */
}
#bottom-right-buttons .favorites-btn {
  background-color: #0f4c81; /* 보라색 */
  color: white;
  margin-right: 10px; /* 버튼 사이에 간격 추가 */
}
</style>
