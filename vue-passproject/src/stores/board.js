import { defineStore } from "pinia";

export const useBoardStore = defineStore("board", {
  state: () => ({
    posts: [],
  }),
  actions: {
    createPost(post) {
      this.posts.push({ id: Date.now(), ...post });
    },
    updatePost(id, updatedPost) {
      const index = this.posts.findIndex((post) => post.id === id);
      if (index !== -1) {
        this.posts[index] = { ...this.posts[index], ...updatedPost };
      }
    },
    deletePost(id) {
      this.posts = this.posts.filter((post) => post.id !== id);
    },
  },
});
