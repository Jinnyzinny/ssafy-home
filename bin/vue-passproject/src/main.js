import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

import { useKakao } from "vue3-kakao-maps/@utils";

useKakao("d02b03ce9a797eb1398d15acce7bfb49");

const app = createApp(App);
app.use(createPinia());

// app.use(router);
 app.mount("#app");