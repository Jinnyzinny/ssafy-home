import axios from "axios";

const Axios = axios.create({
  baseURL: "https://localhost:5000",
  timeout: 1000,
});

export default Axios;
