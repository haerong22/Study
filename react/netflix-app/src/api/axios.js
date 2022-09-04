const { default: axios } = require("axios");

const instance = axios.create({
  baseURL: "https://api.themoviedb.org/3",
  params: {
    api_key: "b543d4f502da8c044bf0b5405c85a2d9",
    language: "ko-KR",
  },
});

export default instance;
