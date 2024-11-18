import { defineStore } from "pinia";

export const useUserStore = defineStore("userStore", {
  state: () => ({
    jwtToken: null,
  }),
  actions: {
    setjwtToken(token) {
      this.jwtToken = token;
    },
    logout() {
      this.jwtToken = null;
      localStorage.removeItem("jwtToken");
      sessionStorage.removeItem("jwtToken");
    },
    getjwtToken() {
      return this.jwtToken;
    },

    
  },
});
