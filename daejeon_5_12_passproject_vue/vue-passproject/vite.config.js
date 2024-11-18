import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import vueDevTools from "vite-plugin-vue-devtools";

// https://vite.dev/config/
export default defineConfig({
  assetsinclude: ["**/*.vue"],
  plugins: [vue(), vueDevTools()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
  build: {
    outDir: "../home/src/main/resources/static",
  }, // 빌드 결과물이 생성되는 경로
  server: {
    port: 5173,
    proxy: {
      "/board": {
        target: "http://localhost:5000",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/board/, "/board"),
      },
      // API 요청을 프록시
      "/user": {
        target: "http://localhost:5000", // Spring Boot 서버 주소
        changeOrigin: true, // CORS 허용
        rewrite: (path) => path.replace(/^\/user/, ""), // '/api'를 제거하고 Spring Boot로 전달
      },
    },
  },
});
