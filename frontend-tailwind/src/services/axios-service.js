import axios from "axios";

export let Axios = axios.create({
  baseURL: `/api`,
  timeout: 20000,
});

export let Login = axios.create({
  baseURL: `/login`,
  timeout: 20000,
});
