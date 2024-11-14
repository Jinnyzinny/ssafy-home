import axios from "axios";

const getSidoList = axios
  .post({
    url: "http://localhost:5000/interest/addFavoriteArea",
    timeout: 1000,
    header: ["text/html; charset=UTF-8"],
  })
  .then(
    (response) => {
      response.json();
    },
    (text) => {
      console.log(text);
    }
  )
  .catch((error) => {
    console.log(error);
  })
  .finally(() => {});
