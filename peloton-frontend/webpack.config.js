const createExpoWebpackConfigAsync = require("@expo/webpack-config");
const { BundleAnalyzerPlugin } = require("webpack-bundle-analyzer");

module.exports = async (env, argv) => {
  const config = await createExpoWebpackConfigAsync(env, argv);

  // Optionally you can enable the bundle size report.
  // It's best to do this only with production builds because it will add noticeably more time to your builds and reloads.
  if (env.mode === "production") {
    config.plugins.push(
      new BundleAnalyzerPlugin({
        path: "web-report",
      })
    );
  }

  return config;
};
