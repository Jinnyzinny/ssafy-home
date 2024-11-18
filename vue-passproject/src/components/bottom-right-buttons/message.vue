<template>
    <div>
      <!-- Modal Trigger Button -->
      <button @click="openModal" class="open-button">Open Chat</button>
  
      <!-- Modal -->
      <div v-if="isModalOpen" class="modal-overlay" @click="closeModal">
        <div class="modal-content" @click.stop>
          <header class="modal-header">
            <h3>Chat</h3>
            <button class="close-button" @click="closeModal">X</button>
          </header>
          <div class="modal-body">
            <div class="message-list">
              <div v-for="(message, index) in messages" :key="index" class="message">
                <span>{{ message }}</span>
              </div>
            </div>
          </div>
          <footer class="modal-footer">
            <input
              v-model="newMessage"
              type="text"
              placeholder="Type a message..."
              @keydown.enter="sendMessage"
            />
            <button @click="sendMessage">Send</button>
          </footer>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from "vue";
  
  // Modal state
  const isModalOpen = ref(false);
  
  // Messages state
  const messages = ref([]);
  const newMessage = ref("");
  
  // Open and close modal functions
  const openModal = () => {
    isModalOpen.value = true;
  };
  
  const closeModal = () => {
    isModalOpen.value = false;
  };
  
  // Send message function
  const sendMessage = () => {
    if (newMessage.value.trim() !== "") {
      messages.value.push(newMessage.value);
      newMessage.value = "";
    }
  };
  </script>
  
  <style scoped>
  /* Modal Styles */
  .modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.6);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
  }
  
  .modal-content {
    background: white;
    width: 90%;
    max-width: 400px;
    border-radius: 8px;
    overflow: hidden;
    display: flex;
    flex-direction: column;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
  }
  
  .modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: #f4f4f4;
    padding: 10px 15px;
    border-bottom: 1px solid #ddd;
  }
  
  .modal-body {
    padding: 10px;
    flex-grow: 1;
    overflow-y: auto;
  }
  
  .message-list {
    max-height: 300px;
    overflow-y: auto;
  }
  
  .message {
    margin-bottom: 10px;
    padding: 8px 10px;
    background: #f1f1f1;
    border-radius: 5px;
  }
  
  .modal-footer {
    display: flex;
    padding: 10px 15px;
    border-top: 1px solid #ddd;
    background: #f4f4f4;
  }
  
  .modal-footer input {
    flex-grow: 1;
    margin-right: 10px;
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 5px;
  }
  
  .modal-footer button {
    padding: 8px 15px;
    background: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .modal-footer button:hover {
    background: #0056b3;
  }
  
  .close-button {
    background: none;
    border: none;
    cursor: pointer;
    font-size: 16px;
    color: #333;
  }
  
  .open-button {
    padding: 10px 20px;
    background: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .open-button:hover {
    background: #0056b3;
  }
  </style>
  