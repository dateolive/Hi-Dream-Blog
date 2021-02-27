module.exports = {
  presets: [
    '@vue/app'
  ],
  plugins: [
    ["prismjs", {
      "languages": ["javascript", "java","css", "markup"],
      "plugins": ["line-numbers"], //配置显示行号插件
      "theme": "tomorrow", //主体名称
      "css": true
    }]
  ]
}
