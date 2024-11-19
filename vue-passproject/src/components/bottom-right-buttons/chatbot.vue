<template>
  <div class="chatbot">
    <h2>Chatbot</h2>
    <div class="chat-window">
      <div class="message" v-for="(message, index) in messages" :key="index">
        <span :class="message.sender">{{ message.sender }}:</span>
        <p>{{ message.text }}</p>
      </div>
    </div>
    <div class="chat-input">
      <input
        v-model="userInput"
        type="text"
        placeholder="Type your message..."
        @keyup.enter="sendMessage"
      />
      <button @click="sendMessage">Send</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

// State variables
const messages = ref([{ sender: 'Bot', text: 'Hello! How can I help you?' }]);
const userInput = ref('');

// Methods
const sendMessage = () => {
  if (userInput.value.trim() === '') return;

  // Add user message
  messages.value.push({ sender: 'User', text: userInput.value });

  // Simulate bot response
  const response = generateResponse(userInput.value);
  messages.value.push({ sender: 'Bot', text: response });

  // Clear input
  userInput.value = '';
};

const generateResponse = (input) => {
  // Simple response logic (replace with API call if needed)
  if (input.toLowerCase().includes('hello')) {
    return 'Hi there!';
  } else if (input.toLowerCase().includes('how are you')) {
    return "I'm just a bot, but I'm doing great!";
  } else {
    return "I'm not sure how to respond to that.";
  }
};
</script>

<style>
.chatbot {
  width: 300px;
  margin: 0 auto;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  font-family: Arial, sans-serif;
  overflow: hidden;
  z-index: 8;
}

h2 {
  background-color: #007bff;
  color: white;
  text-align: center;
  margin: 0;
  padding: 10px 0;
}

.chat-window {
  height: 300px;
  overflow-y: auto;
  padding: 10px;
  border-bottom: 1px solid #ddd;
  background-color: #f9f9f9;
}

.message {
  margin-bottom: 10px;
}

.message .Bot {
  font-weight: bold;
  color: #007bff;
}

.message .User {
  font-weight: bold;
  color: #28a745;
}

.chat-input {
  display: flex;
  padding: 10px;
}

.chat-input input {
  flex: 1;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.chat-input button {
  margin-left: 8px;
  padding: 8px 12px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.chat-input button:hover {
  background-color: #0056b3;
}
</style>
