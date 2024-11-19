import BoardView from "@/views/BoardView.vue";
import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: "/", component: () => import("@/App.vue") },
    {
      path: "/modal",
      component: () => import("@/components/user/modalWrapper.vue"),
      // children: [
      //   {
      //     path: "",
      //     name: "BoardList",
      //     component: () => import("./pages/BoardList.vue"),
      //   },
      //   {
      //     path: "post/:id",
      //     name: "BoardPost",
      //     component: () => import("./pages/BoardPost.vue"),
      //     props: true,
      //   },
      // ],
    },
  ],
});

export default router;
