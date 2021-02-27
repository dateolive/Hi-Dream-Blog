module.exports = {
  presets: [
    '@vue/cli-plugin-babel/preset'
  ],
  plugins: [
    ["prismjs", {
      "languages": ["javascript", "Java","css", "markup"],
      "plugins": ["line-numbers"], //配置显示行号插件
      "theme": "tomorrow", //主体名称
      "css": true
    }]
  ]
}
