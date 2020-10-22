import axios from 'axios';

export let Axios = axios.create({
    baseURL: `/api`,
    timeout: 1000
});
