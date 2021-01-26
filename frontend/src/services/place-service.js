import { Axios } from "./axios-service";

export default {
  getPlaces() {
    return Axios.get("/places/");
  },
};
