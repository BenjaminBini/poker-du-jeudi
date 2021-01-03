import { Axios } from "./axios-service";

export default {
  getSeasons() {
    return Axios.get("/seasons/");
  },
};
