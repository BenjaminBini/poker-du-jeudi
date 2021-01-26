import { Axios, Login } from "./axios-service";

export default {
  async login(username, password) {
    try {
      const response = await Login.post("", {
        username,
        password,
      });
      const jwt = response.data.replace("Bearer ", "");
      Axios.defaults.headers.common["Authorization"] = `Bearer ${jwt}`;
      localStorage.setItem("jwt", jwt);
    } catch (err) {
      this.logout();
      throw err;
    }
  },
  logout() {
    localStorage.removeItem("jwt");
    delete Axios.defaults.headers.common["Authorization"];
  },
};
