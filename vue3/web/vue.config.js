module.exports = {
    devServer: {
        open: true,
        port: 8080,
        https: false,
        proxy: {
            '/api': {
                target: "http://localhost:8999",
                changeOrigin: true,
                pathRewrite: {
                    '^/api': ''
                }
            }
        }
    }
}