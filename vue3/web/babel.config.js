module.exports = {
    presets: [
        '@vue/cli-plugin-babel/preset'
    ],
    // css: {
    //   loaderOptions: {
    //     css: {
    //       // 这里的选项会传递给 css-loader
    //     },
    //     postcss: {
    //       // 这里的选项会传递给 postcss-loader
    //     },
    //     less:{
    //       javascriptEnabled: true,
    //     }
    //   }
    // },
    "plugins": [
        ["import", {"libraryName": "ant-design-vue", "libraryDirectory": "es", "style": "css"}]
    ],// `style: true` 会加载 less 文件

}
