import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

import { useKakao } from "vue3-kakao-maps/@utils";

useKakao("05a47079bf95d0a15cefbb1c9fc58b82");

const app = createApp(App);
app.use(createPinia());

// app.use(router);
app.mount("#app");
