<template>
    <div>
      <h2>게시판 리스트</h2>
      <ul>
        <li v-for="post in posts" :key="post.id">
          <router-link :to="{ name: 'BoardPost', params: { id: post.id } }">
            {{ post.title }}
          </router-link>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        posts: [
          { id: 1, title: '첫 번째 게시글' },
          { id: 2, title: '두 번째 게시글' },
          { id: 3, title: '세 번째 게시글' },
        ],
      };
    },
  };
  </script>
  