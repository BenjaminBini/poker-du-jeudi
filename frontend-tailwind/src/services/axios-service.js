import axios from "axios";

export let Axios = axios.create({
  baseURL: process.env.VUE_APP_API_BASE,
  timeout: 20000,
});

export let Login = axios.create({
  baseURL: process.env.VUE_APP_LOGIN_BASE,
  timeout: 20000,
});
