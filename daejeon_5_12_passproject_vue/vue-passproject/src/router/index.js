import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/board",
      beforeEnter() {
        // Spring 서버의 JSP 페이지로 리디렉션
        window.location.href = "http://localhost:5000/api";
      },
    },
  ],
});

export default router;
