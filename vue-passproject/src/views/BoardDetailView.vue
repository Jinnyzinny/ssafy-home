<template>
  <div>
    <h2>게시글 상세</h2>
    <p>게시글 ID: {{ id }}</p>
    <router-link to="/modal">뒤로 가기</router-link>
  </div>
</template>

<script setup>
const props=defineProps({
  id: {
    type: String,
    required: true,
  },
});
</script>
