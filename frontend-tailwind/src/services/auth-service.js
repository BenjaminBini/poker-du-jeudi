import { Axios, Login } from "./axios-service";

export default {
  async login(username, password) {
    try {
      const response = await Login.post("", {
        username,
        password,
      });
      console.dir(response);
      console.dir(response.headers);
      const jwt = response.headers.authorization.replace("Bearer ", "");
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
