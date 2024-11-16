import axios from "axios";

const axiosInstance = axios.create({
  baseURL: "http://localhost:5000", // Spring Boot API URL
  withCredentials: true, // 필요 시 쿠키 기반 인증
});

export default axiosInstance;
