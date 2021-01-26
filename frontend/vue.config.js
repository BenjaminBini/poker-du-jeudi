module.exports = {
  // proxy all webpack dev-server requests starting with /api
  // to our Spring Boot backend (localhost:8098) using http-proxy-middleware
  // see https://cli.vuejs.org/config/#devserver-proxy
  devServer: {
    proxy: "http://localhost:8081",
  },
  configureWebpack: {
    devtool: "source-map",
  },
};
